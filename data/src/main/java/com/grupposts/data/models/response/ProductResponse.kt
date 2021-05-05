package com.grupposts.data.models.response

import com.google.gson.annotations.SerializedName

data class ProductResponse (
    @SerializedName("id")
    val id : Int? = null,
    @SerializedName("name")
    val name : String? = null,
    @SerializedName("description")
    val description : String? = null,
    @SerializedName("product_type_id")
    val productTypeId :Int? = null,
    @SerializedName("is_active")
    val isActive : Int? = null,
    @SerializedName("temperature_min")
    val temperatureMin : Int? = null,
    @SerializedName("temperature_max")
    val temperatureMax : Int? = null,
    @SerializedName("created_at")
    val createdAt : String? = null,
    @SerializedName("updated_at")
    val updatedAt :String? = null,
    @SerializedName("deleted_at")
    val deletedAt : String? = null,
    @SerializedName("category_id")
    val categoryId : Int? = null,
    @SerializedName("parent_id")
    val parentId : Int? = null,
    @SerializedName("product_type")
    val productType : ProductTypeResponse? = null
)