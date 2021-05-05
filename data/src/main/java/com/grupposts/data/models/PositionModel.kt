package com.grupposts.data.models

import com.google.gson.annotations.SerializedName

data class PositionModel (
    @SerializedName("latitude")
    val latitude : String? = null,
    @SerializedName("longitude")
    val longitude : String? = null
)