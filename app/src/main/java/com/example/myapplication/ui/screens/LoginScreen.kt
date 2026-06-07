package com.example.myapplication.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.myapplication.ui.navigation.Screen

@Composable
fun LoginScreen(navController: NavHostController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var emailError by remember { mutableStateOf("") }
    var passwordError by remember { mutableStateOf("") }
    val context = LocalContext.current

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Header
            Text(
                text = "Welcome Back",
                style = MaterialTheme.typography.displaySmall,
                modifier = Modifier.padding(bottom = 8.dp),
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Sign in to your account",
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(bottom = 40.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            // Email Field with animation
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(initialOffsetY = { -40 })
            ) {
                OutlinedTextField(
                    value = email,
                    onValueChange = {
                        email = it
                        emailError = ""
                    },
                    label = { Text("Email Address") },
                    placeholder = { Text("user@example.com") },
                    leadingIcon = { Icon(Icons.Filled.Email, contentDescription = "Email") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    isError = emailError.isNotEmpty(),
                    supportingText = { if (emailError.isNotEmpty()) Text(emailError) }
                )
            }

            // Password Field with animation
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(initialOffsetY = { -40 })
            ) {
                OutlinedTextField(
                    value = password,
                    onValueChange = {
                        password = it
                        passwordError = ""
                    },
                    label = { Text("Password") },
                    placeholder = { Text("••••••••") },
                    leadingIcon = { Icon(Icons.Filled.Lock, contentDescription = "Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 24.dp),
                    isError = passwordError.isNotEmpty(),
                    supportingText = { if (passwordError.isNotEmpty()) Text(passwordError) }
                )
            }

            // Login Button with animation
            AnimatedVisibility(
                visible = true,
                enter = slideInVertically(initialOffsetY = { 40 })
            ) {
                Button(
                    onClick = {
                        var hasError = false
                        if (email.isEmpty()) {
                            emailError = "Email is required"
                            hasError = true
                        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                            emailError = "Invalid email format"
                            hasError = true
                        }
                        if (password.isEmpty()) {
                            passwordError = "Password is required"
                            hasError = true
                        } else if (password.length < 6) {
                            passwordError = "Password must be at least 6 characters"
                            hasError = true
                        }

                        if (!hasError) {
                            isLoading = true
                            Toast.makeText(context, "Login Successful!", Toast.LENGTH_SHORT).show()
                            navController.navigate(Screen.Dashboard.route) {
                                popUpTo(Screen.Login.route) { inclusive = true }
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    enabled = !isLoading,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text("Sign In", style = MaterialTheme.typography.labelLarge)
                }
            }

            // Forgot Password & Sign Up Links
            TextButton(
                onClick = { /* Handle forgot password */ },
                modifier = Modifier.padding(top = 8.dp)
            ) {
                Text("Forgot Password?", color = MaterialTheme.colorScheme.primary)
            }

            Text(
                text = "Don't have an account?",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(top = 24.dp)
            )

            TextButton(onClick = { /* Handle sign up */ }) {
                Text("Create Account", color = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
