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
                    modifier = Modifier.padding(8.dp, 8.dp)
                )
                GemBuy(
                    onBuy = {},
                    preciousPearl = painterResource(R.drawable.gem_buy_precious_pearl),
                    gemCount = "2 Gem",
                    price = "Come at 6PM",
                    modifier = Modifier.padding(8.dp, 8.dp)
                )
                GemBuy(
                    onBuy = {},
                    preciousPearl = painterResource(R.drawable.gem_buy_precious_pearl),
                    gemCount = "2 Gem",
                    price = "Come at 6PM",
                    modifier = Modifier.padding(8.dp, 8.dp)
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
                    gemCount = "65 Gem",
                    price = "TRY 50",
                    modifier = Modifier.padding(8.dp, 8.dp)
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