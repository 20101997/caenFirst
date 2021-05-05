package com.grupposts.domain.usecases

import com.grupposts.domain.models.Travel
import com.grupposts.domain.repositories.TravelRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class CreateTravelUseCase @Inject constructor(private val repository: TravelRepository) {

    suspend operator fun invoke(pianificationId: Int?): Travel {
        if (pianificationId == null) {
            throw InvalidParamsException()
        }
        return repository.createTravel(pianificationId)
    }

}