package com.grupposts.trasporti.utils

import android.content.Context

fun Float.pxToDp(context: Context): Float {
    return this / context.resources.displayMetrics.density
}

fun Float.dpToPx(context: Context): Float {
    return this * context.resources.displayMetrics.density
}