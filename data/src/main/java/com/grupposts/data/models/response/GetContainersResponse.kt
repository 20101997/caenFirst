package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName
import com.grupposts.data.models.TemperatureModel

data class GetContainersResponse (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("code")
    val code: String? =null,
    @SerializedName("final_travel_id")
    val finalTravelId: Int? =null,
    @SerializedName("products")
    val product: ProductResponse? =null,
    @SerializedName("note")
    val note: String? =null,
    @SerializedName("temperature_tracking")
    val temperatureTracking: Boolean? =null,
    @SerializedName("temperature")
    val temperature: TemperatureModel? =null,
    @SerializedName("department_from")
    val departmentFrom: Int? =null,
    @SerializedName("department_to")
    val departmentTo: Int? =null,
    @SerializedName("created_at")
    val createdAt: String? =null,
    @SerializedName("updated_at")
    val updatedAt: String? =null,
    @SerializedName("deleted_at")
    val deletedAt: String? =null
)