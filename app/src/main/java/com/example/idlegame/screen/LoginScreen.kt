package com.example.idlegame.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val backgroundImage = painterResource(id = R.drawable.background)

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
            Text(
                text = "Idle Game",
                fontFamily = pressStart2P,
                fontSize = 25.sp,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text(text = "Email", fontFamily = pressStart2P, color = Color.White, fontSize = 12.sp) },
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = pressStart2P, color = Color.White)
            )
            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text(text = "Password", fontFamily = pressStart2P, color = Color.White, fontSize = 12.sp) },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                textStyle = TextStyle(fontFamily = pressStart2P, color = Color.White)
            )
            Spacer(modifier = Modifier.height(16.dp))

            ClickableText(
                text = AnnotatedString("Forgot Password?"),
                style = TextStyle(
                    color = Color.White,
                    fontFamily = pressStart2P,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                onClick = {
                    navController.navigate("reset") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
            Spacer(modifier = Modifier.height(6.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = {
                    navController.navigate("main") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            ) {
                Text(text = "Sign In", fontFamily = pressStart2P, color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        Box( // Box for Alignment
            modifier = Modifier.fillMaxWidth()
        ) {
            ClickableText(
                modifier = Modifier.align(Alignment.Center), // Center within the Box
                text = AnnotatedString("Don't have account?\n      Sign Up!"),
                style = TextStyle(
                    color = Color.White,
                    fontFamily = pressStart2P,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                ),
                onClick = {
                    navController.navigate("register") {
                        popUpTo("login") { inclusive = true }
                    }
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    IdleGameTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color(0xFF373737)
        ){
            LoginScreen(navController = rememberNavController())
        }
    }
}