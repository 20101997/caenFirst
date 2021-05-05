package com.grupposts.trasporti.features.container.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.navGraphViewModels
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.FragmentProductsBinding
import com.grupposts.trasporti.features.MainViewModel
import com.grupposts.trasporti.features.container.ContainerViewModel
import com.grupposts.trasporti.utils.showError
import com.grupposts.trasporti.utils.showToolbarWithSearch
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsFragment : Fragment(R.layout.fragment_products) {

    private lateinit var binding: FragmentProductsBinding
    private lateinit var adapter: ProductListAdapter

    private val viewModel: ContainerViewModel by navGraphViewModels(R.id.container_navigation) { defaultViewModelProviderFactory }
    private val mainViewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.getProducts(mainViewModel.selectedDepartment?.id)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        showToolbarWithSearch(
            title = getString(R.string.container_value, viewModel.getContainerNumber()),
            subtitle = getString(
                R.string.product_selection_label
            ),
            onSearchSubmit = { query ->
                val filteredList = adapter.currentList.filter {
                    it.name?.contains(query, true) == true
                }
                adapter.submitList(filteredList)
            },
            onSearchClear = {
                adapter.submitList(viewModel.products.value)
            }
        )
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentProductsBinding.bind(view)

        adapter = ProductListAdapter { product, isChecked ->
            viewModel.onProductChecked(product, isChecked)
        }

        binding.btnSaveAndContinue.setOnClickListener {
            viewModel.onSaveAndContinue(mainViewModel.selectedTravel?.id)
        }

        binding.btnSaveAndClose.setOnClickListener {
            viewModel.onSaveAndClose(mainViewModel.selectedTravel?.id)
        }

        binding.rvCheckableProducts.adapter = adapter

        setObservers()
    }

    private fun setObservers() {
        with(viewModel) {
            error.observe(viewLifecycleOwner) {
                showError(it)
            }

            products.observe(viewLifecycleOwner) {
                adapter.submitList(it)
            }

            singleContainer.observe(viewLifecycleOwner) {
                binding.btnSaveAndContinue.visibility = if (it) View.GONE else View.VISIBLE
            }

            navigateToScanContainer.observe(viewLifecycleOwner) {
                findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToScanContainerFragment())
            }

            navigateToContainers.observe(viewLifecycleOwner) {
                findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToContainersFragment())
            }
        }
    }
}