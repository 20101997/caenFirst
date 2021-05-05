package com.grupposts.domain.models

data class Signature(
    val finalTravelStructureId: Int?,
    val departmentId: Int?,
    val containers: List<String>?,
    val type: String?,
    val signature: String?,
    val signatureDepartment: String?,
    val id: Int?
)
