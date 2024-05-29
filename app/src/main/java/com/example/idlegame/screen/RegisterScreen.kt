package com.example.idlegame.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.idlegame.R
import com.example.idlegame.gembuy.pressStart2P
import com.example.idlegame.ui.theme.IdleGameTheme
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController,randomIndex: Int = Random.nextInt(0, 6)) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordAgain by remember { mutableStateOf("") }
    val context = LocalContext.current

    val backgroundImages = listOf(
        R.drawable.background0,
        R.drawable.background1,
        R.drawable.background2,
        R.drawable.background3,
        R.drawable.background4,
        R.drawable.background5
    )

    val backgroundImage = painterResource(id = backgroundImages[randomIndex])

    // Set the image as the background
    Image(
        painter = backgroundImage,
        contentDescription = null, // decorative
        modifier = Modifier.fillMaxSize(),
        contentScale = ContentScale.FillBounds // This will make the image scale to fill the entire screen
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // Push content to the top center
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box { // Center within the Box
                // Draw the outline by overlaying the same text with offset positions and a different color
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        Text(
                            text = "Idle Game",
                            modifier = Modifier.offset(dx.dp, dy.dp),
                            style = TextStyle(
                                color = Color.Black, // Outline color
                                fontFamily = pressStart2P,
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }

                // Draw the main text
                Text(
                    text = "Idle Game",
                    fontFamily = pressStart2P,
                    fontSize = 25.sp,
                    color = Color.White
                )
            }
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", fontFamily = pressStart2P, color = Color.White, fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(fontFamily = pressStart2P, color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", fontFamily = pressStart2P, color = Color.White, fontSize = 12.sp) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(fontFamily = pressStart2P, color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            )
            Spacer(modifier = Modifier.height(8.dp))

            TextField(
                value = passwordAgain,
                onValueChange = { passwordAgain = it },
                label = { Text(text = "Password Again", fontFamily = pressStart2P, color = Color.White, fontSize = 12.sp) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                textStyle = TextStyle(fontFamily = pressStart2P, color = Color.White),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    containerColor = Color.Black.copy(alpha = 0.5f)
                )
            )
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                    Toast.makeText(context, "Account Created", Toast.LENGTH_SHORT).show()
                })
            {
                Text(text = "Sign Up", fontFamily = pressStart2P, color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }
        Box(
            modifier = Modifier.fillMaxWidth()
        ) { // Center within the Box
            // Draw the outline by overlaying the same text with offset positions and a different color
            for (dx in -1..1) {
                for (dy in -1..1) {
                    Text(
                        text = "Already have an account?\n        Sign in!",
                        modifier = Modifier.offset(dx.dp, dy.dp).align(Alignment.Center),
                        style = TextStyle(
                            color = Color.Black, // Outline color
                            fontFamily = pressStart2P,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                }
            }

            // Draw the main text
            ClickableText(
                text = AnnotatedString("Already have an account?\n        Sign in!"),
                modifier = Modifier.align(Alignment.Center),
                style = TextStyle(
                    color = Color.White,
                    fontFamily = pressStart2P,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                onClick = {
                    navController.navigate("login") {
                        popUpTo("register") { inclusive = true }
                    }
                }
            )
        }

    }
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    IdleGameTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF373737)
        ){
            RegisterScreen(navController = rememberNavController())
        }
    }
}