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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idlegame.game.PlayerViewModel
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.upgrade.Upgrade

@Composable
fun UpgradeScreen(playerViewModel: PlayerViewModel) {

    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))
            LazyColumn(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                items(playerViewModel.weapons.value) { upgrade ->
                    Upgrade(
                        title = upgrade.title(),
                        material = upgrade.material() + if (!upgrade.isUpgradeMaxed()) {
                            " Upgrade"
                        } else {
                            ""
                        },
                        description = "Increase income by 2x",
                        price = if (upgrade.isUpgradeMaxed()) "Maxed" else upgrade.formattedMultiplierCost(),
                        weaponPicture = painterResource(upgrade.picture(1)),
                        modifier = Modifier.padding(5.dp, 8.dp),
                        onBuy = {
                            if (!upgrade.isUpgradeMaxed()) {
                                playerViewModel.buyUpgrade(upgrade)
                            }
                        }
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
        UpgradeScreen(playerViewModel = PlayerViewModel())
    }
}