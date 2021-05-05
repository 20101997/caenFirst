package com.grupposts.domain.usecases

import com.grupposts.domain.models.Travel
import com.grupposts.domain.repositories.TravelRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class GetTravelUseCase @Inject constructor(private val repository: TravelRepository) {

    suspend operator fun invoke(travelId: Int?): Travel {
        if(travelId == null){
            throw InvalidParamsException()
        }
        return repository.getTravel(travelId)
    }

}