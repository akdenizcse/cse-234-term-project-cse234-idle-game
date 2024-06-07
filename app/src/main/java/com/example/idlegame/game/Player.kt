package com.example.idlegame.game


import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableDoubleStateOf

class PlayerViewModel : ViewModel() {
    val player: Player = Player(money = 10.0)
    val weapons: State<MutableList<WeaponGame>> get() = player.weapons
    fun earnMoney() {
        viewModelScope.launch {
            player.earnMoney()
        }}

    fun buyWeapon(weapon: WeaponGame) {
        viewModelScope.launch {
            player.buyWeapon(weapon)
        }
    }

    fun setWeapons(weapons: List<WeaponGame>) {
        player.weapons.value = weapons.toMutableList()
    }
}
class Player(money: Double, weapons: MutableList<WeaponGame> = mutableListOf()) {
    var money: MutableState<Double> = mutableDoubleStateOf(money)
    var weapons: MutableState<MutableList<WeaponGame>> = mutableStateOf(weapons)
    fun earnMoney() {
        weapons.value.forEach { money.value += it.damage() }
    }

    fun buyWeapon(weapon: WeaponGame) {
        if (money.value >= weapon.upgradeCost()) {
            money.value -= weapon.upgradeCost()
            if (weapons.value.contains(weapon)) {
                weapon.upgrade()
            } else {
                weapons.value.add(weapon)
                weapon.upgrade()
            }

        }
    }

}


