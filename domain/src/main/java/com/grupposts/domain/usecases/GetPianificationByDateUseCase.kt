package com.grupposts.domain.usecases

import com.grupposts.domain.models.Pianification
import com.grupposts.domain.repositories.PianificationRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class GetPianificationByDateUseCase @Inject constructor(private val repository: PianificationRepository) {

    suspend operator fun invoke(date: String?): Pianification {
        if (date.isNullOrEmpty()) {
            throw InvalidParamsException()
        }
        return repository.getPianificationByDate(date)
    }

}