package com.grupposts.domain.usecases

import com.grupposts.domain.models.Signature
import com.grupposts.domain.models.Travel
import com.grupposts.domain.repositories.SignatureRepository
import com.grupposts.domain.repositories.TravelRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class DeleteSignatureUseCase @Inject constructor(private val repository: SignatureRepository) {

    suspend operator fun invoke(signatureId: Int?): Signature {
        if(signatureId == null){
            throw InvalidParamsException()
        }
        return repository.deleteSignature(signatureId)
    }

}