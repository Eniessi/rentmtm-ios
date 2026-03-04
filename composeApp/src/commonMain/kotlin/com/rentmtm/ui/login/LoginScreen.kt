package com.rentmtm.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
@Preview(showBackground = true)
fun LoginScreen(
    onNavigateToSignUp: () -> Unit = {},
    onNavigateToForgotPassword: () -> Unit = {},
    onLoginSuccess: () -> Unit = {}
) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // 1. Criamos as variáveis para guardar o "estado de erro"
    var emailError by remember { mutableStateOf(false) }
    var passwordError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Welcome Back", fontSize = 32.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(48.dp))

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = false // 2. Se o usuário começar a corrigir, limpamos o erro!
            },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            isError = emailError, // <-- Fica com o contorno vermelho se for true
            supportingText = {    // <-- O texto que aparece magicamente por baixo
                if (emailError) {
                    Text("The Email is required.")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp)) // Diminui um pouco o espaço porque o supportingText já ocupa espaço

        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                passwordError = false // Limpa o erro ao digitar
            },
            label = { Text("Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = passwordError,
            supportingText = {
                if (passwordError) {
                    Text("The Password is required.")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        )

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
            TextButton(onClick = onNavigateToForgotPassword) {
                Text("Forgot password?", color = MaterialTheme.colorScheme.secondary)
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            // 3. A lógica de Validação acontece AQUI!
            onClick = {
                val isEmailEmpty = email.isBlank()
                val isPasswordEmpty = password.isBlank()

                if (isEmailEmpty || isPasswordEmpty) {
                    // Se algum estiver vazio, ativamos os alertas de erro
                    emailError = isEmailEmpty
                    passwordError = isPasswordEmpty
                } else {
                    // Tudo preenchido? Pode passar!
                    onLoginSuccess()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
            // Retirei o 'enabled = ...' para que o botão possa sempre ser clicado e mostrar os erros!
        ) {
            Text("Enter", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text("Don't have an account?")
            TextButton(onClick = onNavigateToSignUp) {
                Text("Sign up", color = MaterialTheme.colorScheme.secondary)
            }
        }
    }
}