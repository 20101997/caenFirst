package com.grupposts.data.mappers

import com.grupposts.data.models.response.TokenResponse
import com.grupposts.data.models.response.UserResponse
import com.grupposts.domain.models.Token
import com.grupposts.domain.models.User
import javax.inject.Inject

class AuthMapper @Inject constructor() {

    fun toToken(tokenResponse: TokenResponse): Token = tokenResponse.run {
        Token(
            accessToken = accessToken,
            expiresIn = expiresIn,
            refreshToken = refreshToken
        )
    }

    fun toUser(userResponse: UserResponse): User = userResponse.run {
        User(
            id = id,
            name = name,
            lastName = lastName,
            phone = phone,
            isActive = isActive,
            email = email
        )
    }

}