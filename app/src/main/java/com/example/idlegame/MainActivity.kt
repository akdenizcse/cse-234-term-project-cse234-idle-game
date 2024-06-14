package com.example.idlegame

import EnemyViewModel
import PlayerViewModelFactory
import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
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
import com.example.idlegame.upbar.UpBar
import androidx.compose.runtime.LaunchedEffect
import com.example.idlegame.game.PlayerViewModel
import com.example.idlegame.screen.ResetPasswordScreen
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.delay
import java.math.BigDecimal
import kotlin.random.Random
import android.util.Log
import androidx.navigation.NavHostController
import com.example.idlegame.game.WeaponGame
import java.util.concurrent.CountDownLatch

class MainActivity : ComponentActivity() {
    private lateinit var sharedPreferences: SharedPreferences // is the thing that holds these values when the app is closed
    private lateinit var sound: MutableState<Boolean>
    private lateinit var music: MutableState<Boolean>
    private lateinit var mediaPlayer: MediaPlayer

    private val enemyViewModel: EnemyViewModel by viewModels()
    val playerViewModel: PlayerViewModel by viewModels { PlayerViewModelFactory(this,sound) }
    private val auth: FirebaseAuth by lazy {
        Firebase.auth
    }

    private val db: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = getSharedPreferences("settings", Context.MODE_PRIVATE)
        sound = mutableStateOf(loadCheckState("sound"))
        music = mutableStateOf(loadCheckState("music"))
        mediaPlayer = MediaPlayer.create(this, R.raw.song)
        mediaPlayer.setVolume(0.1f, 0.1f)
        mediaPlayer.isLooping = true
        if (music.value) {             // Start playing if music setting is on
            mediaPlayer.start()
        }
        val randomIndex = Random.nextInt(6)
        val weapons = listOf(
            WeaponGame(
                "Sword", BigDecimal("10"), BigDecimal("100"), 1.1,
                weaponImages = listOf(
                    R.drawable.sword_wooden, R.drawable.sword_iron, R.drawable.sword_silver, R.drawable.sword_gold,
                    R.drawable.sword_cobalt, R.drawable.sword_mythril, R.drawable.sword_amethyst, R.drawable.sword_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Dagger", BigDecimal("15"), BigDecimal("500"), 1.15,
                weaponImages = listOf(
                    R.drawable.dagger_wooden, R.drawable.dagger_iron, R.drawable.dagger_silver, R.drawable.dagger_gold,
                    R.drawable.dagger_cobalt, R.drawable.dagger_mythril, R.drawable.dagger_amethyst, R.drawable.dagger_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Bow", BigDecimal("25"), BigDecimal("1000"), 1.2,
                weaponImages = listOf(
                    R.drawable.bow_wooden, R.drawable.bow_iron, R.drawable.bow_silver, R.drawable.bow_gold,
                    R.drawable.bow_cobalt, R.drawable.bow_mythril, R.drawable.bow_amethyst, R.drawable.bow_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Spear", BigDecimal("40"), BigDecimal("2500"), 1.25,
                weaponImages = listOf(
                    R.drawable.spear_wooden, R.drawable.spear_iron, R.drawable.spear_silver, R.drawable.spear_gold,
                    R.drawable.spear_cobalt, R.drawable.spear_mythril, R.drawable.spear_amethyst, R.drawable.spear_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Kunai", BigDecimal("60"), BigDecimal("5000"), 1.3,
                weaponImages = listOf(
                    R.drawable.kunai_wooden, R.drawable.kunai_iron, R.drawable.kunai_silver, R.drawable.kunai_gold,
                    R.drawable.kunai_cobalt, R.drawable.kunai_mythril, R.drawable.kunai_amethyst, R.drawable.kunai_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Greatsword", BigDecimal("100"), BigDecimal("10000"), 1.35,
                weaponImages = listOf(
                    R.drawable.greatsword_wooden, R.drawable.greatsword_iron, R.drawable.greatsword_silver, R.drawable.greatsword_gold,
                    R.drawable.greatsword_cobalt, R.drawable.greatsword_mythril, R.drawable.greatsword_amethyst, R.drawable.greatsword_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Axe", BigDecimal("150"), BigDecimal("20000"), 1.4,
                weaponImages = listOf(
                    R.drawable.axe_wooden, R.drawable.axe_iron, R.drawable.axe_silver, R.drawable.axe_gold,
                    R.drawable.axe_cobalt, R.drawable.axe_mythril, R.drawable.axe_amethyst, R.drawable.axe_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Staff", BigDecimal("250"), BigDecimal("50000"), 1.45,
                weaponImages = listOf(
                    R.drawable.staff_wooden, R.drawable.staff_iron, R.drawable.staff_silver, R.drawable.staff_gold,
                    R.drawable.staff_cobalt, R.drawable.staff_mythril, R.drawable.staff_amethyst, R.drawable.staff_steel
                ),
                player = playerViewModel.player
            ),
            WeaponGame(
                "Crossbow", BigDecimal("500"), BigDecimal("100000"), 1.5,
                weaponImages = listOf(
                    R.drawable.crossbow_wooden, R.drawable.crossbow_iron, R.drawable.crossbow_silver, R.drawable.crossbow_gold,
                    R.drawable.crossbow_cobalt, R.drawable.crossbow_mythril, R.drawable.crossbow_amethyst, R.drawable.crossbow_steel
                ),
                player = playerViewModel.player
            )
        )
        playerViewModel.setWeapons(weapons)

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
                        composable("login") { LoginScreen(navController, randomIndex, auth, ::loadUserData) }
                        composable("reset") { ResetPasswordScreen(navController, randomIndex, auth) }
                        composable("register") { RegisterScreen(navController, randomIndex, auth) }
                        composable("main") { Main(navController, enemyViewModel, playerViewModel, sound, music, auth, ::logout, ::saveUserData, ::toggleMusic) }
                    }
                }
            }
        }
    }

    override fun onPause() {
        Log.d("Firestore", "ONPAUSE")
        saveUserData()
        saveCheckState("sound", sound.value)
        saveCheckState("music", music.value)
        super.onPause()
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }
    private fun toggleMusic() {
        music.value = !music.value
        if (music.value && mediaPlayer != null) { // Check if mediaPlayer is not null
            mediaPlayer.start()
        } else if (!music.value && mediaPlayer != null) { // Check if mediaPlayer is not null
            mediaPlayer.pause()
        }
    }

    @SuppressLint("MissingSuperCall")
    override fun onBackPressed() {
        finish()
    }


    private fun loadCheckState(key: String): Boolean {
        val sharedPrefs = sharedPreferences
        // Check if the key exists and its type is String
        val allPrefs = sharedPrefs.all
        return if (allPrefs.containsKey(key) && allPrefs[key] is String) {
            // Convert the stored String value to Boolean
            sharedPrefs.getString(key, "true")!!.toBoolean()
        } else {
            // Read it as Boolean
            sharedPrefs.getBoolean(key, true) // Default to true if the key is not found
        }
    }

    private fun saveCheckState(key: String, check: Boolean) {
        with(sharedPreferences.edit()) {
            putBoolean(key, check)
            apply()
        }
    }


    fun saveUserData() {
        val userData = hashMapOf(
            "enemy hp" to enemyViewModel.slimeEnemy.health,
            "coins" to playerViewModel.player.money.value.setScale(2, BigDecimal.ROUND_HALF_EVEN).toString(),
            "gems" to playerViewModel.player.gems.value,
            "global modifier" to playerViewModel.player.globalModifier.value.toString(),
            "last active" to playerViewModel.player.getCurrentTime()
        )
        auth.currentUser?.uid?.let { uid ->
            db.collection("users").document(uid)
                .set(userData)
                .addOnSuccessListener {
                    Log.d("Firestore", "User data successfully written!")
                }
                .addOnFailureListener { e ->
                    Log.w("Firestore", "Error writing user data", e)
                }

            // Save each weapon data to a separate document in the "weapons" collection
            playerViewModel.weapons.value.forEach { weapon ->
                val weaponData = hashMapOf(
                    "level" to weapon.level.value,
                    "material" to weapon.multiplier.value
                )

                db.collection("weapons").document("${weapon.title()}_$uid")
                    .set(weaponData)
                    .addOnSuccessListener {
                        Log.d("Firestore", "Weapon data successfully written!")
                    }
                    .addOnFailureListener { e ->
                        Log.w("Firestore", "Error writing weapon data", e)
                    }
            }
        }
    }

    fun loadUserData(navController: NavHostController, isLoading: MutableState<Boolean>) {
        auth.currentUser?.uid?.let { uid ->
            // Load weapons data
            val weaponTitles = listOf("Sword", "Dagger", "Bow", "Spear", "Kunai", "Greatsword", "Axe", "Staff", "Crossbow")
            val countDownLatch = CountDownLatch(weaponTitles.size)
            for (title in weaponTitles) {
                val docRef = db.collection("weapons").document("${title}_$uid")
                docRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            Log.d("Firestore", "${document.id} => ${document.data}")
                            val level = document.getLong("level")?.toInt()
                            val material = document.getLong("material")?.toInt()
                            // Update your local variables or UI with the loaded data
                            val weapon = playerViewModel.weapons.value.find { it.title() == title }
                            weapon?.level?.value = level ?: 0
                            weapon?.multiplier?.value = material ?: 1
                            countDownLatch.countDown()
                        } else {
                            Log.d("Firestore", "No such document. Creating a new one.")
                            // Create a new document with default values
                            val defaultData = hashMapOf(
                                "level" to 0,
                                "material" to 1
                            )
                            docRef.set(defaultData)
                                .addOnSuccessListener {
                                    Log.d("Firestore", "New weapon document successfully created!")
                                    // Update your local variables or UI with the default data
                                    val weapon = playerViewModel.weapons.value.find { it.title() == title }
                                    weapon?.level?.value = 0
                                    weapon?.multiplier?.value = 1
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firestore", "Error creating new weapon document", e)
                                }
                            countDownLatch.countDown()
                        }
                    }
            }

            Thread {
                countDownLatch.await()

                val userDocRef = db.collection("users").document(uid)
                userDocRef.get()
                    .addOnSuccessListener { document ->
                        if (document != null && document.exists()) {
                            Log.d("Firestore", "User data successfully loaded!")
                            val enemyHp = document.getLong("enemy hp")?.toInt()
                            val coins = document.getString("coins")
                            val gems = document.getLong("gems")?.toInt()
                            val globalModifier = document.getString("global modifier")
                            val lastActiveTime = document.getLong("last active")
                            // Update your local variables or UI with the loaded data
                            enemyViewModel.slimeEnemy.health = enemyHp ?: 1
                            playerViewModel.player.money.value = if (coins != null) BigDecimal(coins) else BigDecimal.ZERO
                            playerViewModel.player.gems.value = gems ?: 0
                            playerViewModel.player.globalModifier.value = if (globalModifier != null) BigDecimal(globalModifier) else BigDecimal.ZERO
                            playerViewModel.player.lastActiveTime.value = lastActiveTime ?: playerViewModel.player.getCurrentTime()
                            playerViewModel.getOfflineEarnings()

                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                            isLoading.value = false
                        } else {
                            Log.d("Firestore", "No such document. Creating a new one.")
                            // Create a new document with default values
                            val defaultUserData = hashMapOf(
                                "enemy hp" to 1,
                                "coins" to "100",
                                "gems" to 0,
                                "global modifier" to "1"
                            )
                            enemyViewModel.slimeEnemy.health = 1
                            playerViewModel.player.money.value = BigDecimal("100")
                            playerViewModel.player.gems.value = 0
                            playerViewModel.player.globalModifier.value = BigDecimal("1")
                            navController.navigate("main") {
                                popUpTo("login") { inclusive = true }
                            }
                            isLoading.value = false
                            userDocRef.set(defaultUserData)
                                .addOnSuccessListener {
                                    Log.d("Firestore", "New user document successfully created!")
                                }
                                .addOnFailureListener { e ->
                                    Log.w("Firestore", "Error creating new user document", e)
                                }
                        }
                    }
            }.start()
        }
    }

    fun logout() {
        saveUserData()
        auth.signOut()
    }
}

@Composable
fun Main(loginNavController: NavController, enemyViewModel: EnemyViewModel, playerViewModel: PlayerViewModel,
         sound: MutableState<Boolean>, music: MutableState<Boolean>, auth: FirebaseAuth,
         logout: () -> Unit,saveUserData: () -> Unit,
         toggleMusic: () -> Unit){
    val navController = rememberNavController()
    val showSettingsDialog = remember { mutableStateOf(false) }
    val design = remember { mutableStateOf(Design.WeaponsTab) }
    val counter: MutableState<Int> = remember { mutableStateOf(0) }

    LaunchedEffect(key1 = "earnMoney") {
        while (true) {
            playerViewModel.earnMoney()
            delay(1000)
            counter.value++
            if(counter.value ==30)
            {
                saveUserData()
                counter.value = 0
            }
        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        UpBar(
            output = playerViewModel.formattedearningsPerSecond()+"/s",
            onGear = { showSettingsDialog.value = true },
            money = playerViewModel.player.formattedMoney(),
            gems = playerViewModel.player.gems.value.toString(),
            modifier = Modifier.fillMaxWidth()
        )

        if (showSettingsDialog.value) {
            Dialog(onDismissRequest = { showSettingsDialog.value = false }) {
                SettingsPopUp(
                    sound,
                    music,
                    onClose = { showSettingsDialog.value = false },
                    navController = loginNavController,
                    logout = logout,
                    playerViewModel = playerViewModel,
                    toggleMusic = toggleMusic
                )
            }
        }

        NavHost(navController, startDestination = Screen.WeaponsTab.route) {
            composable(Screen.WeaponsTab.route) { WeaponsScreen(enemyViewModel,playerViewModel) }
            composable(Screen.StoreTab.route) { StoreScreen(playerViewModel) }
            composable(Screen.UpgradesTab.route) { UpgradeScreen(playerViewModel) }
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