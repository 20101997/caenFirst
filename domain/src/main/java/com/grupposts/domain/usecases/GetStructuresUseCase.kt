package com.grupposts.domain.usecases

import com.grupposts.domain.models.Structure
import com.grupposts.domain.models.Travel
import com.grupposts.domain.repositories.DepartmentRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class GetStructuresUseCase @Inject constructor(private val repository: DepartmentRepository) {

    suspend operator fun invoke(): List<Structure> {
        return repository.getStructures()
    }

}