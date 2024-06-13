package com.example.idlegame.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.window.Dialog
import com.example.idlegame.R
import com.example.idlegame.biggembuy.BigGemBuy
import com.example.idlegame.game.NumberFormatter.formatLargeNumber
import com.example.idlegame.game.PlayerViewModel
import com.example.idlegame.gembuy.GemBuy
import com.example.idlegame.gembuy.pressStart2P
import com.example.idlegame.incomemultiplier.IncomeMultiplier
import com.example.idlegame.timewarp.TimeWarp
import com.example.idlegame.ui.theme.IdleGameTheme

@Composable
fun StoreScreen(playerViewModel: PlayerViewModel) {
    val context = LocalContext.current
    val showPurchaseDialogue = remember { mutableStateOf(false) }
    val gems: MutableState<Int> = remember { mutableStateOf(0) }
    if (showPurchaseDialogue.value) {
        Dialog(onDismissRequest = { showPurchaseDialogue.value = false }) {
            PurchasePopUp(
                onClose = { showPurchaseDialogue.value = false },
                playerViewModel = playerViewModel,
                gems = gems
            )
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))
            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Gem Shop",
                fontFamily = pressStart2P,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                GemBuy(
                    onBuy = {
                        gems.value = 2
                        showPurchaseDialogue.value = true
                    },
                    gemCount = "2 Gem",
                    price = "TRY 5",
                    modifier = Modifier.padding(8.dp, 6.dp)
                )
                GemBuy(
                    onBuy = {
                        gems.value = 10
                        showPurchaseDialogue.value = true
                    },
                    gemCount = "10 Gem",
                    price = "TRY 10",
                    modifier = Modifier.padding(8.dp, 6.dp)
                )
                GemBuy(
                    onBuy = {
                        gems.value = 25
                        showPurchaseDialogue.value = true
                    },
                    gemCount = "25 Gem",
                    price = "TRY 20",
                    modifier = Modifier.padding(8.dp, 6.dp)
                )
            }

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                BigGemBuy(
                    onBuy = {
                        gems.value = 65
                        showPurchaseDialogue.value = true
                    },
                    preciousPearl = painterResource(R.drawable.big_gem_buy_precious_pearl_4),
                    gemCount = "65 Gem",
                    price = "TRY 50",
                    modifier = Modifier.padding(8.dp, 8.dp)
                )
                BigGemBuy(
                    onBuy = {
                        gems.value = 150
                        showPurchaseDialogue.value = true
                    },
                    preciousPearl = painterResource(R.drawable.big_gem_buy_precious_pearl_4),
                    gemCount = "150 Gem",
                    price = "TRY 100",
                    modifier = Modifier.padding(8.dp, 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Time Warps",
                fontFamily = pressStart2P,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TimeWarp(
                    onBuy = {
                        if (playerViewModel.player.gems.value >= 50) {
                            playerViewModel.player.gems.value -= 50
                            val income =
                                playerViewModel.earningsPerSecond.value.multiply(3600.toBigDecimal())
                            playerViewModel.player.money.value += income
                            Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Not enough gems!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    hours = "1-Hour Time Warp",
                    description = "Get 1 hour worth income instantly!",
                    amount = "That’s ${
                        formatLargeNumber(
                            playerViewModel.earningsPerSecond.value.multiply(
                                3600.toBigDecimal()
                            )
                        )
                    }!",
                    price = "50",
                    modifier = Modifier.padding(6.dp, 6.dp)
                )
                TimeWarp(
                    onBuy = {
                        if (playerViewModel.player.gems.value >= 150) {
                            playerViewModel.player.gems.value -= 150
                            val income =
                                playerViewModel.earningsPerSecond.value.multiply(3600.toBigDecimal())
                                    .multiply(6.toBigDecimal())
                            playerViewModel.player.money.value += income
                            Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Not enough gems!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    hours = "6-Hour Time Warp",
                    description = "Get 6 hour worth income instantly!",
                    amount = "That’s ${
                        formatLargeNumber(
                            playerViewModel.earningsPerSecond.value.multiply(
                                3600.toBigDecimal()
                            ).multiply(6.toBigDecimal())
                        )
                    }!",
                    price = "150",
                    modifier = Modifier.padding(6.dp, 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Multipliers",
                fontFamily = pressStart2P,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                IncomeMultiplier(
                    onBuy = {
                        if (playerViewModel.player.gems.value >= 300) {
                            playerViewModel.player.gems.value -= 300
                            if (playerViewModel.player.globalModifier.value == 1.toBigDecimal())
                                playerViewModel.player.globalModifier.value += 1.toBigDecimal()
                            else
                                playerViewModel.player.globalModifier.value += 2.toBigDecimal()
                            Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Not enough gems!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    multiplierTitle = "2x Income\nMultiplier",
                    description = "Applies a 2x Income Multiplier to all weapons! ",
                    price = "300",
                    modifier = Modifier.padding(6.dp, 6.dp)
                )
                IncomeMultiplier(
                    onBuy = {
                        if (playerViewModel.player.gems.value >= 600) {
                            playerViewModel.player.gems.value -= 600
                            if (playerViewModel.player.globalModifier.value == 1.toBigDecimal())
                                playerViewModel.player.globalModifier.value += 3.toBigDecimal()
                            else
                                playerViewModel.player.globalModifier.value += 4.toBigDecimal()
                            Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Toast.makeText(context, "Not enough gems!", Toast.LENGTH_SHORT).show()
                        }
                    },
                    multiplierTitle = "4x Income\nMultiplier",
                    description = "Applies a 4x Income Multiplier to all weapons! ",
                    price = "600",
                    modifier = Modifier.padding(6.dp, 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(60.dp))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun StoreScreenPreview() {
    IdleGameTheme {
        StoreScreen(playerViewModel = PlayerViewModel())
    }
}