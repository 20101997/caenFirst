package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName

data class ContainersRemoveProductRequest(
    @SerializedName("product_id")
    val productId: Int?
)