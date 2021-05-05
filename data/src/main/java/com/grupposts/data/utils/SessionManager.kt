package com.grupposts.data.utils

import android.content.SharedPreferences
import androidx.core.content.edit
import com.grupposts.domain.models.Token
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SessionManager @Inject constructor(private val sharedPreferences: SharedPreferences) {

    companion object {
        private const val TOKEN_KEY = "token-key"
        private const val EXPIRES_IN_KEY = "expires-in-key"
        private const val REFRESH_TOKEN_KEY = "refresh-token-key"
    }

    var token: Token? = null
        get() = Token(
            accessToken = sharedPreferences.getString(TOKEN_KEY, null),
            expiresIn = sharedPreferences.getInt(EXPIRES_IN_KEY, -1),
            refreshToken = sharedPreferences.getString(REFRESH_TOKEN_KEY, null)
        )
        set(value) {
            field = value
            sharedPreferences.edit {
                putString(TOKEN_KEY, value?.accessToken)
                putInt(EXPIRES_IN_KEY, value?.expiresIn ?: -1)
                putString(REFRESH_TOKEN_KEY, value?.refreshToken)
            }
        }

}