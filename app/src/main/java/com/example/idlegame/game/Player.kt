package com.example.idlegame.game


import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PlayerViewModel : ViewModel() {
    val player: Player = Player(money = 10)
    fun earnMoney() {
        viewModelScope.launch {
            player.earnMoney()
        }}

    fun buyWeapon(weapon: WeaponGame) {
        viewModelScope.launch {
            player.buyWeapon(weapon)
        }
    }

}
class Player(money: Int = 10, val weapons: MutableList<WeaponGame> = mutableListOf()) {
    var money: MutableState<Int> = mutableStateOf(money)
    fun earnMoney() {
        weapons.forEach { money.value += it.damage().toInt() }
    }

    fun buyWeapon(weapon: WeaponGame) {
        if (money.value >= weapon.upgradeCost()) {
            money.value -= weapon.upgradeCost().toInt()
            if (weapons.contains(weapon)) {
                weapon.upgrade()
            } else {
            weapons.add(weapon)
                weapon.upgrade()
            }

        }
    }

}


