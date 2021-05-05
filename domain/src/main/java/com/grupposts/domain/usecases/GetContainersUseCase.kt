package com.grupposts.domain.usecases

import com.grupposts.domain.models.Container
import com.grupposts.domain.models.Travel
import com.grupposts.domain.repositories.ContainerRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class GetContainersUseCase @Inject constructor(private val repository: ContainerRepository) {

    suspend operator fun invoke(travelId: Int?): List<Container> {
        if(travelId == null){
            throw InvalidParamsException()
        }
        return repository.getContainers(travelId)
    }

}