package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class DeleteTravelUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?, containerId: Int?) {
        if(travelId == null || containerId == null){
            throw InvalidParamsException()
        }
        return repository.deleteTravel(travelId, containerId)
    }

}