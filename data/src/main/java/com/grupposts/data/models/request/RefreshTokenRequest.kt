package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @SerializedName("client_id")
    val clientId: Int?,
    @SerializedName("client_secret")
    val clientSecret: String?,
    @SerializedName("grant_type")
    val grantType: String?,
    @SerializedName("refresh_token")
    val refreshToken: String?
)
