package com.grupposts.trasporti.utils

import java.time.LocalDate

fun LocalDate?.greaterThan(date: LocalDate): Boolean = this?.let {
    this > date
} ?: false