package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class TravelResponse (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("date")
    val date : String? = null,
    @SerializedName("vehicle")
    val vehicle : VehicleResponse? = null,
    @SerializedName("journey")
    val journeys : List<JourneyResponse>? = null
)