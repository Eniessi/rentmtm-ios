package com.rentmtm.ui.signup

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignUpScreen(
    onNavigateToLogin: () -> Unit = {},
    onNavigateToProfile: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var mismatchError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Sign Up",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(Modifier.height(48.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text("Email is required.")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false
                mismatchError = false
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text("Password is required.")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = false
                mismatchError = false
            },
            label = { Text("Confirm Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmPasswordError || mismatchError,
            supportingText = {
                if (confirmPasswordError) {
                    Text("Password confirmation is required.")
                } else if (mismatchError) {
                    Text("Passwords do not match.")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        )

        Spacer(Modifier.height(32.dp))

        Button(
            onClick = {

                val isEmailEmpty = email.isBlank()
                val isPasswordEmpty = password.isBlank()
                val isConfirmEmpty = confirmPassword.isBlank()

                val isMismatch = password != confirmPassword && !isPasswordEmpty && !isConfirmEmpty

                if (isEmailEmpty || isPasswordEmpty || isConfirmEmpty || isMismatch) {
                    emailError = isEmailEmpty
                    passwordError = isPasswordEmpty
                    confirmPasswordError = isConfirmEmpty
                    mismatchError = isMismatch
                } else {
                    onNavigateToProfile()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Enter", fontSize = 16.sp)
        }

        Spacer(Modifier.height(24.dp))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Already have an account? ")
            TextButton(onClick = onNavigateToLogin) {
                Text("Login", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    MaterialTheme { SignUpScreen() }
}
