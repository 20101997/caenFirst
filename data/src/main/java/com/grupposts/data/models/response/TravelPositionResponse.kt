package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class TravelPositionResponse (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("date")
    val date : String? = null,
    @SerializedName("travel_id")
    val travelId : Int? = null,
    @SerializedName("vehicle_id")
    val vehicleId : Int? = null,
    @SerializedName("user_id")
    val userId : Int? = null,
    @SerializedName("is_active")
    val isActive : Int? = null,
    @SerializedName("position")
    val position : List<String>? = null,
    @SerializedName("crated_at")
    val createdAt : String? = null,
    @SerializedName("updated_at")
    val updatedAt : String? = null,
    @SerializedName("deleted_at")
    val deletedAt : String? = null
)