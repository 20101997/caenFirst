package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.models.Temperature
import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class UpdateTemperatureUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?, containerId: Int?, temperature: List<Temperature>?): Container {
        if (travelId == null || containerId == null || temperature == null) {
            throw InvalidParamsException()
        } else {
            return repository.updateTemperature(travelId, containerId, temperature)
        }
    }

}