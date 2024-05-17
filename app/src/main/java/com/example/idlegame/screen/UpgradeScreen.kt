package com.example.idlegame.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idlegame.R
import com.example.idlegame.data.UpgradeData
import com.example.idlegame.downbar.Design
import com.example.idlegame.downbar.DownBar
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.upbar.UpBar
import com.example.idlegame.upgrade.Upgrade

@Composable
fun UpgradeScreen() {
    val upgrades = listOf(
        UpgradeData("Grim Reapersssss", "Diamond", "Increase income by 2x", "802.12M", R.drawable.weapon_photo),
        UpgradeData("title2", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title3", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title4", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title5", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title6", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title7", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title8", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title9", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title10", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title11", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo),
        UpgradeData("title12", "Diamond", "Increase income by 2x", "123.31k", R.drawable.weapon_photo)
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
                items(upgrades) { upgrade ->
                    Upgrade(
                        title = upgrade.title,
                        material = upgrade.material,
                        description = upgrade.description,
                        price = upgrade.price,
                        weaponPicture = painterResource(upgrade.weaponPicture),
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
fun UpgradeScreenPreview() {
    IdleGameTheme {
        UpgradeScreen()
    }
}