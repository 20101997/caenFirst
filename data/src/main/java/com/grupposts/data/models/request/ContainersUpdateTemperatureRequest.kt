package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName
import com.grupposts.data.models.TemperatureModel

data class ContainersUpdateTemperatureRequest(
    @SerializedName("temperatures")
    val temperatures: List<TemperatureModel>?
)
