package com.example.idlegame

import EnemyViewModel
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.idlegame.componentbutton.Check
import com.example.idlegame.ui.theme.IdleGameTheme
import com.example.idlegame.downbar.Design
import com.example.idlegame.downbar.DownBar
import com.example.idlegame.screen.Screen
import com.example.idlegame.screen.SettingsPopUp
import com.example.idlegame.screen.StoreScreen
import com.example.idlegame.screen.UpgradeScreen
import com.example.idlegame.screen.WeaponsScreen
import com.example.idlegame.timewarp.TimeWarp
import com.example.idlegame.upbar.UpBar

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences // is the thing that holds these values when the app is closed
    private lateinit var sound: MutableState<Check>
    private lateinit var music: MutableState<Check>
    private val enemyViewModel: EnemyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)

        setContent {
            IdleGameTheme {
                sound = remember { mutableStateOf(loadCheckState("sound")) }
                music = remember { mutableStateOf(loadCheckState("music")) }

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF373737)
                ) {
                    Main(enemyViewModel,sound, music)
                }
            }
        }
    }
    override fun onPause() {
        super.onPause()
        enemyViewModel.resetEnemyState()
        saveCheckState("sound", sound.value)
        saveCheckState("music", music.value)
    }

    private fun loadCheckState(key: String): Check {
        val state = sharedPreferences.getString(key, Check.Enabled.name)
        return Check.valueOf(state ?: Check.Enabled.name)
    }

    private fun saveCheckState(key: String, check: Check) {
        with(sharedPreferences.edit()) {
            putString(key, check.name)
            apply()
        }
    }

}

@Composable
fun Main(enemyViewModel: EnemyViewModel,sound: MutableState<Check>, music: MutableState<Check>) {
    val screen = remember { mutableStateOf(Screen.WeaponsTab) }
    val design = remember { mutableStateOf(Design.WeaponsTab) }
    val showSettingsDialog = remember { mutableStateOf(false) }

    Box(modifier = Modifier.fillMaxSize()) {
        UpBar(
            output = "120k/s",
            onGear = { showSettingsDialog.value = true },
            money = "911m",
            gems = "30",
            modifier = Modifier.fillMaxWidth()
        )

        if (showSettingsDialog.value) {
            Dialog(onDismissRequest = { showSettingsDialog.value = false }) {
                SettingsPopUp(sound, music, onClose = { showSettingsDialog.value = false })
            }
        }

        when (screen.value) {
            Screen.WeaponsTab -> WeaponsScreen(enemyViewModel.slimeEnemy)
            Screen.StoreTab -> StoreScreen()
            Screen.UpgradesTab -> UpgradeScreen()
        }

        DownBar(
            onWeaponsTab = {
                screen.value = Screen.WeaponsTab
                design.value = Design.WeaponsTab
                enemyViewModel.resetEnemyState() // Reset enemy state when switching to Weapons tab
            },
            onStoreTab = {
                screen.value = Screen.StoreTab
                design.value = Design.StoreTab
            },
            onUpgradesTab = {
                screen.value = Screen.UpgradesTab
                design.value = Design.UpgradeTab
            },
            design = design.value,
            modifier = Modifier.align(Alignment.BottomCenter)
        )

    }
}