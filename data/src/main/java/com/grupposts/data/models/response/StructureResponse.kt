package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class StructureResponse (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("name")
    val name :  String? = null,
    @SerializedName("address")
    val address : String? = null,
    @SerializedName("city")
    val city : String? = null,
    @SerializedName("cap")
    val cap : String ? = null,
    @SerializedName("province")
    val province : String? = null,
    @SerializedName("is_active")
    val isActive : Int? = null,
    @SerializedName("customer_id")
    val customerId : Int? = null,
    @SerializedName("deleted_at")
    val deletedAt : String? = null,
    @SerializedName("created_at")
    val createdAt : String? = null,
    @SerializedName("updated_at")
    val updatedAt :String? = null
)