package com.persado.sdkexample.util

import java.text.NumberFormat
import java.util.Locale
import kotlin.math.pow

fun Double.format(digits: Int): String {
    val rounded = (this * 10f.pow(digits)) / 10f.pow(digits)
    val numberFormat = NumberFormat.getCurrencyInstance(Locale.US)
    return try {
        numberFormat.format(rounded)
    } catch (_: Exception) {
        ""
    }
}