package com.example.idlegame.game



import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.idlegame.game.NumberFormatter.formatLargeNumber
import java.math.BigDecimal


class Player(money: BigDecimal = BigDecimal("10"), gems: Int = 0, weapons: MutableList<WeaponGame> = mutableListOf()) {
    var money: MutableState<BigDecimal> = mutableStateOf(money)
    var gems: MutableState<Int> = mutableStateOf(gems)
    var weapons: MutableState<MutableList<WeaponGame>> = mutableStateOf(weapons)
    fun earnMoney(): BigDecimal {
        var moneyEarned:BigDecimal = BigDecimal("0")
        weapons.value.forEach {
            moneyEarned += it.damage()
            money.value += it.damage()
        }
        return moneyEarned
    }
    fun formattedMoney(): String {
        return NumberFormatter.formatLargeNumber(money.value)
    }

    fun addGems(amount: Int) {
        gems.value += amount
    }

    fun buyMultiplier(weapon: WeaponGame) {
        if (money.value >= weapon.multiplierCost()) {
            money.value -= weapon.multiplierCost()
            weapon.upgradeMultiplier()
        }
    }


    fun buyWeapon(weapon: WeaponGame) {
        if (money.value >= weapon.upgradeCost()) {
            money.value -= weapon.upgradeCost()
            if (!weapons.value.contains(weapon)) {
                weapons.value.add(weapon)
            }
            weapon.upgrade()
        }
    }
}

class PlayerViewModel : ViewModel() {
    val player: Player = Player(money = 10.toBigDecimal(), gems = 0)
    val earningsPerSecond: MutableState<BigDecimal> = mutableStateOf(BigDecimal.ZERO)
    val weapons: State<MutableList<WeaponGame>> get() = player.weapons
    fun earnMoney(): BigDecimal {
        var moneyEarned:BigDecimal = BigDecimal("0")
        viewModelScope.launch {
            moneyEarned = player.earnMoney()
        }
        earningsPerSecond.value = moneyEarned
        return moneyEarned
    }
    fun formattedearningsPerSecond(): String {
        return formatLargeNumber(earningsPerSecond.value)
    }


    fun addGems(amount: Int) {
        viewModelScope.launch {
            player.addGems(amount)
        }
    }

    fun buyUpgrade(weapon: WeaponGame) {
        viewModelScope.launch {
            player.buyMultiplier(weapon)
        }
    }
    fun buyWeapon(weapon: WeaponGame) {
        viewModelScope.launch {
            player.buyWeapon(weapon)
        }
    }


    fun setWeapons(weapons: List<WeaponGame>) {
        player.weapons.value = weapons.toMutableList()
    }
}