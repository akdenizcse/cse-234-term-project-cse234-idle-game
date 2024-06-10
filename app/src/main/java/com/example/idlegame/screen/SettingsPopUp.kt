package com.example.idlegame.screen

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.idlegame.MainActivity
import com.example.idlegame.settings.Settings
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.componentbutton.Check
import com.google.firebase.auth.FirebaseAuth

@Composable
fun SettingsPopUp(sound: MutableState<Check>,
                  music: MutableState<Check>,
                  navController: NavController,
                  onClose: () -> Unit) {
    val context = LocalContext.current
    Box(modifier = Modifier.height(245.dp).width(315.dp)) {
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
            onLogout = {
                // Get a reference to MainActivity
                val mainActivity = LocalContext.current as MainActivity
                onClose()
                // Call the logout function
                mainActivity.logout()

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
