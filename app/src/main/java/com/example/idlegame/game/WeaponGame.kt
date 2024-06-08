package com.example.idlegame.game

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class WeaponGame(
    private val title: String,
    @DrawableRes private val weaponPicture: Int,
    private val baseDamage: Double,
    private val baseCost: Double,
    private val damageGrowthRate: Double,
    private val costGrowthRate: Double,
    level: Int = 0,
    multiplier: Double = 1.0
) {
    var level: MutableState<Int> = mutableStateOf(level)
    var multiplier: MutableState<Double> = mutableStateOf(multiplier)
    fun title(): String {
        return title
    }

    fun picture(): Int {
        return weaponPicture
    }

    fun damage(): Double {
        return if (level.value > 0) {
            baseDamage * Math.pow(1 + damageGrowthRate, (level.value - 1).toDouble()) * multiplier.value
        } else {
            0.0
        }
    }

    fun upgradeCost(): Int {
        if (level.value == 0) {
            return baseCost.toInt()
        }
        return (baseCost * Math.pow(1 + costGrowthRate, (level.value).toDouble())).toInt()
    }

    fun upgrade() {
        level.value++

    }

    fun upgradeMultiplier() {
        multiplier.value *= 2

    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WeaponGame

        if (title != other.title) return false

        return true
    }

    override fun hashCode(): Int {
        return title.hashCode()
    }
}