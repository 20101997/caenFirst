package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class RemoveProductTravelUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?, containerId: Int?, product: Int?): Container {
        if (travelId == null || containerId == null || product == null) {
            throw InvalidParamsException()
        } else {
            return repository.removeProductFromContainer(travelId, containerId, product)
        }
    }

}