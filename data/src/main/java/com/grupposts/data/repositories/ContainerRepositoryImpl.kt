package com.grupposts.data.repositories

import com.grupposts.data.api.ApiService
import com.grupposts.data.mappers.ContainerMapper
import com.grupposts.data.models.request.*
import com.grupposts.data.utils.handleDefaultResponse
import com.grupposts.data.utils.handleResponse
import com.grupposts.domain.models.Container
import com.grupposts.domain.models.Product
import com.grupposts.domain.models.Temperature
import com.grupposts.domain.repositories.ContainerRepository
import javax.inject.Inject

class ContainerRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: ContainerMapper
) : ContainerRepository {

    override suspend fun createContainer(
        travelId: Int,
        container: Container
    ): Container {
        val request = ContainersRequest(mapper.toContainersModel(container))
        val result = api.createContainer(travelId, request).handleDefaultResponse()
        return mapper.toContainer(result)
    }

    override suspend fun editContainer(
        travelId: Int,
        containerId: Int,
        container: Container
    ): Container {
        val request = ContainersRequest(mapper.toContainersModel(container))
        val result = api.editContainer(travelId, containerId, request).handleDefaultResponse()
        return mapper.toContainer(result)
    }

    override suspend fun deleteContainers(travelId: Int, containerIds: List<Int>) {
        val request = DeleteContainersRequest(containerIds)
        api.deleteContainers(travelId, request).handleResponse()
    }

    override suspend fun getContainers(travelId: Int): List<Container> {
        val result = api.getContainers(travelId).handleDefaultResponse()
        return result.map { mapper.toContainer(it) }
    }

    override suspend fun getDepartmentContainers(
        travelId: Int,
        departmentFrom: Int?,
        departmentTo: Int?
    ): List<Container> {
        val request = DepartmentContainersRequest(departmentFrom, departmentTo)
        val result = api.getDepartmentContainers(travelId, request).handleDefaultResponse()
        return result.map { mapper.toContainer(it) }
    }

    override suspend fun deleteTravel(travelId: Int, containerId: Int) {
        api.deleteTravel(travelId, containerId).handleResponse()
    }

    override suspend fun addProductsToContainer(
        travelId: Int,
        containerId: Int,
        products: List<Product>
    ): Container {
        val request = ContainersAddProductRequest(mapper.toProductResponseList(products))
        val result = api.addProductsToContainer(travelId, containerId, request)
            .handleDefaultResponse()
        return mapper.toContainer(result)
    }

    override suspend fun removeProductFromContainer(
        travelId: Int,
        containerId: Int,
        productId: Int
    ): Container {
        val result = api.removeProductsFromContainer(
            travelId,
            containerId,
            ContainersRemoveProductRequest(productId)
        ).handleDefaultResponse()
        return mapper.toContainer(result)
    }

    override suspend fun updateTemperature(
        travelId: Int,
        containerId: Int,
        temperatures: List<Temperature>
    ): Container {
        val list = temperatures.map { mapper.toTemperatureModel(it) }
        val request = ContainersUpdateTemperatureRequest(list)
        val result = api.updateTemperature(travelId, containerId, request).handleDefaultResponse()
        return mapper.toContainer(result)
    }

}