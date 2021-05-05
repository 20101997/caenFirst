package com.grupposts.trasporti.features.home

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.PopupMenu
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.grupposts.domain.models.Journey
import com.grupposts.domain.models.TravelStructure
import com.grupposts.trasporti.features.MainViewModel
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentHomeBinding
import com.grupposts.trasporti.utils.TopSheetBehavior
import com.grupposts.trasporti.utils.hideToolbar
import com.grupposts.trasporti.utils.showError
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var journeyAdapter: PianificationListAdapter<Journey>
    private lateinit var travelStructureAdapter: PianificationListAdapter<TravelStructure>

    private val viewModel: HomeViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var topSheetBehavior: TopSheetBehavior<ConstraintLayout>
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var requestPermissionLauncher: ActivityResultLauncher<String>

    companion object {
        // For development only
        private const val SKIP_START_JOURNY = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            mainViewModel.onBackPressed()
        }

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireContext())
        requestPermissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                viewModel.startLocationUpdates(fusedLocationClient)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        hideToolbar()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)

        setupUI()
        setObservers()
    }

    override fun onResume() {
        super.onResume()
        hideTopSheet()
        hideBottomSheet()
        hideStopTravelOverlay()

        viewModel.checkToken()

        if (viewModel.isJourneyStarted)
            startLocationUpdates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.stopLocationUpdates(fusedLocationClient)
    }

    private fun setupUI() {
        with(binding) {
            progressIndicator.hide()

            stopTravelOverlay.stopTravelButton.setOnClickListener {
                showBottomSheet(mainViewModel.selectedJourney)
            }

            journeyAdapter = PianificationListAdapter {
                mainViewModel.selectedJourney = it
                showTopSheet(it)
            }

            travelStructureAdapter = PianificationListAdapter()
        }

        setupToolbar()
        setupTopSheet()
        setupBottomSheet()
    }

    private fun setObservers() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.progressIndicator.show()
                } else {
                    binding.progressIndicator.hide()
                }
            }

            error.observe(viewLifecycleOwner) {
                showError(it)
            }

            navigateToLogin.observe(viewLifecycleOwner) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoginFragment())
            }

            navigateToDepartments.observe(viewLifecycleOwner) {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDepartmentsFragment())
            }

            noTravelFoundVisibility.observe(viewLifecycleOwner) {
                binding.tvNoTravelFound.visibility = it
            }

            pianification.observe(viewLifecycleOwner) {
                travelStructureAdapter.submitList(it?.travelStructures)
            }

            toolbarDate.observe(viewLifecycleOwner) {
                binding.toolbar.toolbarDatepickerValue.text = it
            }

            toolbarDateColor.observe(viewLifecycleOwner) {
                val color = ContextCompat.getColor(requireContext(), it)
                binding.toolbar.toolbarDatepickerValue.setTextColor(color)
            }

            isJourneyAdapter.observe(viewLifecycleOwner) {
                if (it) {
                    binding.rvDestinations.adapter = journeyAdapter
                } else {
                    binding.rvDestinations.adapter = travelStructureAdapter
                }
            }

            travel.observe(viewLifecycleOwner) {
                mainViewModel.selectedTravel = it
                journeyAdapter.submitList(it?.journeys)
            }

            startedJourney.observe(viewLifecycleOwner) {
                it?.let {
                    hideTopSheet()
                    // TODO: take structure position from api response, when available
                    if (isJourneyStarted)
                        getStructureLocation(requireContext(), it.structure)
                }
            }

            destinationLocation.observe(viewLifecycleOwner) {
                if (isJourneyStarted)
                    openGoogleMaps(it)
            }

            lastLocation.observe(viewLifecycleOwner) {
                if (viewModel.isCloseToDestination(it))
                    showStopTravelOverlay(mainViewModel.selectedJourney)
            }
        }
    }

    private fun openGoogleMaps(location: Location?) {
        location?.let {
            val intentUri =
                Uri.parse("google.navigation:q=${location.latitude},${location.longitude}")
            val mapIntent = Intent(Intent.ACTION_VIEW, intentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }

    private fun startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            viewModel.startLocationUpdates(fusedLocationClient)
        } else {
            requestPermissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun setupToolbar() {
        with(binding.toolbar) {
            toolbarDatepickerValue.text = viewModel.getTodayDate()
            toolbarDatepickerPlus.setOnClickListener {
                viewModel.increaseToolbarDate()
            }
            toolbarDatepickerMinus.setOnClickListener {
                viewModel.decreaseToolbarDate()
            }
            toolbarMenuButton.setOnClickListener { view ->
                val popup = PopupMenu(requireContext(), view)
                popup.menuInflater.inflate(R.menu.toolbar_menu, popup.menu)
                popup.setOnMenuItemClickListener {
                    if (it.title == getString(R.string.logout_label)) {
                        viewModel.logout()
                        true

                    } else {
                        false
                    }
                }
                popup.show()
            }
        }
    }

    private fun showStopTravelOverlay(journey: Journey?) {
        with(binding.stopTravelOverlay) {
            structureName.text = journey?.structure?.name
            departmentNames.text = journey?.departments?.map {
                it.name
            }?.joinToString(separator = ", ")

            root.visibility = View.VISIBLE
        }
    }

    private fun hideStopTravelOverlay() {
        binding.stopTravelOverlay.root.visibility = View.GONE
    }

    private fun showTopSheet(journey: Journey) {
        with(binding.topSheet) {
            destinationStructureValue.text = journey.structure?.name
            arrivalTimeValue.text = viewModel.formatArrivalTime(journey.arrivalAt)
            departmentsValue.text = viewModel.formatDepartments(journey.departments)
            backButton.setOnClickListener {
                hideTopSheet()
            }
            startButton.setOnClickListener {
                if (SKIP_START_JOURNY) {
                    findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDepartmentsFragment())
                } else {
                    viewModel.startJourney(journey.id)
                }
            }
        }

        topSheetBehavior.state = TopSheetBehavior.STATE_EXPANDED
    }

    private fun hideTopSheet() {
        topSheetBehavior.state = TopSheetBehavior.STATE_HIDDEN
    }

    private fun showBottomSheet(journey: Journey?) {
        with(binding.bottomSheet) {
            tvStructureName.text = journey?.structure?.name
            ibCloseBottomSheet.setOnClickListener {
                hideBottomSheet()
            }
            btnStopTravel.setOnClickListener {
                viewModel.stopJourney(journey?.id)
            }
        }

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }

    private fun hideBottomSheet() {
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun setupTopSheet() {
        topSheetBehavior = TopSheetBehavior.from(binding.topSheet.root)
        topSheetBehavior.isDraggable = false
    }

    private fun setupBottomSheet() {
        bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.root)
        bottomSheetBehavior.isFitToContents = false
        bottomSheetBehavior.isDraggable = false
        bottomSheetBehavior.expandedOffset = 400
    }

}