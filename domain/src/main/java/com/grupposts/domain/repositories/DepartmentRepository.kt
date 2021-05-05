package com.grupposts.domain.repositories

import com.grupposts.domain.models.*

interface DepartmentRepository {
    suspend fun getStructures(): List<Structure>
    suspend fun getProducts(): List<Product>
    suspend fun getDepartmentProducts(departmentId: Int): List<Product>
    suspend fun getDepartments(structureId: Int): List<Department>
}