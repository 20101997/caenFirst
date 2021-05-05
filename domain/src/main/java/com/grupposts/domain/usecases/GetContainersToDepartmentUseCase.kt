package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class GetContainersToDepartmentUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?, departmentId: Int?): List<Container> {
        if (travelId == null || departmentId == null) {
            throw InvalidParamsException()
        }
        return repository.getDepartmentContainers(travelId, null, departmentId)
    }

}