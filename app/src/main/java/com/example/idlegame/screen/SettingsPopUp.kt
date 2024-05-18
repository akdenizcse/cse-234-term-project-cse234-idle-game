package com.example.idlegame.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idlegame.settings.Settings
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.componentbutton.Check

@Composable
fun SettingsPopUp(sound: MutableState<Check>,
                  music: MutableState<Check>,
                  onClose: () -> Unit){
    Box(modifier = Modifier.height(200.dp).width(315.dp)) {
        Settings(
            onCancel = onClose,
            soundDesign = sound.value,
            onSound = {
                sound.value = if (sound.value == Check.Enabled) Check.Disabled else Check.Enabled
            },
            musicDesign = music.value,
            onMusic = {
                music.value = if (music.value == Check.Enabled) Check.Disabled else Check.Enabled
            },
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPopUpPreview() {
    IdleGameTheme {
        SettingsPopUp(remember { mutableStateOf(Check.Enabled) },remember { mutableStateOf(Check.Enabled) },onClose = {})
    }
}
