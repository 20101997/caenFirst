package com.grupposts.data.repositories

import com.grupposts.data.api.ApiService
import com.grupposts.data.mappers.CommonMapper
import com.grupposts.data.utils.handleDefaultResponse
import com.grupposts.data.utils.handleResponse
import com.grupposts.domain.models.Department
import com.grupposts.domain.models.Product
import com.grupposts.domain.models.Structure
import com.grupposts.domain.repositories.DepartmentRepository
import javax.inject.Inject

class DepartmentRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: CommonMapper
) : DepartmentRepository {

    override suspend fun getStructures(): List<Structure> {
        val result = api.getStructures().handleResponse()
        return result.map { mapper.toStructure(it) }
    }

    override suspend fun getProducts(): List<Product> {
        val result = api.getProducts().handleResponse()
        return result.map { mapper.toProduct(it) }
    }

    override suspend fun getDepartmentProducts(departmentId: Int): List<Product> {
        val result = api.getDepartmentProducts(departmentId).handleDefaultResponse()
        return result.map { mapper.toProduct(it) }
    }

    override suspend fun getDepartments(structureId: Int): List<Department> {
        val result = api.getDepartments(structureId).handleResponse()
        return result.map { mapper.toDepartment(it) }
    }

}