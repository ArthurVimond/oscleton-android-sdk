package com.oscleton.sdk.extensions

import kotlin.math.round
import kotlin.math.roundToInt

fun Float.round(decimals: Int): Float {
    var multiplier = 1.0f
    repeat(decimals) { multiplier *= 10 }
    return round(this * multiplier) / multiplier
}