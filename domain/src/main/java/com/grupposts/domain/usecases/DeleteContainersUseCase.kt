package com.grupposts.domain.usecases

import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class DeleteContainersUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?, containerIds: List<Int>?) {
        if (travelId == null || containerIds.isNullOrEmpty()) {
            throw InvalidParamsException()
        } else {
            return repository.deleteContainers(travelId, containerIds)
        }
    }

}