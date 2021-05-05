package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class VehicleResponse (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("license_plate")
    val licensePlate : String? = null,
    @SerializedName("description")
    val description : String? = null,
    @SerializedName("is_active")
    val isActive : Int? = null,
    @SerializedName("created_at")
    val createdAt : String? = null,
    @SerializedName("updated_at")
    val updatedAt :String? = null,
    @SerializedName("deleted_at")
    val deletedAt : String? = null
)