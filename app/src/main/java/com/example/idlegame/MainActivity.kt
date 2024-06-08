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
import androidx.compose.runtime.rememberCoroutineScope
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
import com.example.idlegame.screen.LoginScreen
import com.example.idlegame.screen.RegisterScreen
import com.example.idlegame.screen.Screen
import com.example.idlegame.screen.SettingsPopUp
import com.example.idlegame.screen.StoreScreen
import com.example.idlegame.screen.UpgradeScreen
import com.example.idlegame.screen.WeaponsScreen
import com.example.idlegame.timewarp.TimeWarp
import com.example.idlegame.upbar.UpBar
import androidx.compose.runtime.LaunchedEffect
import com.example.idlegame.game.PlayerViewModel
import com.example.idlegame.screen.ResetPasswordScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import kotlinx.coroutines.delay
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences // is the thing that holds these values when the app is closed
    private lateinit var sound: MutableState<Check>
    private lateinit var music: MutableState<Check>
    private val playerViewModel: PlayerViewModel by viewModels()
    private val enemyViewModel: EnemyViewModel by viewModels()
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        val randomIndex = Random.nextInt(6)
        playerViewModel.player.money = loadPlayerMoney("playerMoney")

        setContent {
            IdleGameTheme {
                sound = remember { mutableStateOf(loadCheckState("sound")) }
                music = remember { mutableStateOf(loadCheckState("music")) }

                val navController = rememberNavController()

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF373737)
                ) {
                    NavHost(navController = navController, startDestination = "login") {
                        composable("login") { LoginScreen(navController,randomIndex, auth) }
                        composable("reset") { ResetPasswordScreen(navController,randomIndex, auth) }
                        composable("register") { RegisterScreen(navController,randomIndex, auth) }
                        composable("main") { Main(enemyViewModel,playerViewModel, sound, music) }
                    }
                }
            }
        }
    }
    override fun onPause() {
        super.onPause()
        enemyViewModel.resetEnemyState()
        saveCheckState("sound", sound.value)
        saveCheckState("music", music.value)
        savePlayerMoney("playerMoney", playerViewModel.player.money)
    }

    private fun loadCheckState(key: String): Check {
        val state = sharedPreferences.getString(key, Check.Enabled.name)
        return Check.valueOf(state ?: Check.Enabled.name)
    }
    private fun loadPlayerMoney(key: String): Int {
        return sharedPreferences.getInt(key, 100)
    }
    private fun savePlayerMoney(key: String, money: Int) {
        with(sharedPreferences.edit()) {
            putInt(key, money)
            apply()
        }
    }

    private fun saveCheckState(key: String, check: Check) {
        with(sharedPreferences.edit()) {
            putString(key, check.name)
            apply()
        }
    }

}

@Composable
fun Main(enemyViewModel: EnemyViewModel,playerViewModel: PlayerViewModel, sound: MutableState<Check>, music: MutableState<Check>) {
    val navController = rememberNavController()
    val showSettingsDialog = remember { mutableStateOf(false) }
    val design = remember { mutableStateOf(Design.WeaponsTab) }
    LaunchedEffect(key1 = "earnMoney") {
        while (true) {
            playerViewModel.earnMoney()
            delay(1000) // delay for 1 second
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        UpBar(
            output = "1/s",
            onGear = { showSettingsDialog.value = true },
            money = playerViewModel.player.money.toString(),
            gems = "0",
            modifier = Modifier.fillMaxWidth()
        )

        if (showSettingsDialog.value) {
            Dialog(onDismissRequest = { showSettingsDialog.value = false }) {
                SettingsPopUp(sound, music, onClose = { showSettingsDialog.value = false })
            }
        }

        NavHost(navController, startDestination = Screen.WeaponsTab.route) {
            composable(Screen.WeaponsTab.route) { WeaponsScreen(enemyViewModel.slimeEnemy,playerViewModel) }
            composable(Screen.StoreTab.route) { StoreScreen() }
            composable(Screen.UpgradesTab.route) { UpgradeScreen() }
        }

        DownBar(
            onWeaponsTab = {
                navController.navigate(Screen.WeaponsTab.route)
                enemyViewModel.resetEnemyState()
                design.value = Design.WeaponsTab },
            onStoreTab = {
                navController.navigate(Screen.StoreTab.route)
                design.value = Design.StoreTab },
            onUpgradesTab = {
                navController.navigate(Screen.UpgradesTab.route)
                design.value = Design.UpgradeTab },
            design = design.value,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}