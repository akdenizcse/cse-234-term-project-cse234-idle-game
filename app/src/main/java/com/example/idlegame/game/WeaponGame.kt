package com.example.idlegame.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.math.BigDecimal
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.roundToInt


class WeaponGame(
    private val title: String,
    private val baseDamage: BigDecimal,
    private val baseCost: BigDecimal,
    private val costGrowthRate: Double,
    level: Int = 0,
    multiplier: Int = 1,
    private val weaponImages: List<Int>,
    private val player: Player
) {
    var level: MutableState<Int> = mutableStateOf(level)
    var multiplier: MutableState<Int> = mutableStateOf(multiplier)
    var weaponPicture: MutableState<Int> = mutableStateOf(weaponImages[0])

    fun title(): String = title

    fun picture(indexPlus: Int = 0): Int {
        updateWeaponImage(indexPlus)
        return weaponPicture.value
    }

    private fun updateWeaponImage(indexPlus: Int = 0) {
        val upgradeLevel = (multiplier.value / 2) + indexPlus
        val imageIndex = upgradeLevel.coerceAtMost(weaponImages.size - 1)
        weaponPicture.value = weaponImages[imageIndex]
    }

    fun damage(): BigDecimal {
        return if (level.value > 0) {
            baseDamage * level.value.toBigDecimal() * player.globalModifier.value * BigDecimal(multiplier.value)
        } else {
            BigDecimal.ZERO
        }
    }

    fun upgradeCost(): BigDecimal {
        return if (level.value == 0) {
            baseCost
        } else {
            baseCost * costGrowthRate.toBigDecimal().pow(level.value)
        }
    }

    fun multiplierCost(): BigDecimal {
        return baseCost * costGrowthRate.toBigDecimal() * BigDecimal(100) * BigDecimal(2.0.pow(multiplier.value.toDouble()).roundToInt())
    }

    fun formattedDamage(): String = NumberFormatter.formatLargeNumber(damage())

    fun formattedUpgradeCost(): String = NumberFormatter.formatLargeNumber(upgradeCost())

    fun isUpgradeMaxed(): Boolean = multiplier.value / 2 >= weaponImages.size - 1

    fun formattedMultiplierCost(): String = NumberFormatter.formatLargeNumber(multiplierCost())

    fun material(): String {
        return when (multiplier.value / 2) {
            0 -> "Iron"
            1 -> "Silver"
            2 -> "Gold"
            3 -> "Cobalt"
            4 -> "Mythril"
            5 -> "Amethyst"
            6 -> "Steel"
            7 -> "Maxed out!"
            else -> "Wooden"
        }
    }

    fun upgrade() {
        level.value++
    }

    fun upgradeMultiplier() {
        multiplier.value += if (multiplier.value == 1) 1 else 2
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