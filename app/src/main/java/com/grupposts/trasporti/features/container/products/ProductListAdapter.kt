package com.grupposts.trasporti.features.container.products

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grupposts.domain.models.Product
import com.grupposts.domain.models.TemperatureType
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.ItemProductCheckboxBinding

class ProductListAdapter(
    private val onClick: (Product, Boolean) -> Unit
) : ListAdapter<Product, ProductListAdapter.ViewHolder>(ProductDiffCallback) {

    class ViewHolder(
        private val binding: ItemProductCheckboxBinding,
        private val onClick: (Product, Boolean) -> Unit,
        private val context: Context
    ) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.checkboxProducts.text = item.name

            when (item.productType?.name) {
                TemperatureType.ROOM -> binding.checkboxProducts.setTextAppearance(
                    context,
                    R.style.TextAppearance_App_SemiBold_Red
                )
                TemperatureType.COLD -> binding.checkboxProducts.setTextAppearance(
                    context,
                    R.style.TextAppearance_App_SemiBold_Blue
                )
                TemperatureType.UNKNOWN -> binding.checkboxProducts.setTextAppearance(
                    context,
                    R.style.TextAppearance_App_SemiBold_Grey
                )
            }

            binding.checkboxProducts.setOnCheckedChangeListener { _, isChecked ->
                onClick(item, isChecked)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemProductCheckboxBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick, parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.name == newItem.name
        }

    }
}