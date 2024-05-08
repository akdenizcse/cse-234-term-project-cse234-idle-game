package com.example.idlegame

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.idlegame.ui.template.Weapon
import com.example.idlegame.ui.theme.IdleGameTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            IdleGameTheme {
                // A surface container using the 'background' color from the theme

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = Color(0xff373737)
                ) {
                    Greeting("Android")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.align(Alignment.Center)) {
            item {
                Weapon(modifier=Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp))
                Weapon(modifier=Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp))
                Weapon(modifier=Modifier.padding(0.dp, 5.dp, 0.dp, 5.dp))
            }
        }
    }

    }


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    IdleGameTheme {
        Greeting("Android")
    }
}