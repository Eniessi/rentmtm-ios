package com.rentmtm.ui.newpassword

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NewPasswordScreen(
    emailReceived: String,
    onPasswordChanged: () -> Unit
) {
    var email by remember { mutableStateOf(emailReceived) }
    var newPassword by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }

    // 1. Variáveis de estado para os nossos erros
    var newPasswordError by remember { mutableStateOf(false) }
    var confirmPasswordError by remember { mutableStateOf(false) }
    var mismatchError by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Balão para centralizar
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Create New Password",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Enter your new password and confirm password below.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("E-mail") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            readOnly = true, // O e-mail não precisa de validação extra porque vem travado da tela anterior
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email, imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = newPassword,
            onValueChange = {
                newPassword = it
                newPasswordError = false // Limpa o erro de vazio
                mismatchError = false    // Limpa o erro de senhas diferentes
            },
            label = { Text("New Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = newPasswordError,
            supportingText = {
                if (newPasswordError) {
                    Text("The new password is required.")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Next)
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                confirmPasswordError = false // Limpa o erro de vazio
                mismatchError = false        // Limpa o erro de senhas diferentes
            },
            label = { Text("Confirm New Password") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            isError = confirmPasswordError || mismatchError,
            supportingText = {
                if (confirmPasswordError) {
                    Text("A confirmação da senha é obrigatória.")
                } else if (mismatchError) {
                    Text("As senhas não coincidem.")
                }
            },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password, imeAction = ImeAction.Done)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                val isNewEmpty = newPassword.isBlank()
                val isConfirmEmpty = confirmPassword.isBlank()

                val isMismatch = newPassword != confirmPassword && !isNewEmpty && !isConfirmEmpty

                if (isNewEmpty || isConfirmEmpty || isMismatch) {
                    newPasswordError = isNewEmpty
                    confirmPasswordError = isConfirmEmpty
                    mismatchError = isMismatch
                } else {
                    onPasswordChanged()
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Reset Password", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}

@Preview(showBackground = true)
@Composable
fun NewPasswordScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            NewPasswordScreen(emailReceived = "teste@exemplo.com", onPasswordChanged = {})
        }
    }
}