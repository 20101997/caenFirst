package com.grupposts.trasporti.features.container.containers

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.grupposts.domain.models.ActionType
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentContainersBinding
import com.grupposts.trasporti.features.MainViewModel
import com.grupposts.trasporti.features.container.*
import com.grupposts.trasporti.utils.showError
import com.grupposts.trasporti.utils.showToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ContainersFragment : Fragment(R.layout.fragment_containers) {

    private lateinit var binding: FragmentContainersBinding
    private lateinit var adapter: ContainerListAdapter

    private val viewModel: ContainerViewModel by navGraphViewModels(R.id.container_navigation) { defaultViewModelProviderFactory }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateBackToDepartments()
        }
    }

    override fun onResume() {
        super.onResume()
        showToolbar(
            mainViewModel.selectedJourney?.structure?.name,
            mainViewModel.selectedDepartment?.name
        ) {
            navigateBackToDepartments()
        }

        when (mainViewModel.selectedActionType) {
            ActionType.DELIVERY -> {
                viewModel.actionType = ActionType.DELIVERY
                viewModel.getContainersToDepartment(
                    mainViewModel.selectedTravel?.id,
                    mainViewModel.selectedDepartment?.id
                )
            }

            ActionType.WITHDRAWAL -> {
                viewModel.actionType = ActionType.WITHDRAWAL
                viewModel.getContainersFromDepartment(
                    mainViewModel.selectedTravel?.id,
                    viewModel.getContainerDepartmentFrom()
                )
            }

            else -> viewModel.getContainers(mainViewModel.selectedTravel?.id)
        }
    }

    private fun navigateBackToDepartments() {
        showBackAlertDialog()
    }

    private fun showBackAlertDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.attention))
            setMessage(getString(R.string.containers_back_alert_message))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.deleteContainers(mainViewModel.selectedTravel?.id)
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentContainersBinding.bind(view)

        setupUI()
        setObserver()
    }

    private fun setupUI() {
        with(binding) {
            progressIndicator.hide()

            cvNewContainer.setOnClickListener {
                viewModel.onNewContainerClicked()
            }

            btnSignWithdrawal.setOnClickListener {
                findNavController().navigate(ContainersFragmentDirections.actionContainersFragmentToSignatureFragment())
            }

            adapter = ContainerListAdapter { container, name ->
                setFragmentResult(CONTAINER_REQUEST_KEY, bundleOf(CONTAINER_PARAM_KEY to container))
                val action =
                    ContainersFragmentDirections.actionContainersFragmentToAddContainerFragment()
                action.name = name
                findNavController().navigate(action)
            }

            rvContainerList.adapter = adapter
        }
    }

    private fun setObserver() {
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

            containers.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            newContainerVisibility.observe(viewLifecycleOwner) {
                binding.cvNewContainer.visibility = it
            }

            signButtonText.observe(viewLifecycleOwner) {
                binding.btnSignWithdrawal.text = it
            }

            navigateToAddContainer.observe(viewLifecycleOwner) {
                findNavController().navigate(
                    ContainersFragmentDirections.actionContainersFragmentToAddContainerFragment()
                )
            }

            navigateToDepartments.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_global_departmentsFragment)
            }
        }
    }
}