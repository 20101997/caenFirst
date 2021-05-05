package com.grupposts.trasporti.features.container.scancontainer

import android.annotation.SuppressLint
import android.content.Context
import android.hardware.display.DisplayManager
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentScanContainerBinding
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import android.Manifest
import android.content.pm.PackageManager
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.google.mlkit.vision.barcode.Barcode
import com.google.mlkit.vision.barcode.BarcodeScannerOptions
import com.google.mlkit.vision.barcode.BarcodeScanning
import com.google.mlkit.vision.common.InputImage
import com.grupposts.trasporti.features.container.ContainerViewModel
import com.grupposts.trasporti.utils.hideToolbar
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min

@AndroidEntryPoint
class ScanContainerFragment : Fragment(R.layout.fragment_scan_container) {

    private lateinit var binding: FragmentScanContainerBinding
    private lateinit var broadcastManager: LocalBroadcastManager
    private lateinit var cameraExecutor: ExecutorService

    private var displayId: Int = -1
    private var lensFacing: Int = CameraSelector.LENS_FACING_BACK
    private var preview: Preview? = null
    private var imageCapture: ImageCapture? = null
    private var imageAnalyzer: ImageAnalysis? = null
    private var camera: Camera? = null
    private var cameraProvider: ProcessCameraProvider? = null
    private val PERMISSIONS_REQUIRED = arrayOf(Manifest.permission.CAMERA)

    private var isScanning = false

    private val viewModel: ContainerViewModel by navGraphViewModels(R.id.container_navigation) { defaultViewModelProviderFactory }

    private val displayManager by lazy {
        requireContext().getSystemService(Context.DISPLAY_SERVICE) as DisplayManager
    }

    private val displayListener = object : DisplayManager.DisplayListener {
        override fun onDisplayAdded(displayId: Int) = Unit
        override fun onDisplayRemoved(displayId: Int) = Unit
        override fun onDisplayChanged(displayId: Int) = view?.let { view ->
            if (displayId == this@ScanContainerFragment.displayId) {
                Timber.d("Rotation changed: ${view.display.rotation}")
                imageCapture?.targetRotation = view.display.rotation
                imageAnalyzer?.targetRotation = view.display.rotation
            }
        } ?: Unit
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.setContainerCode(null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @SuppressLint("MissingPermission")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentScanContainerBinding.bind(view)

        setupToolbar()

        binding.btnScanQrcode.setOnClickListener {
            isScanning = true
        }

        cameraExecutor = Executors.newSingleThreadExecutor()
        broadcastManager = LocalBroadcastManager.getInstance(view.context)

        displayManager.registerDisplayListener(displayListener, null)

        // Wait for the views to be properly laid out
        binding.viewFinder.post {

            // Keep track of the display in which this view is attached
            displayId = binding.viewFinder.display.displayId

            // Build UI controls
            //updateCameraUi()

            // Set up the camera and its use cases
            setUpCamera()
        }
    }

    private fun setupToolbar() {
        with(binding) {
            toolbar.toolbarTitle.text = getString(R.string.container_value, viewModel.getContainerNumber())
            toolbar.toolbarBackButton.setOnClickListener {
                findNavController().navigateUp()
            }
            toolbar.toolbarSearchButton.visibility = View.GONE
            toolbar.root.visibility = View.VISIBLE
        }
    }

    override fun onResume() {
        super.onResume()
        // Make sure that all permissions are still present, since the
        // user could have removed them while the app was in paused state.
        if (!hasPermissions(requireContext())) {
            requestPermissions(PERMISSIONS_REQUIRED, PERMISSIONS_REQUEST_CODE)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        cameraExecutor.shutdown()

        displayManager.unregisterDisplayListener(displayListener)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<String>, grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSIONS_REQUEST_CODE) {
            if (PackageManager.PERMISSION_GRANTED == grantResults.firstOrNull()) {
                // Take the user to the success fragment when permission is granted
                Toast.makeText(context, "Permission request granted", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(context, "Permission request denied", Toast.LENGTH_LONG).show()
                findNavController().navigateUp()
            }
        }
    }

    private fun setUpCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener(Runnable {

            // CameraProvider
            cameraProvider = cameraProviderFuture.get()

            // Select lensFacing depending on the available cameras
            lensFacing = when {
                hasBackCamera() -> CameraSelector.LENS_FACING_BACK
                else -> throw IllegalStateException("Back and front camera are unavailable")
            }

            // Build and bind the camera use cases
            bindCameraUseCases()
        }, ContextCompat.getMainExecutor(requireContext()))
    }

    private fun bindCameraUseCases() {
        val metrics = DisplayMetrics().also { binding.viewFinder.display.getRealMetrics(it) }
        Timber.d("Screen metrics: ${metrics.widthPixels} x ${metrics.heightPixels}")

        val screenAspectRatio = aspectRatio(metrics.widthPixels, metrics.heightPixels)
        Timber.d("Preview aspect ratio: $screenAspectRatio")

        val rotation = binding.viewFinder.display.rotation

        val cameraProvider = cameraProvider
            ?: throw IllegalStateException("Camera initialization failed.")

        val cameraSelector = CameraSelector.Builder().requireLensFacing(lensFacing).build()

        preview = Preview.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        imageCapture = ImageCapture.Builder()
            .setCaptureMode(ImageCapture.CAPTURE_MODE_MINIMIZE_LATENCY)
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()

        imageAnalyzer = ImageAnalysis.Builder()
            .setTargetAspectRatio(screenAspectRatio)
            .setTargetRotation(rotation)
            .build()
            .also {
                it.setAnalyzer(cameraExecutor, BarcodeAnalyzer { result ->
                    if (isScanning && result != null) {
                        isScanning = false
                        viewModel.setContainerCode(result)
                        findNavController().navigate(ScanContainerFragmentDirections.actionScanContainerFragmentToProductsFragment())
                    }
                })
            }

        cameraProvider.unbindAll()

        try {
            camera = cameraProvider.bindToLifecycle(
                this, cameraSelector, preview, imageCapture, imageAnalyzer
            )

            preview?.setSurfaceProvider(binding.viewFinder.surfaceProvider)
        } catch (exc: Exception) {
            Timber.e(exc, "Use case binding failed")
        }
    }

    private fun aspectRatio(width: Int, height: Int): Int {
        val previewRatio = max(width, height).toDouble() / min(width, height)
        if (abs(previewRatio - RATIO_4_3_VALUE) <= abs(previewRatio - RATIO_16_9_VALUE)) {
            return AspectRatio.RATIO_4_3
        }
        return AspectRatio.RATIO_16_9
    }

    private fun hasBackCamera(): Boolean {
        return cameraProvider?.hasCamera(CameraSelector.DEFAULT_BACK_CAMERA) ?: false
    }

    private class BarcodeAnalyzer(private val listener: (String?) -> Unit) :
        ImageAnalysis.Analyzer {
        @SuppressLint("UnsafeExperimentalUsageError")
        override fun analyze(imageProxy: ImageProxy) {
            val mediaImage = imageProxy.image
            if (mediaImage != null) {
                val image = InputImage.fromMediaImage(
                    mediaImage,
                    imageProxy.imageInfo.rotationDegrees
                )
                val options = BarcodeScannerOptions.Builder()
                    .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
                    .build()
                val scanner = BarcodeScanning.getClient(options)
                scanner.process(image)
                    .addOnSuccessListener {
                        it.firstOrNull()?.let { barcode ->
                            listener(barcode.displayValue)
                        }
                    }
                    .addOnFailureListener {
                        Timber.e(it)
                    }
                    .addOnCompleteListener {
                        imageProxy.close()
                    }
            }
        }
    }

    private fun hasPermissions(context: Context) = PERMISSIONS_REQUIRED.all {
        ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
    }

    companion object {
        private const val RATIO_4_3_VALUE = 4.0 / 3.0
        private const val RATIO_16_9_VALUE = 16.0 / 9.0

        private const val PERMISSIONS_REQUEST_CODE = 10
    }

}