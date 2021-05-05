package com.grupposts.data.mappers

import com.grupposts.data.models.response.*
import com.grupposts.domain.models.*
import javax.inject.Inject

class PianificationMapper @Inject constructor(private val commonMapper: CommonMapper) {

    fun toPianification(pianificationResponse: PianificationResponse): Pianification =
        pianificationResponse.run {
            Pianification(
                id = id,
                date = date,
                vehicle = vehicle?.map { commonMapper.toVehicle(it) },
                travelStructures = travelStructures?.map { toTravelStructure(it) }
            )
        }

    fun toTravelStructure(travelStructuresResponse: TravelStructuresResponse): TravelStructure = travelStructuresResponse.run {
        TravelStructure(
            id =id,
            type = type,
            arrivalAt = arrivalAt,
            structure = structure?.let { commonMapper.toStructure(it) },
            departments = departments?.map { commonMapper.toDepartment(it) }
        )
    }

}