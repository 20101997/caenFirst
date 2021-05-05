package com.grupposts.data.models

import com.google.gson.annotations.SerializedName

data class ProductModel(
    @SerializedName("product_id")
    val productId: Int?,
    @SerializedName("product_description")
    val productDescription: String?,
    @SerializedName("product_serial")
    val productSerial: String?
)
