package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class DepartmentResponse (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("referent_name")
    val referentName : String? = null,
    @SerializedName("referent_phone")
    val referentPhone : String? = null,
    @SerializedName("floor")
    val floor : String? = null,
    @SerializedName("structure_id")
    val structureId : Int?  = null,
    @SerializedName("deleted_at")
    val deletedAt : String? = null,
    @SerializedName("created_at")
    val createdAt : String? = null,
    @SerializedName("updated_at")
    val updatedAt : String? = null,
    @SerializedName("show_position")
    val showPosition : Int? = null,
    @SerializedName("product_categories")
    val productCategories : List<String>? = null
)