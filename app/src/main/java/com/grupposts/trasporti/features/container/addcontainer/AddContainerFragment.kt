package com.grupposts.trasporti.features.container.addcontainer

import android.os.Bundle
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.navGraphViewModels
import com.grupposts.domain.models.ActionType
import com.grupposts.domain.models.Container
import com.grupposts.trasporti.features.MainViewModel
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentAddContainerBinding
import com.grupposts.trasporti.features.container.*
import com.grupposts.trasporti.utils.showError
import com.grupposts.trasporti.utils.showToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddContainerFragment : Fragment(R.layout.fragment_add_container) {

    private lateinit var binding: FragmentAddContainerBinding

    private val viewModel: AddContainerViewModel by viewModels()
    private val containerViewModel: ContainerViewModel by navGraphViewModels(R.id.container_navigation) { defaultViewModelProviderFactory }
    private val mainViewModel: MainViewModel by activityViewModels()

    private val navArgs: AddContainerFragmentArgs by navArgs()

    private lateinit var selectedProductsAdapter: SelectedProductListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setFragmentResultListener(CONTAINER_REQUEST_KEY) { _, bundle ->
            val container = bundle.getParcelable<Container>(CONTAINER_PARAM_KEY)
            setupSelectedContainerUI(container)
        }

        if (mainViewModel.selectedActionType == ActionType.DELIVERY) {
            findNavController().navigate(AddContainerFragmentDirections.actionAddContainerFragmentToContainersFragment())
        }

        containerViewModel.resetContainer()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showToolbar(
            navArgs.name ?: getString(
                R.string.container_value,
                containerViewModel.getContainerNumber()
            )
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddContainerBinding.bind(view)

        selectedProductsAdapter = SelectedProductListAdapter {
            viewModel.deleteProduct(it)
        }
        binding.rvSelectedProducts.adapter = selectedProductsAdapter

        setupUI()
        setObservers()
        containerViewModel.setContainerDepartmentFrom(mainViewModel.selectedDepartment?.id)
    }

    private fun setupUI() {
        with(binding) {
            destinationDepartmentProgressIndicator.hide()

            tvHospitalOriginName.text = mainViewModel.selectedJourney?.structure?.name
            tvDepartmentOriginName.text = mainViewModel.selectedDepartment?.name

            ivScanSingleQrcode.setOnClickListener {
                if (viewModel.areDestinationFieldFilled())
                    containerViewModel.onSingleContainer()
            }

            ivScanMultipleQrcode.setOnClickListener {
                if (viewModel.areDestinationFieldFilled())
                    containerViewModel.onMultipleContainer()
            }

            ibThermometer.setOnCheckedChangeListener {
                viewModel.setTemperatureTracking(it)
            }

            tilNote.editText?.addTextChangedListener {
                containerViewModel.setContainerNote(it.toString())
            }

            editButton.setOnClickListener {
                containerViewModel.editContainer(mainViewModel.selectedTravel?.id)
            }
        }
    }

    private fun setupSelectedContainerUI(container: Container?) {
        containerViewModel.setContainerId(container?.id)
        viewModel.setTemperatureTracking(container?.temperatureTracking == 1)
        viewModel.setProducts(container?.products)

        with(binding) {
            clScanQrcode.visibility = View.GONE

            // TODO: pre-fill destination structure and department
           //  tilDestinationDepartment.editText?.setText("Seleziona di nuovo")
            // tilDestinationStructure.editText?.setText(container?.structure?.name)

            tilNote.editText?.setText(container?.note)
            clSelectedProducts.visibility = View.VISIBLE
        }
    }

    private fun setObservers() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                if (it) {
                    binding.destinationDepartmentProgressIndicator.show()
                } else {
                    binding.destinationDepartmentProgressIndicator.hide()
                }
            }

            error.observe(viewLifecycleOwner) {
                showError(it)
            }

            structures.observe(viewLifecycleOwner) { list ->
                val items = list.map { it.name }
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    items
                )
                binding.actDestinationStructure.setAdapter(adapter)
                binding.actDestinationStructure.setOnItemClickListener { _, _, position, _ ->
                    onDestinationStructureSelected(list[position])
                }
            }

            departments.observe(viewLifecycleOwner) { list ->
                val items = list.map { it.name }
                val adapter = ArrayAdapter(
                    requireContext(),
                    android.R.layout.simple_dropdown_item_1line,
                    items
                )
                binding.actDestinationDepartment.setAdapter(adapter)
                binding.actDestinationDepartment.setOnItemClickListener { _, _, position, _ ->
                    val department = list[position]
                    viewModel.onDestinationDepartmentSelected(department)
                    containerViewModel.setContainerDepartmentTo(department.id)
                }
            }

            isTemperatureTrackingActive.observe(viewLifecycleOwner) {
                containerViewModel.setContainerTemperatureTracking(it)
                binding.ibThermometer.isChecked = it
                if (it) {
                    binding.clTemperature.setBackgroundResource(R.color.blue)
                    binding.tvTemperatureSwitch.text = getString(R.string.on)
                } else {
                    binding.clTemperature.setBackgroundResource(R.color.lightGrey)
                    binding.tvTemperatureSwitch.text = getString(R.string.off)
                }
            }

            destinationStructureError.observe(viewLifecycleOwner) {
                binding.tilDestinationStructure.error = it
                binding.tilDestinationStructure.isErrorEnabled = it != null
            }

            destinationDepartmentError.observe(viewLifecycleOwner) {
                binding.tilDestinationDepartment.error = it
                binding.tilDestinationDepartment.isErrorEnabled = it != null
            }

            products.observe(viewLifecycleOwner) {
                selectedProductsAdapter.submitList(it)
                containerViewModel.setContainerProducts(it)
            }
        }

        with(containerViewModel) {
            error.observe(viewLifecycleOwner) {
                showError(it)
            }

            editButtonLoading.observe(viewLifecycleOwner) {
                binding.editButton.isEnabled = !it
                // TODO: show circle progress on button while loading
            }

            navigateToScanContainer.observe(viewLifecycleOwner) {
                findNavController().navigate(AddContainerFragmentDirections.actionAddContainerFragmentToScanContainerFragment())
            }

            navigateToContainers.observe(viewLifecycleOwner) {
                findNavController().navigate(AddContainerFragmentDirections.actionAddContainerFragmentToContainersFragment())
            }
        }
    }
}