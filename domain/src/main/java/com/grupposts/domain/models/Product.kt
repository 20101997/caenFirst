package com.grupposts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Product(
    val id: Int?,
    val name: String?,
    val description: String?,
    val productTypeId: Int?,
    val isActive: Int?,
    val temperatureMin: Int?,
    val temperatureMax: Int?,
    val categoryId: Int?,
    val parentId: Int?,
    val productType: ProductType?
) : Parcelable
