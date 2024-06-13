package com.example.idlegame.screen

import Enemy
import EnemyView
import EnemyViewModel
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
fun WeaponsScreen(enemy: EnemyViewModel, playerViewModel: PlayerViewModel, ) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))

            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.weapons_background),
                    contentDescription = "Background for the weapon idle tab",
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    contentAlignment = Alignment.Center
                ) {
                    EnemyView(enemyViewModel = enemy, playerViewModel = playerViewModel, health =enemy.slimeEnemy.health)
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color(0xFF252525))
                ) {
                }
            }
            LazyColumn(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                items(playerViewModel.weapons.value) { weapon ->
                    Weapon(
                        title = weapon.title(),
                        level = weapon.level.value.toString() + " LVL", // Use the level from the gameWeapon
                        income = weapon.formattedDamage() + "/s", // Use the damage from the gameWeapon as income
                        price = weapon.formattedUpgradeCost(), // Use the upgradeCost from the gameWeapon as price
                        weaponPicture = painterResource(weapon.picture()),
                        modifier = Modifier.padding(5.dp, 8.dp),
                        onBuy = { playerViewModel.buyWeapon(weapon) }
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