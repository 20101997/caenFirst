package com.grupposts.domain.usecases

import com.grupposts.domain.models.TravelPosition
import com.grupposts.domain.repositories.TravelRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class UpdateTravelPositionUseCase @Inject constructor(private val repository: TravelRepository) {

    suspend operator fun invoke(travelId: Int?, position: List<Double>?): TravelPosition {
        if (travelId == null || position.isNullOrEmpty()) {
            throw InvalidParamsException()
        } else {
            return repository.updateTravelPosition(travelId, position)
        }
    }

}