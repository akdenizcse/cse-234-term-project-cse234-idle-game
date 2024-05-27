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
        UpgradeData("Sword", "Iron Upgrade", "Increase income by 2x", "1k", R.drawable.sword_iron),
        UpgradeData("Dagger", "Iron Upgrade", "Increase income by 2x", "10k", R.drawable.dagger_iron),
        UpgradeData("Bow", "Iron Upgrade", "Increase income by 2x", "200k", R.drawable.bow_iron),
        UpgradeData("Spear", "Iron Upgrade", "Increase income by 2x", "1m", R.drawable.spear_iron),
        UpgradeData("Kunai", "Iron Upgrade", "Increase income by 2x", "5m", R.drawable.kunai_iron),
        UpgradeData("Greatsword", "Iron Upgrade", "Increase income by 2x", "50m", R.drawable.greatsword_iron),
        UpgradeData("Axe", "Iron Upgrade", "Increase income by 2x", "200m", R.drawable.axe_iron),
        UpgradeData("Staff", "Iron Upgrade", "Increase income by 2x", "500m", R.drawable.staff_iron),
        UpgradeData("Crossbow", "Iron Upgrade", "Increase income by 2x", "1.5bn", R.drawable.crossbow_iron)
    )

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))
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