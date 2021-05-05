package com.grupposts.data.models

import com.google.gson.annotations.SerializedName

data class DefaultResponse<T>(
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("data")
    val data: T? = null,
    @SerializedName("message")
    val message: String? = null
)