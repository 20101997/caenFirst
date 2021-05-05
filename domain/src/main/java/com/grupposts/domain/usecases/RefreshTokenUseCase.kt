package com.grupposts.domain.usecases

import com.grupposts.domain.repositories.AuthRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class RefreshTokenUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(refreshToken: String?) {
        if (refreshToken.isNullOrEmpty()) {
            throw InvalidParamsException()
        } else {
            repository.refreshToken(refreshToken)
        }
    }

}