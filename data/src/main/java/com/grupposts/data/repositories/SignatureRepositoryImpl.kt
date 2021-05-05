package com.grupposts.data.repositories

import com.grupposts.data.api.ApiService
import com.grupposts.data.mappers.SignatureMapper
import com.grupposts.data.models.request.SignatureCreateRequest
import com.grupposts.data.utils.handleDefaultResponse
import com.grupposts.data.utils.handleResponse
import com.grupposts.domain.models.ActionType
import com.grupposts.domain.models.Signature
import com.grupposts.domain.repositories.SignatureRepository
import javax.inject.Inject

class SignatureRepositoryImpl @Inject constructor(
    private val api: ApiService,
    private val mapper: SignatureMapper
) : SignatureRepository {

    override suspend fun createSignature(
        journeyItemId: Int,
        departmentId: Int,
        containerIds: List<Int>,
        type: ActionType,
        signature: String,
        signatureDepartment: String
    ): Signature {
        val request = SignatureCreateRequest(
            departmentId = departmentId,
            containerIds = containerIds,
            type = type.value,
            signature = signature,
            signatureDepartment = signatureDepartment
        )
        val result = api.createSignature(journeyItemId, request).handleDefaultResponse()
        return mapper.toSignature(result)
    }

    override suspend fun deleteSignature(signatureId: Int): Signature {
        val result = api.deleteSignature(signatureId).handleResponse()
        return mapper.toSignature(result)
    }

}