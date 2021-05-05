package com.grupposts.domain.repositories

import com.grupposts.domain.models.Journey
import com.grupposts.domain.models.Travel
import com.grupposts.domain.models.TravelPosition

interface TravelRepository {
    suspend fun createTravel(pianificationId: Int): Travel
    suspend fun getTravel(travelId: Int): Travel
    suspend fun updateTravelPosition(travelId: Int, position: List<Double>): TravelPosition
    suspend fun startJourney(travelId: Int, journeyItemId: Int): Journey
    suspend fun stopJourney(travelId: Int, journeyItemId: Int): Journey
}