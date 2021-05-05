package com.grupposts.domain.usecases

import com.grupposts.domain.models.Pianification
import com.grupposts.domain.models.User
import com.grupposts.domain.repositories.PianificationRepository
import javax.inject.Inject

class GetPianificationsUseCase @Inject constructor(private val repository: PianificationRepository) {

    suspend operator fun invoke(): List<Pianification> {
        return repository.getPianifications()
    }

}