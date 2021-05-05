package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName
import com.grupposts.data.models.PositionModel

data class TravelUpdatePositionRequest(
    @SerializedName("position")
    val position: List<Double>?
)
