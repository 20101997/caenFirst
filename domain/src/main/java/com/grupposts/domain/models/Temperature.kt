package com.grupposts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Temperature(
    val time: String?,
    val temperature: String?
) : Parcelable
