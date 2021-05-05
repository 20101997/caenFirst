package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName

data class SignatureCreateRequest(
    @SerializedName("department_id")
    val departmentId: Int?,
    @SerializedName("container_ids")
    val containerIds: List<Int>?,
    @SerializedName("type")
    val type: String?,
    @SerializedName("signature")
    val signature: String?,
    @SerializedName("signature_department")
    val signatureDepartment: String?
)
