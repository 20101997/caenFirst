package com.grupposts.domain.usecases

import com.grupposts.domain.repositories.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke() {
        return repository.logout()
    }

}