package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName

data class TokenRequest(
    @SerializedName("username")
    val username: String?,
    @SerializedName("password")
    val password: String?,
    @SerializedName("client_id")
    val clientID: Int?,
    @SerializedName("client_secret")
    val clientSecret: String?,
    @SerializedName("grant_type")
    val grantType: String?
)
