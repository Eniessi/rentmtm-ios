package com.rentmtm.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.viewmodel.ProfileType
import com.rentmtm.viewmodel.RegisterViewModel

@Composable
fun WelcomeSuccessScreen(
    viewModel: RegisterViewModel,
    onFinish: () -> Unit
) {
    // 1. Buscamos o nome diretamente do nosso "Cérebro"
    val userName = viewModel.personalInfo.firstName

    // 2. Mapeamento dos perfis (como fizemos antes)
    val profileName = when (viewModel.profileType) {
        ProfileType.PROFESSIONAL -> "Professional Allocated"
        ProfileType.TENANT -> "Tenant / Renter"
        ProfileType.OWNER -> "Property Owner"
        ProfileType.SELLER -> ("Seller")
        ProfileType.BUYER -> ("Buyer")
        ProfileType.PARTNER -> ("Partner")
        ProfileType.NONE -> "User"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // ÍCONE DE SUCESSO (Vetor nativo)
        Icon(
            imageVector = Icons.Default.CheckCircle,
            contentDescription = "Success Icon",
            tint = Color(0xFF4CAF50),
            modifier = Modifier
                .size(180.dp)
                .padding(bottom = 32.dp)
        )

        // TÍTULO PERSONALIZADO
        // Se o nome estiver vazio (por algum erro), mostramos apenas a mensagem padrão
        Text(
            text = if (userName.isNotEmpty()) "Welcome, $userName!" else "Welcome to RentMTM!",
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(16.dp))

        // MENSAGEM DINÂMICA COM O TIPO DE PERFIL
        Text(
            text = "Congratulations! Your $profileName profile has been successfully created. You're all set to explore the platform.",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
            lineHeight = 22.sp
        )

        Spacer(modifier = Modifier.height(48.dp))

        // BOTÃO PARA FINALIZAR
        Button(
            onClick = onFinish,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Go to Home",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

// ==========================================
// PREVIEWS PARA CADA TIPO DE PERFIL
// ==========================================

@Preview(showBackground = true, name = "Success Tenant")
@Composable
fun PreviewSuccessTenant() {
    val mockVM = RegisterViewModel().apply {
        updateProfileType(ProfileType.TENANT)
    }
    MaterialTheme {
        WelcomeSuccessScreen(viewModel = mockVM, onFinish = {})
    }
}

@Preview(showBackground = true, name = "Success Professional")
@Composable
fun PreviewSuccessProf() {
    val mockVM = RegisterViewModel().apply {
        updateProfileType(ProfileType.PROFESSIONAL)
    }
    MaterialTheme {
        WelcomeSuccessScreen(viewModel = mockVM, onFinish = {})
    }
}