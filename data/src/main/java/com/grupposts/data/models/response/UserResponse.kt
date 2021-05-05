package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("lastname")
    val lastName: String? = null,
    @SerializedName("phone")
    val phone: String? = null,
    @SerializedName("is_active")
    val isActive: Int? = null,
    @SerializedName("email")
    val email: String? = null,
    @SerializedName("email_verified_at")
    val emailVerifiedAt: String? = null,
    @SerializedName("created_at")
    val createdAt: String? = null,
    @SerializedName("updated_at")
    val updatedAt: String? = null,
    @SerializedName("deleted_at")
    val deletedAt: String? = null
)
