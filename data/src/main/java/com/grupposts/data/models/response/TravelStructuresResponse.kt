package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class TravelStructuresResponse (
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("type")
    val type : String? = null,
    @SerializedName("arrival_at")
    val arrivalAt : String? = null,
    @SerializedName("structure")
    val structure : StructureResponse? = null,
    @SerializedName("departments")
    val departments : List<DepartmentResponse>? =null
)