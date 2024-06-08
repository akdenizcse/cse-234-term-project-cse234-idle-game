package com.example.idlegame.game

import androidx.annotation.DrawableRes
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.example.idlegame.util.NumberFormatter
import java.math.BigDecimal
import java.math.RoundingMode
import kotlin.math.log2
import kotlin.math.roundToInt

class WeaponGame(
    private val title: String,
    private val baseDamage: BigDecimal,
    private val baseCost: BigDecimal,
    private val damageGrowthRate: Double,
    private val costGrowthRate: Double,
    level: Int = 0,
    multiplier: Double = 1.0,
    private val weaponImages: List<Int>
) {
    var level: MutableState<Int> = mutableStateOf(level)
    var multiplier: MutableState<Double> = mutableStateOf(multiplier)
    var weaponPicture: MutableState<Int> = mutableStateOf(weaponImages[0])
    fun title(): String {
        return title
    }

    fun picture(): Int {
        return weaponPicture.value
    }
    private fun updateWeaponImage(indexPlus: Int = 0) {
        val upgradeLevel = log2(multiplier.value).roundToInt()+indexPlus
        weaponPicture.value = weaponImages.getOrNull(upgradeLevel) ?: weaponPicture.value
    }

    fun damage(): BigDecimal {
        return if (level.value > 0) {
            baseDamage.multiply(BigDecimal(1 + damageGrowthRate).pow((level.value - 1))) * BigDecimal(multiplier.value)
        } else {
            BigDecimal.ZERO
        }
    }

    fun upgradeCost(): BigDecimal {
        if (level.value == 0) {
            return baseCost
        }
        return baseCost.multiply(BigDecimal(1 + costGrowthRate).pow(level.value))
    }

    fun multiplierCost(): BigDecimal {
        return baseCost.multiply(BigDecimal(100)).multiply(BigDecimal(1 + costGrowthRate * 2).pow(multiplier.value.toInt()))
    }
    fun formattedDamage(): String {
        return NumberFormatter.formatLargeNumber(damage())
    }

    fun formattedUpgradeCost(): String {
        return NumberFormatter.formatLargeNumber(upgradeCost())
    }

    fun formattedMultiplierCost(): String {
        return NumberFormatter.formatLargeNumber(multiplierCost())
    }

    fun material(): String {
        val material = when (multiplier.value.toInt() / 2) {
            0 -> return "Iron"
            1 -> return "Silver"
            2 -> return "Gold"
            4 -> return "Cobalt"
            8 -> return "Mythril"
            16 -> return "Amethyst"
            32 -> return "Steel"
            else -> return "Wooden"
        }
        return material
    }

    fun upgrade() {
        level.value++
    }

    fun upgradeMultiplier() {
        multiplier.value *= 2
        updateWeaponImage()
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