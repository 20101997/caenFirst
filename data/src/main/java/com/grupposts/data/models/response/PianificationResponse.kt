package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class PianificationResponse (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("date")
    val date : String? = null,
    @SerializedName("vehicle")
    val vehicle : List<VehicleResponse>? = null,
    @SerializedName("travelStructures")
    val travelStructures : List<TravelStructuresResponse>? = null
)