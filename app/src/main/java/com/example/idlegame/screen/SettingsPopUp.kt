package com.example.idlegame.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.idlegame.ui.theme.IdleGameTheme

@Composable
fun SettingsPopUp(onClose: () -> Unit){
    Box(modifier = Modifier.fillMaxSize()){
        Button(onClick = onClose, modifier = Modifier.align(Alignment.Center)) {
            Text("Close")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPopUpPreview() {
    IdleGameTheme {
        SettingsPopUp(onClose = {})
    }
}
