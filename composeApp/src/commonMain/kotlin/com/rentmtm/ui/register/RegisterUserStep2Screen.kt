package com.rentmtm.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.ui.components.MtmDateField
import com.rentmtm.ui.components.MtmDropdownField
import com.rentmtm.ui.components.MtmTextField
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.ic_step_02
import com.rentmtm.viewmodel.RegisterViewModel

@Composable
fun RegisterUserStep2Screen(
    viewModel: RegisterViewModel,
    profileTitle: String = "Tenant / Renter",
    onBack: () -> Unit,
    onNext: () -> Unit
) {
    val genderOptions = listOf("Male", "Female", "Other", "Prefer not to Say")
    val idTypeOptions = listOf("Driver's License (DL)", "Social Security Number (SSN)", "Individual Taxpayer Identification Number (ITIN)")

    // Estado para controlar se o balão de Termos está visível
    var showTermsDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp, vertical = 24.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // CABEÇALHO COM INDICADOR DE PASSO
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Image(
                painter = painterResource(Res.drawable.ic_step_02),
                contentDescription = "Step Indicator",
                modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                contentScale = ContentScale.Fit
            )
        }

        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(24.dp))

        // SEÇÃO: PERSONAL DETAILS
        Text(
            text = "Personal Details",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MtmTextField("First Name", viewModel.personalInfo.firstName, { viewModel.personalInfo = viewModel.personalInfo.copy(firstName = it) }, "Enter your name", Modifier.weight(1f))
            MtmTextField("Last Name", viewModel.personalInfo.lastName, { viewModel.personalInfo = viewModel.personalInfo.copy(lastName = it) }, "Enter your last name", Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MtmDateField("Date of Birth", viewModel.personalInfo.dateOfBirth, { viewModel.personalInfo = viewModel.personalInfo.copy(dateOfBirth = it) }, Modifier.weight(1f))
            MtmTextField("Phone Number", viewModel.personalInfo.phone, { viewModel.personalInfo = viewModel.personalInfo.copy(phone = it) }, "Enter mobile number", Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        MtmTextField("Email", viewModel.personalInfo.email, { viewModel.personalInfo = viewModel.personalInfo.copy(email = it) }, "Enter your email")
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MtmTextField("Password", viewModel.personalInfo.password, { viewModel.personalInfo = viewModel.personalInfo.copy(password = it) }, "••••••••", Modifier.weight(1f))
            MtmDropdownField("Gender", genderOptions, viewModel.personalInfo.gender, { viewModel.personalInfo = viewModel.personalInfo.copy(gender = it) }, Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(24.dp))

        // SEÇÃO: IDENTITY DETAILS
        Text(
            text = "Identity Details",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(modifier = Modifier.height(16.dp))

        MtmDropdownField("Type (Select your option)", idTypeOptions, viewModel.identityDetails.idType, { viewModel.identityDetails = viewModel.identityDetails.copy(idType = it) })
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MtmDateField("Issue Date", viewModel.identityDetails.issueDate, { viewModel.identityDetails = viewModel.identityDetails.copy(issueDate = it) }, Modifier.weight(1f))
            MtmDateField("Expiration Date", viewModel.identityDetails.expirationDate, { viewModel.identityDetails = viewModel.identityDetails.copy(expirationDate = it) }, Modifier.weight(1f))
        }
        Spacer(modifier = Modifier.height(12.dp))
        MtmTextField("Full Name of the Holder", viewModel.identityDetails.holderName, { viewModel.identityDetails = viewModel.identityDetails.copy(holderName = it) }, "Enter Full name of the Holder")
        Spacer(modifier = Modifier.height(12.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            MtmTextField("License Number", viewModel.identityDetails.licenseNumber, { viewModel.identityDetails = viewModel.identityDetails.copy(licenseNumber = it) }, "Enter License Number", Modifier.weight(1f))
            MtmTextField("License Class", viewModel.identityDetails.licenseClass, { viewModel.identityDetails = viewModel.identityDetails.copy(licenseClass = it) }, "Enter License Class", Modifier.weight(1f))
        }

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider(color = Color.LightGray)
        Spacer(modifier = Modifier.height(16.dp))

        // CHECKBOX E LINK DOS TERMOS
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(
                checked = viewModel.identityDetails.acceptedTerms,
                onCheckedChange = { viewModel.identityDetails = viewModel.identityDetails.copy(acceptedTerms = it) }
            )
            Text(
                text = "I accept the Terms and Conditions of Use",
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.primary,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .clickable { showTermsDialog = true } // Abre o balão
                    .padding(start = 4.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // BOTÕES BACK E NEXT
        Row(
            modifier = Modifier.fillMaxWidth().padding(bottom = 24.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = onBack,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
                modifier = Modifier.width(130.dp).height(48.dp)
            ) {
                Icon(Icons.Default.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text("Back")
            }

            Button(
                onClick = onNext,
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                modifier = Modifier.width(130.dp).height(48.dp),
                enabled = viewModel.identityDetails.acceptedTerms // Só avança se aceitar
            ) {
                Text("Next")
                Spacer(Modifier.width(8.dp))
                Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }

    // EXIBIÇÃO DO BALÃO SE O ESTADO FOR TRUE
    if (showTermsDialog) {
        TermsAndConditionsDialog(
            onAccept = {
                // Fecha o balão e marca o checkbox
                showTermsDialog = false
                viewModel.identityDetails = viewModel.identityDetails.copy(acceptedTerms = true)
            },
            onDismiss = {
                // Apenas fecha o balão
                showTermsDialog = false
            }
        )
    }
}

// ==========================================
// COMPONENTE DO BALÃO (DIALOG)
// ==========================================
@Composable
fun TermsAndConditionsDialog(
    onAccept: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = "Terms and Conditions of Use",
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        },
        text = {
            Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                Text(
                    text = "1. Introduction\nWelcome to RentMTM. By using our platform, you agree to these terms.\n\n" +
                            "2. Privacy Policy\nWe protect your data and identity information securely.\n\n" +
                            "3. User Responsibilities\nYou must provide accurate identity details. Falsifying documents will result in immediate ban.\n\n" +
                            "4. Fees and Payments\n(Add your specific app rules here).",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            TextButton(onClick = onAccept) {
                Text("Accept Terms", fontWeight = FontWeight.Bold)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserStep2ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserStep2Screen(
                viewModel = RegisterViewModel(),
                profileTitle = "Tenant / Renter",
                onBack = {},
                onNext = {}
            )
        }
    }
}

// Preview exclusivo para você visualizar o balão sem depender do modo interativo
@Preview(showBackground = true)
@Composable
fun TermsDialogPreview() {
    MaterialTheme {
        TermsAndConditionsDialog(onAccept = {}, onDismiss = {})
    }
}