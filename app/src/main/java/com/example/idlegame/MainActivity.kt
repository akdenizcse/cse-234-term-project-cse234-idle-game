package com.example.idlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.weapon.Weapon
import com.example.idlegame.data.WeaponData
import com.example.idlegame.downbar.Design
import com.example.idlegame.downbar.DownBar
import com.example.idlegame.upbar.UpBar

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IdleGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF373737)
                ) {
                    WeaponsScreen()
                }
            }
        }
    }
}

@Composable
fun WeaponsScreen() {
    val weapons = listOf(
        WeaponData("Grim Reapersssss", "12 LVL", "/s", "802.12M", R.drawable.weapon_photo),
        WeaponData("title2", "11 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title3", "10 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title4", "9 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title5", "8 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title6", "7 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title7", "6 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title8", "5 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title9", "4 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title10", "3 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title11", "2 LVL", "/s", "123.31k", R.drawable.weapon_photo),
        WeaponData("title12", "1 LVL", "/s", "123.31k", R.drawable.weapon_photo)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            UpBar(
                output = "120k/s",
                onGear = {},
                money = "911m",
                gems = "30",
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(modifier = Modifier.weight(1f).fillMaxWidth()) {
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
            Spacer(modifier = Modifier.height(60.dp)) // Might need adjusting not sure if 60.dp is correct
        }
        DownBar(
            onWeaponsTab = {},
            onStoreTab = {},
            onUpgradesTab = {},
            design = Design.WeaponsTab,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }

}

@Preview(showBackground = true)
@Composable
fun WeaponsScreenPreview() {
    IdleGameTheme {
        WeaponsScreen()
    }
}