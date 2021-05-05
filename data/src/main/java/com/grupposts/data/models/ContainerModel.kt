package com.grupposts.data.models

import com.google.gson.annotations.SerializedName
import com.grupposts.data.models.response.ProductResponse

data class ContainerModel(
    @SerializedName("code")
    val code: String?,
    @SerializedName("department_from")
    val departmentFrom: Int?,
    @SerializedName("department_to")
    val departmentTo: Int?,
    @SerializedName("temperature_tracking")
    val temperatureTracking: Int?,
    @SerializedName("note")
    val note: String?,
    @SerializedName("products")
    val products: List<ProductResponse>?
)