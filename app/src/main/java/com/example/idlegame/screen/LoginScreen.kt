package com.example.idlegame.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.idlegame.gembuy.pressStart2P

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
                value = username,
                onValueChange = { username = it },
                label = { Text(text = "Username", fontFamily = pressStart2P, color = Color.White, fontSize = 12.sp) },
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
                    fontWeight = FontWeight.Bold
                ),
                onClick = { /* Handle forgot password */ }
            )
            Spacer(modifier = Modifier.height(4.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8f),
                onClick = { /* Handle login */ })
            {
                Text(text = "Login", fontFamily = pressStart2P, color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
            }
        }

        Box( // Box for Alignment
            modifier = Modifier.fillMaxWidth()
        ) {
            ClickableText(
                modifier = Modifier.align(Alignment.Center), // Center within the Box
                text = AnnotatedString("Don't have account? Sign Up"),
                style = TextStyle(
                    color = Color.White,
                    fontFamily = pressStart2P,
                    fontWeight = FontWeight.Bold
                ),
                onClick = { /* Handle registration */ }
            )
        }
    }
}
