package com.example.idlegame.game

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import androidx.compose.runtime.State
import com.example.idlegame.R
import com.example.idlegame.TimeAPI.WorldTime
import com.example.idlegame.componentbutton.Check
import com.example.idlegame.game.NumberFormatter.formatLargeNumber
import kotlinx.coroutines.runBlocking
import java.math.BigDecimal

class Player(context: Context, money: BigDecimal = BigDecimal("100"), gems: Int = 0, weapons: MutableList<WeaponGame> = mutableListOf(), soundState: MutableState<Boolean> ) {
    var money: MutableState<BigDecimal> = mutableStateOf(money)
    var gems: MutableState<Int> = mutableStateOf(gems)
    var weapons: MutableState<MutableList<WeaponGame>> = mutableStateOf(weapons)
    var globalModifier: MutableState<BigDecimal> = mutableStateOf(BigDecimal("1"))
    var worldTime: WorldTime = WorldTime()
    var lastActiveTime: MutableState<Long> = mutableStateOf(System.currentTimeMillis())
    private val clickSound: MediaPlayer = MediaPlayer.create(context, R.raw.click)
    private val errorSound: MediaPlayer = MediaPlayer.create(context, R.raw.error)
    val soundState: MutableState<Boolean> = soundState

    fun getCurrentTime(): Long {
        var currentTimeMillis: Long = 0
        runBlocking {
            val currentTime = worldTime.getCurrentTime()
            if (currentTime != null) {
                currentTimeMillis = currentTime.unixtime * 1000 // Convert to milliseconds
            }
        }
        Log.d("CurrentTime", "Current time in milliseconds: $currentTimeMillis")
        return currentTimeMillis
    }

    fun earnMoney(): BigDecimal {
        var moneyEarned: BigDecimal = BigDecimal("0")
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
            if (soundState.value == true) {
                clickSound.start()
            }
        } else {
            if (soundState.value == true) {
                errorSound.start()
            }
        }
    }

    fun buyWeapon(weapon: WeaponGame) {
        if (money.value >= weapon.upgradeCost()) {
            money.value -= weapon.upgradeCost()
            if (soundState.value == true) {
                clickSound.start()
            }
            if (!weapons.value.contains(weapon)) {
                weapons.value.add(weapon)
            }
            weapon.upgrade()
        } else {
            if (soundState.value == true) {
                errorSound.start()
            }
        }
    }


}


class PlayerViewModel(context: Context, soundState: MutableState<Boolean>) : ViewModel() { // Added soundState parameter
    val player: Player = Player(context, money = 100.toBigDecimal(), gems = 0, soundState = soundState)
    val earningsPerSecond: MutableState<BigDecimal> = mutableStateOf(BigDecimal.ZERO)
    val weapons: State<MutableList<WeaponGame>> get() = player.weapons


    fun updateSoundState() {
        player.soundState.value = !player.soundState.value

    }


    fun getOfflineEarnings(): BigDecimal {
        val currentTime = player.getCurrentTime()
        val offlineTimeInSeconds = (currentTime - player.lastActiveTime.value) / 1000
        val offlineEarnings = earnMoney() * BigDecimal(offlineTimeInSeconds)
        player.money.value += offlineEarnings
        return if (offlineTimeInSeconds != 0L) {
            offlineEarnings / BigDecimal(offlineTimeInSeconds)
        } else {
            BigDecimal.ZERO
        }
    }

    fun earnMoneyForSeconds(seconds: Int) {
        val moneyEarned = earningsPerSecond.value * BigDecimal(seconds)
        player.money.value += moneyEarned
    }

    fun earnMoney(): BigDecimal {
        var moneyEarned: BigDecimal = BigDecimal("0")
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
