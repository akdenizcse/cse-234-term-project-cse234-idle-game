package com.example.idlegame.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.idlegame.MainActivity
import com.example.idlegame.settings.Settings
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.componentbutton.Check
import com.example.idlegame.game.PlayerViewModel

@Composable
fun SettingsPopUp(sound: MutableState<Boolean>,
                  music: MutableState<Boolean>,
                  navController: NavController,
                  onClose: () -> Unit,
                  logout: () -> Unit,
                  playerViewModel: PlayerViewModel,
                  toggleMusic: () -> Unit
                  ) {
    val context = LocalContext.current
    val checkStateSound:MutableState<Check> =if (sound.value) remember {
        mutableStateOf(Check.Enabled)
    } else remember {
        mutableStateOf(Check.Disabled)
    }
    val checkStateMusic:MutableState<Check> =if (music.value) remember {
        mutableStateOf(Check.Enabled)
    } else remember {
        mutableStateOf(Check.Disabled)
    }
    Box(modifier = Modifier
        .height(245.dp)
        .width(315.dp)) {
        Settings(
            onCancel = onClose,
            soundDesign = checkStateSound.value,
            onSound = {
                sound.value = !sound.value
                playerViewModel.updateSoundState()

            },
            musicDesign = checkStateMusic.value,
            onMusic = {
                toggleMusic()

            },
            onLogout = {
                onClose()
                logout()

                navController.apply {
                    navigate("login")
                }
                Toast.makeText(context, "Logged out successfully!", Toast.LENGTH_SHORT).show()
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsPopUpPreview() {
    IdleGameTheme {
//        SettingsPopUp(remember { mutableStateOf(Check.Enabled) },remember { mutableStateOf(Check.Enabled) },onClose = {})
    }
}
