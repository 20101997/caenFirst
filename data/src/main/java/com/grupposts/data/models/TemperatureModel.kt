package com.grupposts.data.models

import com.google.gson.annotations.SerializedName

data class TemperatureModel (
    @SerializedName("time")
    val time: String? = null,
    @SerializedName("temperature")
    val temperature: String? = null
)