package com.grupposts.domain.usecases

import com.grupposts.domain.models.Product
import com.grupposts.domain.repositories.DepartmentRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(private val repository: DepartmentRepository) {

    suspend operator fun invoke(): List<Product> {
        return repository.getProducts()
    }

}