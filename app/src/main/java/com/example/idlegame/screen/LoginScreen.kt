package com.example.idlegame.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.idlegame.R
import com.example.idlegame.gembuy.pressStart2P
import com.example.idlegame.ui.theme.IdleGameTheme
import com.google.firebase.auth.FirebaseAuth
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navController: NavHostController,randomIndex: Int = Random.nextInt(0, 6), auth: FirebaseAuth, loadUserData: (NavHostController, MutableState<Boolean>) -> Unit) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) } // Track loading state

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
                    containerColor = Color.Black.copy(alpha = 0.5f),
                ),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Box { // Center within the Box
                // Draw the outline by overlaying the same text with offset positions and a different color
                for (dx in -1..1) {
                    for (dy in -1..1) {
                        Text(
                            text = "Forgot Password?",
                            modifier = Modifier.offset(dx.dp, dy.dp),
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
            }
            Spacer(modifier = Modifier.height(6.dp))

            Button(
                modifier = Modifier.fillMaxWidth(0.8f),enabled = !isLoading.value,
                onClick = {
                    if (!isLoading.value){
                        isLoading.value = true
                        auth.signInWithEmailAndPassword(email, password)
                            .addOnCompleteListener { task ->
                                if (task.isSuccessful) {
                                    loadUserData(navController, isLoading)
                                    Toast.makeText(context, "Login successful", Toast.LENGTH_SHORT).show()
                                } else {
                                    isLoading.value = false
                                    Toast.makeText(context, "Login failed", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                }
            ) {
                if (isLoading.value) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(20.dp))
                } else {
                    Text(text = "Sign In", fontFamily = pressStart2P, color = Color.White, modifier = Modifier.align(Alignment.CenterVertically))
                }
            }
        }

        Box(
            modifier = Modifier.fillMaxWidth()
        ) { // Center within the Box
            // Draw the outline by overlaying the same text with offset positions and a different color
            for (dx in -1..1) {
                for (dy in -1..1) {
                    Text(
                        text = "Don't have account?\n      Sign Up!",
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
                text = AnnotatedString("Don't have account?\n      Sign Up!"),
                modifier = Modifier.align(Alignment.Center),
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
            //LoginScreen(navController = rememberNavController(), auth = FirebaseAuth.getInstance())
        }
    }
}