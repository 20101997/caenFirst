package com.grupposts.domain.repositories

import com.grupposts.domain.models.Container
import com.grupposts.domain.models.Product
import com.grupposts.domain.models.Temperature

interface ContainerRepository {

    suspend fun createContainer(travelId: Int, container: Container): Container

    suspend fun editContainer(travelId: Int, containerId: Int, container: Container): Container

    suspend fun deleteContainers(travelId: Int, containerIds: List<Int>)

    suspend fun getContainers(travelId: Int): List<Container>

    suspend fun getDepartmentContainers(
        travelId: Int,
        departmentFrom: Int?,
        departmentTo: Int?
    ): List<Container>

    suspend fun deleteTravel(travelId: Int, containerId: Int)

    suspend fun addProductsToContainer(
        travelId: Int,
        containerId: Int,
        products: List<Product>
    ): Container

    suspend fun removeProductFromContainer(
        travelId: Int,
        containerId: Int,
        productId: Int
    ): Container

    suspend fun updateTemperature(
        travelId: Int,
        containerId: Int,
        temperatures: List<Temperature>
    ): Container

}