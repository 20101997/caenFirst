package com.grupposts.data.mappers

import com.grupposts.data.models.TemperatureModel
import com.grupposts.data.models.response.*
import com.grupposts.domain.models.*
import javax.inject.Inject

class CommonMapper @Inject constructor() {

    fun toVehicle(vehicleResponse: VehicleResponse): Vehicle = vehicleResponse.run {
        Vehicle(
            id = id,
            name = name,
            licensePlate = licensePlate,
            description = description,
            isActive = isActive
        )
    }

    fun toStructure(structureResponse: StructureResponse): Structure = structureResponse.run {
        Structure(
            id = id,
            name = name,
            address = address,
            city = city,
            cap = cap,
            province = province,
            isActive = isActive,
            customerId = customerId
        )
    }

    fun toDepartment(departmentResponse: DepartmentResponse): Department =
        departmentResponse.run {
            Department(
                id = id,
                name = name,
                referentName = referentName,
                referentPhone = referentPhone,
                floor = floor,
                structureId = structureId,
                showPosition = showPosition,
                productCategorise = productCategories
            )
        }

    fun toProduct(productResponse: ProductResponse): Product = productResponse.run {
        Product(
            id = id,
            name = name,
            description = description,
            productTypeId = productTypeId,
            isActive = isActive,
            temperatureMin = temperatureMin,
            temperatureMax = temperatureMax,
            categoryId = categoryId,
            parentId = parentId,
            productType = productType?.let { toProductType(it) }
        )
    }

    private fun toProductType(productTypeResponse: ProductTypeResponse): ProductType =
        productTypeResponse.run {
            ProductType(
                id = id,
                name = when (name) {
                    "AMBIENTE" -> TemperatureType.ROOM
                    "FREDDO" -> TemperatureType.COLD
                    else -> TemperatureType.UNKNOWN
                },
                description = description
            )
        }

    private fun toProductTypeResponse(productType: ProductType): ProductTypeResponse =
        productType.run {
            ProductTypeResponse(
                id = id,
                name = name?.value,
                description = description
            )
        }

    fun toTemperature(temperatureModel: TemperatureModel): Temperature = temperatureModel.run {
        Temperature(
            time = time,
            temperature = temperature
        )
    }

    fun toProductResponse(product: Product): ProductResponse = product.run {
        ProductResponse(
            id = id,
            name = name,
            description = description,
            productTypeId = productTypeId,
            isActive = isActive,
            temperatureMin = temperatureMin,
            temperatureMax = temperatureMax,
            categoryId = categoryId,
            parentId = parentId,
            productType = productType?.let { toProductTypeResponse(it) }
        )
    }
}