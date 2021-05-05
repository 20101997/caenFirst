package com.grupposts.domain.models


import android.os.Parcelable
import kotlinx.parcelize.Parcelize
data class Structure (
    val id : Int?,
    val name :  String?,
    val address : String?,
    val city : String?,
    val cap : String ?,
    val province : String?,
    val isActive : Int?,
    val customerId : Int?
)