package com.grupposts.domain.repositories

import com.grupposts.domain.models.User

interface AuthRepository {
    suspend fun login(username: String, password: String)
    suspend fun refreshToken(token: String)
    suspend fun logout()
    suspend fun getUser(): User
}