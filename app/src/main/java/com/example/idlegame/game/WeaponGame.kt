package com.example.idlegame.game

class WeaponGame(
    private val baseDamage: Double,
    private val baseCost: Double,
    private val damageGrowthRate: Double,
    private val costGrowthRate: Double,
    var level: Int = 1,
    var multiplier: Double = 1.0
) {
    var isActive: Boolean = false
        private set

    fun damage(): Double {
        return if (isActive) {
            baseDamage * Math.pow(1 + damageGrowthRate, (level - 1).toDouble()) * multiplier
        } else {
            0.0
        }
    }

    fun upgradeCost(): Double {
        return baseCost * Math.pow(1 + costGrowthRate, (level - 1).toDouble())
    }

    fun activate() {
        isActive = true
    }

    fun upgrade() {
        if (isActive) {
            level++
        }
    }

    fun upgradeMultiplier() {
        if (isActive) {
            multiplier *= 2
        }
    }
}