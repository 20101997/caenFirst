package com.grupposts.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class TemperatureType(val value: String) : Parcelable {
    ROOM("AMBIENTE"),
    COLD("FREDDO"),
    UNKNOWN("")
}