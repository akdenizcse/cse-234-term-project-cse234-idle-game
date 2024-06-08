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
        WeaponGame("Sword",BigDecimal("1.0"), BigDecimal("10.0"), 0.1, 0.1,weaponImages = List(8) { R.drawable.sword_wooden ; R.drawable.sword_iron;R.drawable.sword_silver;R.drawable.sword_gold;R.drawable.sword_cobalt;R.drawable.sword_mythril;R.drawable.sword_amethyst;R.drawable.sword_stell }),
        WeaponGame("Dagger", R.drawable.dagger_wooden,BigDecimal("1.0"), BigDecimal("100.0"), 0.1, 0.1),
        WeaponGame("Bow",R.drawable.bow_wooden,BigDecimal("1.0"), BigDecimal("200.0"), 0.1, 0.1),
        WeaponGame("Spear",R.drawable.spear_wooden,BigDecimal("1.0"), BigDecimal("500.0"), 0.1, 0.1),
        WeaponGame("Kunai",R.drawable.kunai_wooden,BigDecimal("1.0"), BigDecimal("1000.0"), 0.1, 0.1),
        WeaponGame("Greatsword",R.drawable.greatsword_wooden,BigDecimal("1.0"), BigDecimal("2000.0"), 0.1, 0.1),
        WeaponGame("Axe",R.drawable.axe_wooden,BigDecimal("1.0"), BigDecimal("5000.0"), 0.1, 0.1),
        WeaponGame("Staff",R.drawable.staff_wooden,BigDecimal("1.0"), BigDecimal("10000.0"), 0.1, 0.1),
        WeaponGame("Crossbow",R.drawable.crossbow_wooden,BigDecimal("1.0"), BigDecimal("20000.0"), 0.1, 0.1)
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
                        income = String.format(Locale.ROOT, "%.2f", weapon.damage()) + "/s", // Use the damage from the gameWeapon as income
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