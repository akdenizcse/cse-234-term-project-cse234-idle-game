package com.example.idlegame.screen

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idlegame.R
import com.example.idlegame.biggembuy.BigGemBuy
import com.example.idlegame.gembuy.GemBuy
import com.example.idlegame.gembuy.pressStart2P
import com.example.idlegame.incomemultiplier.IncomeMultiplier
import com.example.idlegame.timewarp.TimeWarp
import com.example.idlegame.ui.theme.IdleGameTheme

@Composable
fun StoreScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Column {
            Spacer(modifier = Modifier.height(60.dp))
            Spacer (modifier = Modifier.height(20.dp))

            Text(
                text = "Gem Shop",
                fontFamily = pressStart2P,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                GemBuy(
                    onBuy = {},
                    preciousPearl = painterResource(R.drawable.gem_buy_precious_pearl),
                    gemCount = "2 Gem",
                    price = "Come at 6PM",
                    modifier = Modifier.padding(8.dp, 6.dp)
                )
                GemBuy(
                    onBuy = {},
                    preciousPearl = painterResource(R.drawable.gem_buy_precious_pearl),
                    gemCount = "10 Gem",
                    price = "TRY 10",
                    modifier = Modifier.padding(8.dp, 6.dp)
                )
                GemBuy(
                    onBuy = {},
                    preciousPearl = painterResource(R.drawable.gem_buy_precious_pearl),
                    gemCount = "25 Gem",
                    price = "TRY 20",
                    modifier = Modifier.padding(8.dp, 6.dp)
                )
            }

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center){
                BigGemBuy(
                    onBuy = {},
                    preciousPearl = painterResource(R.drawable.big_gem_buy_precious_pearl_4),
                    gemCount = "65 Gem",
                    price = "TRY 50",
                    modifier = Modifier.padding(8.dp, 8.dp)
                )
                BigGemBuy(
                    onBuy = {},
                    preciousPearl = painterResource(R.drawable.big_gem_buy_precious_pearl_4),
                    gemCount = "150 Gem",
                    price = "TRY 100",
                    modifier = Modifier.padding(8.dp, 8.dp)
                )
            }

            Spacer (modifier = Modifier.height(8.dp))
            Text(
                text = "Time Warps",
                fontFamily = pressStart2P,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                TimeWarp(
                    onBuy = {},
                    days = "1-Day Time Warp",
                    description = "Get 1 day worth income instantly!",
                    amount = "That’s 10.36m!",
                    price = "10",
                    modifier = Modifier.padding(6.dp, 6.dp))
                TimeWarp(
                    onBuy = {},
                    days = "7-Day Time Warp",
                    description = "Get 7 days worth income instantly!",
                    amount = "That’s 72.58m!",
                    price = "50",
                    modifier = Modifier.padding(6.dp, 6.dp))
            }

            Spacer (modifier = Modifier.height(8.dp))
            Text(
                text = "Multipliers",
                fontFamily = pressStart2P,
                fontSize = 25.sp,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Row (modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                IncomeMultiplier(
                    onBuy = {},
                    multiplierTitle = "2x Income\nMultiplier",
                    description = "Applies a 2x Income Multiplier to all weapons! ",
                    price = "100",
                    modifier = Modifier.padding(6.dp, 6.dp)
                )
                IncomeMultiplier(
                    onBuy = {},
                    multiplierTitle = "4x Income\nMultiplier",
                    description = "Applies a 4x Income Multiplier to all weapons! ",
                    price = "300",
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
        StoreScreen()
    }
}