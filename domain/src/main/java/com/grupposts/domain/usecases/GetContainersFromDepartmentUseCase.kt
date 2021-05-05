package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.repositories.ContainerRepository
import java.security.InvalidParameterException
import javax.inject.Inject

class GetContainersFromDepartmentUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?, departmentId: Int?): List<Container> {
        if (travelId == null || departmentId == null) {
            throw InvalidParameterException()
        }
        return repository.getDepartmentContainers(travelId, departmentId, null)
    }

}