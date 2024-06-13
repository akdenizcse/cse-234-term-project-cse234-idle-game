package com.example.idlegame.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.idlegame.game.PlayerViewModel
import com.example.idlegame.purchase.Purchase

@Composable
fun PurchasePopUp(onClose: () -> Unit, playerViewModel: PlayerViewModel, gems: MutableState<Int>) {
    val context = LocalContext.current
    Box(modifier = Modifier.height(180.dp).width(315.dp)) {
        Purchase(
            gemCount = "${gems.value} Gems",
            onCancel = onClose,
            onBuy = {
                playerViewModel.addGems(gems.value)
                onClose()
                Toast.makeText(context, "Purchase successful!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}