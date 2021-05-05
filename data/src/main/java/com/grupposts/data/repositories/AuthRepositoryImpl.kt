package com.grupposts.data.repositories

import com.grupposts.data.api.ApiService
import com.grupposts.data.utils.SessionManager
import com.grupposts.data.mappers.AuthMapper
import com.grupposts.data.models.request.RefreshTokenRequest
import com.grupposts.data.models.request.TokenRequest
import com.grupposts.data.utils.handleDefaultResponse
import com.grupposts.data.utils.handleResponse
import com.grupposts.domain.models.User
import com.grupposts.domain.repositories.AuthRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: AuthMapper,
    private val sessionManager: SessionManager
) : AuthRepository {

    companion object {
        private const val CLIENT_ID = 3
        private const val CLIENT_SECRET = "d0kkU51i87E55LhcBhevb3nwpophNdeyNfgToXTC"
        private const val GRANT_TYPE_PASSWORD = "password"
        private const val GRANT_TYPE_REFRESH_TOKEN = "refresh_token"
    }

    override suspend fun login(username: String, password: String) {
        val request = TokenRequest(
            username = username,
            password = password,
            clientID = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = GRANT_TYPE_PASSWORD
        )

        val result = api.login(request).handleResponse()
        sessionManager.token = mapper.toToken(result)
    }

    override suspend fun refreshToken(token: String) {
        val request = RefreshTokenRequest(
            refreshToken = token,
            clientId = CLIENT_ID,
            clientSecret = CLIENT_SECRET,
            grantType = GRANT_TYPE_REFRESH_TOKEN
        )

        val result = api.refreshToken(request).handleResponse()
        sessionManager.token = mapper.toToken(result)
    }

    override suspend fun logout() {
        try {
            api.logout().handleResponse()
        } catch (e: Exception) {
            throw e
        } finally {
            sessionManager.token = null
        }
    }

    override suspend fun getUser(): User {
        val result = api.getUser().handleDefaultResponse()
        return mapper.toUser(result)
    }

}