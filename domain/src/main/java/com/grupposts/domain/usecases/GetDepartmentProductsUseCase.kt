package com.grupposts.domain.usecases

import com.grupposts.domain.models.Product
import com.grupposts.domain.repositories.DepartmentRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class GetDepartmentProductsUseCase @Inject constructor(private val repository: DepartmentRepository) {

    suspend operator fun invoke(departmentId: Int?): List<Product> {
        if (departmentId == null) {
            throw InvalidParamsException()
        }
        return repository.getDepartmentProducts(departmentId)
    }

}