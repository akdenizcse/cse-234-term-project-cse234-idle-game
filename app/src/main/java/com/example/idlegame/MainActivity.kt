package com.example.idlegame

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.idlegame.ui.screens.Enemies
import com.example.idlegame.ui.screens.slimeEnemy
import com.example.idlegame.ui.screens.slimeEnemy2
import com.example.idlegame.ui.theme.IdleGameTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnrememberedMutableState")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IdleGameTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xFF373737)
                ) {
                    Box(modifier =Modifier.padding(16.dp)) {
                        val enemyList = mutableListOf(slimeEnemy, slimeEnemy2)
                        Enemies(enemies = enemyList)
                    }
                }
            }
        }
    }
}