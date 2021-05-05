package com.grupposts.domain.models

data class Token(
    val accessToken: String?,
    val expiresIn: Int?,
    val refreshToken: String?
)
