package com.example.idlegame.game

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import java.math.BigDecimal
import kotlin.math.log2
import kotlin.math.roundToInt


class WeaponGame(
    private val title: String,
    private val baseDamage: BigDecimal,
    private val baseCost: BigDecimal,
    private val damageGrowthRate: Double,
    private val costGrowthRate: Double,
    level: Int = 0,
    multiplier: Int = 1,
    private val weaponImages: List<Int>,
    private val Player: Player
) {
    var level: MutableState<Int> = mutableStateOf(level)
    var multiplier: MutableState<Int> = mutableStateOf(multiplier)
    var weaponPicture: MutableState<Int> = mutableStateOf(weaponImages.get(0))
    fun title(): String {
        return title
    }

    fun picture(indexPlus: Int = 0): Int {
        updateWeaponImage(indexPlus)
        return weaponPicture.value
    }

    private fun updateWeaponImage(indexPlus: Int = 0) {
        val upgradeLevel = (multiplier.value/2)+indexPlus
        val imageIndex = upgradeLevel.coerceAtMost(weaponImages.size - 1)
        weaponPicture.value = weaponImages[imageIndex]
    }


    fun damage(): BigDecimal {
        return if (level.value > 0) {
            val baseDamage = baseDamage.multiply(BigDecimal(1 + damageGrowthRate).pow((level.value - 1))) * BigDecimal(multiplier.value)
            baseDamage * Player.globalModifier.value
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
    fun isUpgradeMaxed(): Boolean {
        // Assuming the maximum upgrade level is the size of the weaponImages list
        return multiplier.value/2 >= weaponImages.size - 1
    }

    fun formattedMultiplierCost(): String {
        return NumberFormatter.formatLargeNumber(multiplierCost())
    }

    fun material(): String {
        val material = when (multiplier.value/ 2) {
            0 -> return "Iron"
            1 -> return "Silver"
            2 -> return "Gold"
            3 -> return "Cobalt"
            4 -> return "Mythril"
            5 -> return "Amethyst"
            6 -> return "Steel"
            7 -> return "Maxed out!"
            else -> return "Wooden"
        }
        return material
    }

    fun upgrade() {
        level.value++
    }

    fun upgradeMultiplier() {
        return if(multiplier.value==1){
            multiplier.value += 1} else {
            multiplier.value += 2
        }
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