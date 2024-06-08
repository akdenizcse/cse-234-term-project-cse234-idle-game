package com.example.idlegame.game


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

    fun upgradeWeapon(weapon: WeaponGame) {
        viewModelScope.launch {
            player.upgradeWeapon(weapon)
        }
    }
}
class Player(var money: Int = 10, val weapons: MutableList<WeaponGame> = mutableListOf()) {
    fun earnMoney() {
        weapons.filter { it.isActive }.forEach { money += it.damage().toInt() }
    }

    fun buyWeapon(weapon: WeaponGame) {
        if (money >= weapon.upgradeCost()) {
            money -= weapon.upgradeCost().toInt()
            weapons.add(weapon)
            weapon.activate()
        }
    }

    fun upgradeWeapon(weapon: WeaponGame) {
        if (money >= weapon.upgradeCost()) {
            money -= weapon.upgradeCost().toInt()
            weapon.upgrade()
        }
    }
}


