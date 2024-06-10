package com.example.idlegame.screen

import Enemy
import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idlegame.R
import com.example.idlegame.game.PlayerViewModel
import com.example.idlegame.game.WeaponGame
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.weapon.Weapon
import java.math.BigDecimal
import java.util.Locale

@SuppressLint("UnrememberedMutableState")
@Composable
fun WeaponsScreen(enemy: Enemy, playerViewModel: PlayerViewModel) {

    val weapons = listOf(
        WeaponGame("Sword",BigDecimal("1.0"), BigDecimal("10.0"), 0.1, 0.1,
            weaponImages = listOf( R.drawable.sword_wooden , R.drawable.sword_iron,R.drawable.sword_silver,R.drawable.sword_gold,R.drawable.sword_cobalt,R.drawable.sword_mythril,R.drawable.sword_amethyst,R.drawable.sword_stell ),Player = playerViewModel.player),
        WeaponGame("Dagger",BigDecimal("1.0"), BigDecimal("100.0"), 0.1, 0.1,
            weaponImages = listOf(R.drawable.dagger_wooden, R.drawable.dagger_iron, R.drawable.dagger_silver, R.drawable.dagger_gold, R.drawable.dagger_cobalt, R.drawable.dagger_myhtril, R.drawable.dagger_amethyst, R.drawable.dagger_stell),Player = playerViewModel.player),
        WeaponGame("Bow", BigDecimal("1.0"), BigDecimal("200.0"), 0.1, 0.1, weaponImages = listOf(R.drawable.bow_wooden, R.drawable.bow_iron, R.drawable.bow_silver, R.drawable.bow_gold, R.drawable.bow_cobalt, R.drawable.bow_mythril, R.drawable.bow_amethyst, R.drawable.bow_steel),Player = playerViewModel.player),
        WeaponGame("Spear", BigDecimal("1.0"), BigDecimal("500.0"), 0.1, 0.1, weaponImages = listOf(R.drawable.spear_wooden, R.drawable.spear_iron, R.drawable.spear_silver, R.drawable.spear_gold, R.drawable.spear_cobalt, R.drawable.spear_myhtril, R.drawable.spear_amethyst, R.drawable.spear_stell),Player = playerViewModel.player),
        WeaponGame("Kunai", BigDecimal("1.0"), BigDecimal("1000.0"), 0.1, 0.1, weaponImages = listOf(R.drawable.kunai_wooden, R.drawable.kunai_iron, R.drawable.kunai_silver, R.drawable.kunai_gold, R.drawable.kunai_cobalt, R.drawable.kunai_myhtril, R.drawable.kunai_amethyst, R.drawable.kunai_stell),Player = playerViewModel.player),
        WeaponGame("Greatsword", BigDecimal("1.0"), BigDecimal("2000.0"), 0.1, 0.1, weaponImages = listOf(R.drawable.greatsword_wooden, R.drawable.greatsword_iron, R.drawable.greatsword_silver, R.drawable.greatsword_gold, R.drawable.greatsword_cobalt, R.drawable.greatsword_myhtril, R.drawable.greatsword_amethyst, R.drawable.greatsword_stell),Player = playerViewModel.player),
        WeaponGame("Axe", BigDecimal("1.0"), BigDecimal("5000.0"), 0.1, 0.1, weaponImages = listOf(R.drawable.axe_wooden, R.drawable.axe_iron, R.drawable.axe_silver, R.drawable.axe_gold, R.drawable.axe_cobalt, R.drawable.axe_myhtril, R.drawable.axe_amethyst, R.drawable.axe_stell),Player = playerViewModel.player),
        WeaponGame("Staff", BigDecimal("1.0"), BigDecimal("10000.0"), 0.1, 0.1, weaponImages = listOf(R.drawable.staff_wooden, R.drawable.staff_iron, R.drawable.staff_silver, R.drawable.staff_gold, R.drawable.staff_cobalt, R.drawable.staff_myhtril, R.drawable.staff_amethyst, R.drawable.staff_stell),Player = playerViewModel.player),
        WeaponGame("Crossbow", BigDecimal("1.0"), BigDecimal("20000.0"), 0.1, 0.1, weaponImages = listOf(R.drawable.crossbow_wooden, R.drawable.crossbow_iron, R.drawable.crossbow_silver, R.drawable.crossbow_gold, R.drawable.crossbow_cobalt, R.drawable.crossbow_myhtril, R.drawable.crossbow_amethyst, R.drawable.crossbow_stell),Player = playerViewModel.player)
        )
    playerViewModel.setWeapons(weapons)

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Image(painter = painterResource(id = R.drawable.weapons_background),
                    contentDescription = "Background for the weapon idle tab",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop)
                Box(modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center)
                {Enemy(enemy)}}
            Row(modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF252525))){
                }
            }
            LazyColumn(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                items(playerViewModel.weapons.value) {  weapon ->
                    Weapon(
                        title = weapon.title(),
                        level = weapon.level.value.toString() + " LVL", // Use the level from the gameWeapon
                        income = weapon.formattedDamage() + "/s", // Use the damage from the gameWeapon as income
                        price = weapon.formattedUpgradeCost(), // Use the upgradeCost from the gameWeapon as price
                        weaponPicture = painterResource(weapon.picture()),
                        modifier = Modifier.padding(5.dp, 8.dp),
                        onBuy = {playerViewModel.buyWeapon(weapon)}
                    )
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun WeaponsScreenPreview() {
    IdleGameTheme {
        //WeaponsScreen(enemy = Enemy(), playerViewModel = PlayerViewModel())
    }
}