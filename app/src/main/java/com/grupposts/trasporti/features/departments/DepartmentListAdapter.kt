package com.grupposts.trasporti.features.departments

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grupposts.domain.models.Department
import com.grupposts.trasporti.databinding.ItemDepartmentListBinding

class DepartmentListAdapter(
    private val onClick: (Department) -> Unit
) : ListAdapter<Department, DepartmentListAdapter.ViewHolder>(DepartmentDiffCallback) {

    class ViewHolder(val binding: ItemDepartmentListBinding, val onClick: (Department) -> Unit) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Department) {
            binding.tvName.text = item.name
            binding.root.setOnClickListener {
                onClick(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            ItemDepartmentListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, onClick)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    object DepartmentDiffCallback : DiffUtil.ItemCallback<Department>() {
        override fun areItemsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Department, newItem: Department): Boolean {
            return oldItem.name == newItem.name
        }

    }
}