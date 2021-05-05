package com.grupposts.trasporti.features.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grupposts.domain.models.Journey
import com.grupposts.domain.models.JourneyStatus
import com.grupposts.domain.models.TravelStructure
import com.grupposts.trasporti.databinding.ItemDestinationListBinding
import java.time.LocalTime

class PianificationListAdapter<T>(
    private val onJourneyClicked: ((Journey) -> Unit)? = null
) : ListAdapter<T, PianificationListAdapter.ViewHolder>(ItemDiffCallback<T>()) {

    class ViewHolder(
        val binding: ItemDestinationListBinding,
        private val onJourneyClicked: ((Journey) -> Unit)? = null
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindTravelStructure(item: TravelStructure) {
            val time = LocalTime.parse(item.arrivalAt).toString().replace(":", ".")
            binding.tvDestinationHour.append(time)
            binding.tvHospitalName.text = item.structure?.name

            binding.ivTruck.isEnabled = false

            val departments = item.departments?.map { it.name }?.joinToString(separator = ", ")
            binding.tvDepartmentsName.append(departments)
        }

        fun bindJourney(item: Journey) {
            val time = LocalTime.parse(item.arrivalAt).toString().replace(":", ".")
            binding.tvDestinationHour.append(time)
            binding.tvHospitalName.text = item.structure?.name

            binding.ivTruck.isEnabled = item.status == JourneyStatus.TRAVELLING ||
                    item.status == JourneyStatus.COMPLETED

            val departments = item.departments?.map { it.name }?.joinToString(separator = ", ")
            binding.tvDepartmentsName.append(departments)

            binding.root.setOnClickListener {
                onJourneyClicked?.invoke(item)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val binding = ItemDestinationListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding, onJourneyClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        when (item) {
            is TravelStructure -> holder.bindTravelStructure(item)
            is Journey -> holder.bindJourney(item)
        }
    }

    private class ItemDiffCallback<T> : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.toString() == newItem.toString()
        }

        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

}