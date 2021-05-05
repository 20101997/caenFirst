package com.grupposts.domain.usecases

import android.util.Patterns
import com.grupposts.domain.repositories.AuthRepository
import com.grupposts.domain.util.EmptyPasswordException
import com.grupposts.domain.util.InvalidEmailException
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(username: String?, password: String?) {
        if (username.isNullOrEmpty() || !Patterns.EMAIL_ADDRESS.matcher(username).matches()) {
            throw InvalidEmailException()
        } else if (password.isNullOrEmpty()) {
            throw EmptyPasswordException()
        } else {
            repository.login(username, password)
        }
    }

}