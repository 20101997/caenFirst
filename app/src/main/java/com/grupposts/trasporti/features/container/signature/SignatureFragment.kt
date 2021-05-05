package com.grupposts.trasporti.features.container.signature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentSignatureBinding
import com.grupposts.trasporti.features.MainViewModel
import com.grupposts.trasporti.features.container.ContainerViewModel
import com.grupposts.trasporti.utils.showError
import com.grupposts.trasporti.utils.showToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignatureFragment : Fragment(R.layout.fragment_signature) {

    private lateinit var binding: FragmentSignatureBinding

    private val viewModel: ContainerViewModel by navGraphViewModels(R.id.container_navigation) { defaultViewModelProviderFactory }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showToolbar(
            viewModel.getSignatureTitle(),
            mainViewModel.selectedDepartment?.name
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSignatureBinding.bind(view)

        setupUI()
        setObservers()
    }

    private fun setupUI() {
        with(binding) {
            btnCloseWithdrawal.setOnClickListener {
                viewModel.createSignature(
                    mainViewModel.selectedJourney?.id,
                    mainViewModel.selectedDepartment?.id,
                    svSignatureDriver.getSignatureBitmap(),
                    svSignatureDepartment.getSignatureBitmap()
                )
            }

            ibDeleteDepartment.setOnClickListener {
                svSignatureDepartment.resetView()
            }

            ibDeleteDriver.setOnClickListener {
                svSignatureDriver.resetView()
            }
        }
    }

    private fun setObservers() {
        with(viewModel) {
            error.observe(viewLifecycleOwner) {
                showError(it)
            }

            closeButtonText.observe(viewLifecycleOwner) {
                binding.btnCloseWithdrawal.text = it
            }

            navigateToDepartments.observe(viewLifecycleOwner) {
                findNavController().navigate(R.id.action_global_departmentsFragment)
            }
        }
    }
}