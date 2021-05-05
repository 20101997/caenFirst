package com.grupposts.data.models.request

import com.google.gson.annotations.SerializedName
import com.grupposts.data.models.response.ProductResponse

data class ContainersAddProductRequest(
    @SerializedName("products")
    val products: List<ProductResponse>?
)
