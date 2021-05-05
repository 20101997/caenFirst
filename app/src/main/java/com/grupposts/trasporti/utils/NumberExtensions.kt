package com.grupposts.trasporti.utils

fun Int.toStringTwoDigits(): String = if (this < 10)
    String.format("%02d", this)
else
    toString()