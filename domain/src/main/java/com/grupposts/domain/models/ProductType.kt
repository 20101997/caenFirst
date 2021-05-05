package com.grupposts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ProductType(
    val id: Int?,
    val name: TemperatureType?,
    val description: String?
) : Parcelable
