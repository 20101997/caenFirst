package com.grupposts.trasporti.features.departments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.grupposts.trasporti.features.MainViewModel
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentDepartmentsBinding
import com.grupposts.trasporti.utils.showError
import com.grupposts.trasporti.utils.showToolbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DepartmentsFragment : Fragment(R.layout.fragment_departments) {

    private lateinit var binding: FragmentDepartmentsBinding
    private lateinit var adapter: DepartmentListAdapter

    private val viewModel: DepartmentViewModel by viewModels()
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            navigateBackToHome()
        }

        mainViewModel.selectedJourney?.structure?.let {
            viewModel.getDepartments(it.id)
        }
    }

    override fun onResume() {
        super.onResume()
        showToolbar(mainViewModel.selectedJourney?.structure?.name, "") {
            navigateBackToHome()
        }
    }

    private fun navigateBackToHome() {
        showBackAlertDialog()
    }

    private fun showBackAlertDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.attention))
            setMessage(getString(R.string.department_back_alert_message))
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                findNavController().navigateUp()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDepartmentsBinding.bind(view)

        adapter = DepartmentListAdapter {
            mainViewModel.selectedDepartment = it
            findNavController().navigate(
                DepartmentsFragmentDirections.actionDepartmentsFragmentToDepartmentActionFragment()
            )
        }

        binding.rvDepartmentList.adapter = adapter

        setObservers()
    }

    private fun setObservers() {
        with(viewModel) {
            loading.observe(viewLifecycleOwner) {
                binding.progressBar.visibility = if (it) View.VISIBLE else View.GONE
            }

            error.observe(viewLifecycleOwner) {
                showError(it)
            }

            departments.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }
        }
    }
}