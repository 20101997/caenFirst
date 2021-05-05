package com.grupposts.domain.usecases

import com.grupposts.domain.models.Journey
import com.grupposts.domain.repositories.TravelRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class StartJourneyUseCase @Inject constructor(private val repository: TravelRepository) {

    suspend operator fun invoke(travelId: Int?, journeyItemId: Int?): Journey {
        if (travelId == null || journeyItemId == null) {
            throw InvalidParamsException()
        }
        return repository.startJourney(travelId, journeyItemId)
    }

}