package com.rentmtm.ui.forgotpassword

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

enum class ForgotStep {
    Email,
    Code
}

@Composable
fun ForgotPasswordScreen(
    onBackToLogin: () -> Unit,
    onNavigateToNewPassword: (String) -> Unit = {}
) {
    var currentStep by remember { mutableStateOf(ForgotStep.Email) }
    var email by remember { mutableStateOf("") }
    var code by remember { mutableStateOf("") }

    var emailError by remember { mutableStateOf(false) }
    var codeError by remember { mutableStateOf(false) }

    // 1. Variáveis para o nosso cronómetro
    var timeLeft by remember { mutableStateOf(30) }
    var canResend by remember { mutableStateOf(false) }

    // 2. O funcionário invisível que conta o tempo
    LaunchedEffect(currentStep, canResend) {
        if (currentStep == ForgotStep.Code && !canResend) {
            while (timeLeft > 0) {
                delay(1000L) // Espera exatamente 1 segundo (1000 milissegundos)
                timeLeft--   // Tira 1 ao tempo
            }
            canResend = true // Quando o tempo acaba, liberta o botão!
        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        val screenTitle = if (currentStep == ForgotStep.Email) "Forgot Password" else "Verify Your E-mail"

        Text(
            text = screenTitle,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (currentStep == ForgotStep.Email) {

            Text(
                text = "Enter your email to receive the password reset code.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

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
                        Text("The Email is required.")
                    }
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    if (email.isBlank()) {
                        emailError = true
                    } else {
                        // Quando passamos para a próxima tela, o cronómetro de 30s começa a rodar!
                        currentStep = ForgotStep.Code
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Send code")
            }

        } else {

            Text(
                text = "A verification code has been sent to your e-mail. Enter the code below to verify your account to continue.",
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(32.dp))

            OtpInputField(
                code = code,
                isError = codeError,
                onCodeChange = {
                    code = it
                    codeError = false
                }
            )

            if (codeError) {
                Text(
                    text = "The code must have 6 digits.",
                    color = MaterialTheme.colorScheme.error,
                    fontSize = 12.sp,
                    modifier = Modifier.padding(top = 8.dp).align(Alignment.Start)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick =  {
                    if (code.length < 6) {
                        codeError = true
                    } else {
                        onNavigateToNewPassword(email)
                    }
                },
                modifier = Modifier.fillMaxWidth().height(50.dp)
            ) {
                Text("Verify code")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 3. A linha de reenviar o código com o contador
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextButton(
                    onClick = {
                        // Quando clica em reenviar, voltamos o tempo para 30 e trancamos o botão
                        timeLeft = 30
                        canResend = false
                        // (No futuro, aqui irá a função de disparar um novo e-mail)
                    },
                    enabled = canResend // O botão só liga se canResend for true
                ) {
                    Text(
                        text = "Resend code",
                        fontWeight = FontWeight.Bold,
                        // Se não puder clicar, fica cinza para não chamar atenção
                        color = if (canResend) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    )
                }

                // Só mostra o contador de segundos se o botão estiver trancado
                if (!canResend) {
                    Text(
                        text = "available in ${timeLeft}s",
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        fontSize = 14.sp
                    )
                }
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(onClick = onBackToLogin) {
            Text(
                text = "Back to Login",
                color = MaterialTheme.colorScheme.secondary
            )
        }
    }
}

@Composable
fun OtpInputField(
    code: String,
    isError: Boolean,
    onCodeChange: (String) -> Unit
) {
    BasicTextField(
        value = code,
        onValueChange = {
            if (it.length <= 6) {
                onCodeChange(it)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.NumberPassword)
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(6) { index ->
                val char = when {
                    index >= code.length -> ""
                    else -> code[index].toString()
                }

                val isFocused = code.length == index

                val borderColor = when {
                    isError -> MaterialTheme.colorScheme.error
                    isFocused -> MaterialTheme.colorScheme.primary
                    else -> MaterialTheme.colorScheme.outlineVariant
                }

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .aspectRatio(1f)
                        .border(
                            width = if (isFocused || isError) 2.dp else 1.dp,
                            color = borderColor,
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = char,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            ForgotPasswordScreen(onBackToLogin = {})
        }
    }
}