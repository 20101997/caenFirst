package com.grupposts.domain.repositories

import com.grupposts.domain.models.ActionType
import com.grupposts.domain.models.Signature

interface SignatureRepository {
    suspend fun createSignature(
        journeyItemId: Int,
        departmentId: Int,
        containerIds: List<Int>,
        type: ActionType,
        signature: String,
        signatureDepartment: String
    ): Signature

    suspend fun deleteSignature(signatureId: Int): Signature
}