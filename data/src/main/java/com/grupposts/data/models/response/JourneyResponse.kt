package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class JourneyResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("arrival_at")
    val arrivalAt: String? = null,
    @SerializedName("structure")
    val structure: StructureResponse? = null,
    @SerializedName("departments")
    val departments: List<DepartmentResponse>? = null
)