package com.grupposts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Container(
    val id: Int?,
    val code: String?,
    val finalTravelId: Int?,
    val products: List<Product>?,
    val note: String?,
    val temperatureTracking: Int?,
    val temperature: List<Temperature>?,
    val departmentFrom: Int?,
    val departmentTo: Int?
) : Parcelable
