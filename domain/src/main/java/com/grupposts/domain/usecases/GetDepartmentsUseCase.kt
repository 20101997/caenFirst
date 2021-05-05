package com.grupposts.domain.usecases

import com.grupposts.domain.models.Department
import com.grupposts.domain.repositories.DepartmentRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class GetDepartmentsUseCase @Inject constructor(private val repository: DepartmentRepository) {

    suspend operator fun invoke(structureId: Int?): List<Department> {
        if(structureId == null){
            throw InvalidParamsException()
        }
        return repository.getDepartments(structureId)
    }

}