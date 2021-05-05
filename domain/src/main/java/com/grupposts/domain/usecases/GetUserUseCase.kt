package com.grupposts.domain.usecases

import com.grupposts.domain.models.User
import com.grupposts.domain.repositories.AuthRepository
import javax.inject.Inject

class GetUserUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(): User {
        return repository.getUser()
    }

}