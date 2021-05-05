package com.grupposts.data.models

import com.google.gson.annotations.SerializedName

data class ErrorModel(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("message")
    val message: String? = null
)
