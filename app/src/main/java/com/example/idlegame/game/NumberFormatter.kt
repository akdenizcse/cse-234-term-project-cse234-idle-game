package com.example.idlegame.util

import java.math.BigDecimal
import kotlin.math.absoluteValue
import kotlin.math.floor
import kotlin.math.log10
import kotlin.math.pow

object NumberFormatter {
    fun formatLargeNumber(number: BigDecimal): String {
        val suffix = arrayOf("", "k", "m", "b", "t")
        val value = number.toDouble()
        val magnitude = value.takeIf { it != 0.0 }?.let { floor(log10(it.absoluteValue)).toInt() } ?: 0
        val tier = (magnitude / 3).takeIf { it < suffix.size } ?: suffix.size - 1
        val scaled = value / (10.0.pow(tier * 3))
        val formatted = "%.1f".format(scaled)
        return "$formatted${suffix[tier]}"
    }
}