package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class EditContainerUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?, containerId: Int?, container: Container?): Container {
        if (travelId == null || containerId == null || container == null) {
            throw InvalidParamsException()
        } else {
            return repository.editContainer(travelId, containerId, container)
        }
    }

}