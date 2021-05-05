package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class SignatureResponse(
    @SerializedName("final_travel_structure_id")
    val finalTravelStructureId: Int? = null,
    @SerializedName("department_id")
    val departmentId: Int? = null,
    @SerializedName("containers")
    val containers: List<String>? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("signature")
    val signature: String? = null,
    @SerializedName("signature_department")
    val signatureDepartment: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("deleted_at")
    val deletedAt: String? = null,
    @SerializedName("id")
    val id: Int? = null
)
