package com.rentmtm.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.ui.components.MtmDateField
import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.viewmodel.RegisterViewModel
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.ic_step_04

@Composable
fun RegisterUserProfStep4Screen(
    viewModel: RegisterViewModel,
    profileTitle: String = "Professional Allocated",
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
                .padding(top = 24.dp, bottom = 16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(Res.drawable.ic_step_04),
                    contentDescription = "Step Indicator",
                    modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                    contentScale = ContentScale.Fit
                )
            }

            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Government ID (Choose one or both)",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.governmentId.hasNationalId,
                    onCheckedChange = { viewModel.governmentId = viewModel.governmentId.copy(hasNationalId = it) },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Text("National ID Number", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            MtmTextField(
                label = "",
                value = viewModel.governmentId.nationalId,
                onValueChange = { viewModel.governmentId = viewModel.governmentId.copy(nationalId = it) },
                placeholder = "Enter your National ID Number",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Passport Number
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.governmentId.hasPassport,
                    onCheckedChange = { viewModel.governmentId = viewModel.governmentId.copy(hasPassport = it) },
                    colors = CheckboxDefaults.colors(checkedColor = MaterialTheme.colorScheme.primary)
                )
                Text("Passport Number", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            MtmTextField(
                label = "",
                value = viewModel.governmentId.passportNumber,
                onValueChange = { viewModel.governmentId = viewModel.governmentId.copy(passportNumber = it) },
                placeholder = "Enter your Passport Number",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Issuing Country & Expiration Date (Lado a lado)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "Issuing Country",
                    value = viewModel.governmentId.issuingCountry,
                    onValueChange = { viewModel.governmentId = viewModel.governmentId.copy(issuingCountry = it) },
                    placeholder = "Issuing Country",
                    modifier = Modifier.weight(1f)
                )
                MtmDateField(
                    label = "Expiration Date",
                    value = viewModel.governmentId.passportExpirationDate,
                    onDateSelected = { viewModel.governmentId = viewModel.governmentId.copy(passportExpirationDate = it) },
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Social Security Number
            MtmTextField(
                label = "Social Security Number (if applicable)",
                value = viewModel.governmentId.ssn,
                onValueChange = { viewModel.governmentId = viewModel.governmentId.copy(ssn = it) },
                placeholder = "123-45-6789",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Emergency Contact",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))

            MtmTextField(
                label = "Name",
                value = viewModel.emergencyContact.name,
                onValueChange = { viewModel.emergencyContact = viewModel.emergencyContact.copy(name = it) },
                placeholder = "Full Name",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "Relationship",
                    value = viewModel.emergencyContact.relationship,
                    onValueChange = { viewModel.emergencyContact = viewModel.emergencyContact.copy(relationship = it) },
                    placeholder = "Spouse, Friend",
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "Phone Number",
                    value = viewModel.emergencyContact.phone,
                    onValueChange = { viewModel.emergencyContact = viewModel.emergencyContact.copy(phone = it) },
                    placeholder = "+1 (XXX) XXX-XXXX",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

        // 3. BARRA DE BOTÕES INFERIOR (Fixa)
        Surface(
            modifier = Modifier.fillMaxWidth(),
            shadowElevation = 8.dp,
            color = MaterialTheme.colorScheme.surface
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onBack,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(130.dp).height(48.dp)
                ) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text("Back")
                }

                Button(
                    onClick = onNext,
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(130.dp).height(48.dp)
                ) {
                    Text("Next")
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserProfStep4ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserProfStep4Screen(
                viewModel = RegisterViewModel(),
                onBack = {},
                onNext = {}
            )
        }
    }
}