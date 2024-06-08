package com.example.idlegame.game


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf

class Player(money: Double = 10.0, gems: Int = 0, weapons: MutableList<WeaponGame> = mutableListOf()) {
    var money: MutableState<Double> = mutableDoubleStateOf(money)
    var gems: MutableState<Int> = mutableStateOf(gems)
    var weapons: MutableState<MutableList<WeaponGame>> = mutableStateOf(weapons)
    fun earnMoney(): Double {
        var moneyEarned = 0.0
        weapons.value.forEach {
            moneyEarned += it.damage()
            money.value += it.damage()
        }
        return moneyEarned
    }

    fun addGems(amount: Int) {
        gems.value += amount
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
    val player: Player = Player(money = 10.0, gems = 0)
    val earningsPerSecond: MutableState<Double> = mutableStateOf(0.0)
    val weapons: State<MutableList<WeaponGame>> get() = player.weapons
    fun earnMoney(): Double {
        var moneyEarned = 0.0
        viewModelScope.launch {
            moneyEarned = player.earnMoney()
        }
        return moneyEarned
    }

    fun addGems(amount: Int) {
        viewModelScope.launch {
            player.addGems(amount)
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