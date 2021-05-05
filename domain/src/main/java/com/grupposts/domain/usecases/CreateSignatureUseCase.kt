package com.grupposts.domain.usecases

import com.grupposts.domain.models.ActionType
import com.grupposts.domain.models.Signature
import com.grupposts.domain.repositories.SignatureRepository
import com.grupposts.domain.util.InvalidParamsException
import javax.inject.Inject

class CreateSignatureUseCase @Inject constructor(private val repository: SignatureRepository) {

    suspend operator fun invoke(
        journeyItemId: Int?,
        departmentId: Int?,
        containerIds: List<Int>?,
        type: ActionType?,
        signature: String?,
        signatureDepartment: String?
    ): Signature {
        if (journeyItemId == null ||
            departmentId == null ||
            containerIds == null ||
            type == null ||
            signature.isNullOrEmpty() ||
            signatureDepartment.isNullOrEmpty()
        ) {
            throw InvalidParamsException()
        } else {
            val prefixUri = "data:image/png;base64,"
            val signatureUri = prefixUri + signature
            val signatureDepartmentUri = prefixUri + signatureDepartment

            return repository.createSignature(
                journeyItemId,
                departmentId,
                containerIds,
                type,
                signatureUri,
                signatureDepartmentUri
            )
        }
    }

}