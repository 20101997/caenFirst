package com.grupposts.trasporti.features.container.addcontainer

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grupposts.domain.models.Product
import com.grupposts.domain.models.TemperatureType
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.ItemSelectedProductListBinding

class SelectedProductListAdapter(
    private val onDeleteClick: (Product) -> Unit
) : ListAdapter<Product, SelectedProductListAdapter.ViewHolder>(ProductDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSelectedProductListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, parent.context, onDeleteClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    class ViewHolder(
        val binding: ItemSelectedProductListBinding,
        val context: Context,
        val onDeleteClick: (Product) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            when (item.productType?.name) {
                TemperatureType.ROOM -> binding.name.setTextAppearance(
                    context,
                    R.style.TextAppearance_App_SemiBold_Red
                )
                TemperatureType.COLD -> binding.name.setTextAppearance(
                    context,
                    R.style.TextAppearance_App_SemiBold_Blue
                )
                TemperatureType.UNKNOWN -> binding.name.setTextAppearance(
                    context,
                    R.style.TextAppearance_App_SemiBold_Grey
                )
            }
            binding.name.text = item.name

            binding.deleteButton.setOnClickListener {
                onDeleteClick(item)
            }
        }
    }

    object ProductDiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }
    }

}