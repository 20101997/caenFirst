package com.grupposts.data.mappers

import com.grupposts.data.models.ContainerModel
import com.grupposts.data.models.TemperatureModel
import com.grupposts.data.models.response.ContainerResponse
import com.grupposts.data.models.response.ProductResponse
import com.grupposts.domain.models.Container
import com.grupposts.domain.models.Product
import com.grupposts.domain.models.Temperature
import javax.inject.Inject

class ContainerMapper @Inject constructor(private val commonMapper: CommonMapper) {

    fun toContainer(containerResponse: ContainerResponse): Container = containerResponse.run {
        Container(
            id = id,
            code = code,
            finalTravelId = finalTravelId,
            products = products?.map { commonMapper.toProduct(it) },
            note = note,
            temperatureTracking = temperatureTracking,
            temperature = temperature?.map { commonMapper.toTemperature(it) },
            departmentFrom = departmentFrom,
            departmentTo = departmentTo
        )
    }

    fun toContainersModel(container: Container): ContainerModel = container.run {
        ContainerModel(
            code = code,
            departmentFrom = departmentFrom,
            departmentTo = departmentTo,
            temperatureTracking = temperatureTracking,
            note = note,
            products = products?.map { commonMapper.toProductResponse(it) }
        )
    }

    fun toTemperatureModel(temperature: Temperature): TemperatureModel = temperature.run {
        TemperatureModel(
            time = time,
            temperature = this.temperature
        )
    }

    fun toProductResponseList(products: List<Product>): List<ProductResponse> =
        products.map { commonMapper.toProductResponse(it) }

}