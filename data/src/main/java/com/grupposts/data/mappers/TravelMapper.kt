package com.grupposts.data.mappers

import com.grupposts.data.models.response.JourneyResponse
import com.grupposts.data.models.response.TravelResponse
import com.grupposts.data.models.response.TravelPositionResponse
import com.grupposts.domain.models.Journey
import com.grupposts.domain.models.JourneyStatus
import com.grupposts.domain.models.Travel
import com.grupposts.domain.models.TravelPosition
import javax.inject.Inject

class TravelMapper @Inject constructor(private val commonMapper: CommonMapper) {

    fun toTravel(travelResponse: TravelResponse): Travel = travelResponse.run {
        Travel(
            id = id,
            date = date,
            vehicle = vehicle?.let { commonMapper.toVehicle(it) },
            journeys = journeys?.map { toJourney(it) }
        )
    }

    fun toTravelUpdatePosition(travelPositionResponse: TravelPositionResponse): TravelPosition =
        travelPositionResponse.run {
            TravelPosition(
                id = id,
                date = date,
                travelId = travelId,
                vehicleId = vehicleId,
                userId = userId,
                isActive = isActive,
                position = position
            )
        }

    fun toJourney(journeyResponse: JourneyResponse): Journey = journeyResponse.run {
        Journey(
            id = id,
            status = when (status) {
                "pending" -> JourneyStatus.PENDING
                "travelling" -> JourneyStatus.TRAVELLING
                "completed" -> JourneyStatus.COMPLETED
                else -> JourneyStatus.UNKNOWN
            },
            type = type,
            arrivalAt = arrivalAt,
            structure = structure?.let { commonMapper.toStructure(it) },
            departments = departments?.map { commonMapper.toDepartment(it) }
        )
    }

}