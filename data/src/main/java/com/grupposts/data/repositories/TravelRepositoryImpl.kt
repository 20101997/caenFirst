package com.grupposts.data.repositories

import com.grupposts.data.api.ApiService
import com.grupposts.data.mappers.TravelMapper
import com.grupposts.data.models.request.TravelUpdatePositionRequest
import com.grupposts.data.utils.handleDefaultResponse
import com.grupposts.domain.models.Journey
import com.grupposts.domain.models.Travel
import com.grupposts.domain.models.TravelPosition
import com.grupposts.domain.repositories.TravelRepository
import javax.inject.Inject

class TravelRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: TravelMapper
) : TravelRepository {

    override suspend fun createTravel(pianificationId: Int): Travel {
        val result = api.createTravel(pianificationId).handleDefaultResponse()
        return mapper.toTravel(result)
    }

    override suspend fun getTravel(travelId: Int): Travel {
        val result = api.getTravel(travelId).handleDefaultResponse()
        return mapper.toTravel(result)
    }

    override suspend fun updateTravelPosition(
        travelId: Int,
        position: List<Double>
    ): TravelPosition {
        val request = TravelUpdatePositionRequest(position)
        val result = api.updateTravelPosition(travelId, request).handleDefaultResponse()
        return mapper.toTravelUpdatePosition(result)
    }

    override suspend fun startJourney(travelId: Int, journeyItemId: Int): Journey {
        val result = api.startJourney(travelId, journeyItemId).handleDefaultResponse()
        return mapper.toJourney(result)
    }

    override suspend fun stopJourney(travelId: Int, journeyItemId: Int): Journey {
        val result = api.stopJourney(travelId, journeyItemId).handleDefaultResponse()
        return mapper.toJourney(result)
    }
}