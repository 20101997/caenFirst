package com.grupposts.data.mappers

import com.grupposts.data.models.response.SignatureResponse
import com.grupposts.domain.models.Signature
import javax.inject.Inject

class SignatureMapper @Inject constructor(private val commonMapper: CommonMapper) {

    fun toSignature(signatureResponse: SignatureResponse): Signature = signatureResponse.run {
        Signature(
            finalTravelStructureId = finalTravelStructureId,
            departmentId = departmentId,
            containers = containers,
            type = type,
            signature = signature,
            signatureDepartment = signatureDepartment,
            id = id
        )
    }
}