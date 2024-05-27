package com.example.idlegame.screen

import Enemy
import EnemyViewModel
import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Size
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idlegame.R
import com.example.idlegame.data.WeaponData
import com.example.idlegame.downbar.Design
import com.example.idlegame.downbar.DownBar
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.upbar.UpBar
import com.example.idlegame.weapon.Weapon

@SuppressLint("UnrememberedMutableState")
@Composable
fun WeaponsScreen(enemy: Enemy) {
    val weapons = listOf(
        WeaponData("Sword", "1 LVL", "1/s", "10", R.drawable.sword_wooden),
        WeaponData("Dagger", "0 LVL", "0/s", "100", R.drawable.dagger_wooden),
        WeaponData("Bow", "0 LVL", "0/s", "2k", R.drawable.bow_wooden),
        WeaponData("Spear", "0 LVL", "0/s", "10k", R.drawable.spear_wooden),
        WeaponData("Kunai", "0 LVL", "0/s", "50k", R.drawable.kunai_wooden),
        WeaponData("Greatsword", "0 LVL", "0/s", "500k", R.drawable.greatsword_wooden),
        WeaponData("Axe", "0 LVL", "0/s", "2m", R.drawable.axe_wooden),
        WeaponData("Staff", "0 LVL", "0/s", "5m", R.drawable.staff_wooden),
        WeaponData("Crossbow", "0 LVL", "0/s", "15m", R.drawable.crossbow_wooden)
    )
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
                items(weapons) { weapon ->
                    Weapon(
                        title = weapon.title,
                        level = weapon.level,
                        income = weapon.income,
                        price = weapon.price,
                        weaponPicture = painterResource(weapon.weaponPicture),
                        modifier = Modifier.padding(5.dp, 8.dp)
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
        //WeaponsScreen(enemyViewModel.slimeEnemy )
    }
}