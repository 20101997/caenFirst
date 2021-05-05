package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse (
    @SerializedName("status")
    val status : String?= null,
    @SerializedName("message")
    val message : String? = null
)