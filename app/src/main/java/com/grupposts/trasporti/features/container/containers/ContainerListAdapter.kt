package com.grupposts.trasporti.features.container.containers

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grupposts.domain.models.Container
import com.grupposts.domain.models.TemperatureType
import com.grupposts.trasporti.R
import com.grupposts.trasporti.databinding.ItemContainerListBinding
import com.grupposts.trasporti.utils.toStringTwoDigits

class ContainerListAdapter(
    private val onClick: (Container, String) -> Unit
) : ListAdapter<Container, ContainerListAdapter.ViewHolder>(ContainerDiffCallback) {

    class ViewHolder(
        val binding: ItemContainerListBinding,
        val context: Context,
        val onClick: (Container, String) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Container, position: Int) {
            val containerName = context.getString(
                R.string.container_value,
                (position + 1).toStringTwoDigits()
            )

            binding.tvNameStructure.text = containerName

            item.products?.forEach {
                val textView = TextView(context)
                textView.text = it.name
                when (it.productType?.name) {
                    TemperatureType.ROOM -> textView.setTextAppearance(
                        context,
                        R.style.TextAppearance_App_SemiBold_Red
                    )
                    TemperatureType.COLD -> textView.setTextAppearance(
                        context,
                        R.style.TextAppearance_App_SemiBold_Blue
                    )
                    TemperatureType.UNKNOWN -> textView.setTextAppearance(
                        context,
                        R.style.TextAppearance_App_SemiBold_Grey
                    )
                }
                textView.textSize = 14f
                binding.llProductsContainer.addView(textView)
            }

            if (item.temperatureTracking == 1) {
                binding.ivThermometer.visibility = View.VISIBLE
            } else {
                binding.ivThermometer.visibility = View.GONE
            }

            binding.root.setOnClickListener {
                onClick(item, containerName)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemContainerListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, parent.context, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, position)
    }

    object ContainerDiffCallback : DiffUtil.ItemCallback<Container>() {
        override fun areItemsTheSame(oldItem: Container, newItem: Container): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Container, newItem: Container): Boolean {
            return oldItem.temperatureTracking == newItem.temperatureTracking &&
                    oldItem.products?.equals(newItem.products) ?: false
        }
    }

}
