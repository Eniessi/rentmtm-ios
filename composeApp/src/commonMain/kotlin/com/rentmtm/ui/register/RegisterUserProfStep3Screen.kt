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
import rentmtm.composeapp.generated.resources.ic_step_03

@Composable
fun RegisterUserProfStep3Screen(
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
                    painter = painterResource(Res.drawable.ic_step_03),
                    contentDescription = "Step Indicator",
                    modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                    contentScale = ContentScale.Fit
                )
            }

            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "General Information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Personal Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "First Name",
                    value = viewModel.personalInfo.firstName,
                    onValueChange = { viewModel.personalInfo = viewModel.personalInfo.copy(firstName = it) },
                    placeholder = "Enter your first name",
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "Last Name",
                    value = viewModel.personalInfo.lastName,
                    onValueChange = { viewModel.personalInfo = viewModel.personalInfo.copy(lastName = it) },
                    placeholder = "Enter your last name",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Gender", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(modifier = Modifier.fillMaxWidth()) {
                    GenderRadioButton(
                        text = "Male",
                        selected = viewModel.personalInfo.gender == "Male",
                        onClick = { viewModel.personalInfo = viewModel.personalInfo.copy(gender = "Male") },
                        modifier = Modifier.weight(1f)
                    )
                    GenderRadioButton(
                        text = "Female",
                        selected = viewModel.personalInfo.gender == "Female",
                        onClick = { viewModel.personalInfo = viewModel.personalInfo.copy(gender = "Female") },
                        modifier = Modifier.weight(1f)
                    )
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    GenderRadioButton(
                        text = "Other",
                        selected = viewModel.personalInfo.gender == "Other",
                        onClick = { viewModel.personalInfo = viewModel.personalInfo.copy(gender = "Other") },
                        modifier = Modifier.weight(1f)
                    )
                    GenderRadioButton(
                        text = "Prefer not to say",
                        selected = viewModel.personalInfo.gender == "Prefer not to say",
                        onClick = { viewModel.personalInfo = viewModel.personalInfo.copy(gender = "Prefer not to say") },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmDateField(
                    label = "Date of Birth",
                    value = viewModel.personalInfo.dateOfBirth,
                    onDateSelected = { viewModel.personalInfo = viewModel.personalInfo.copy(dateOfBirth = it) },
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "Nationality",
                    value = viewModel.personalInfo.nationality,
                    onValueChange = { viewModel.personalInfo = viewModel.personalInfo.copy(nationality = it) },
                    placeholder = "Nationality",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "Primary Language(s)",
                    value = viewModel.personalInfo.primaryLanguage,
                    onValueChange = { viewModel.personalInfo = viewModel.personalInfo.copy(primaryLanguage = it) },
                    placeholder = "English",
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "Phone Number",
                    value = viewModel.personalInfo.phone,
                    onValueChange = { viewModel.personalInfo = viewModel.personalInfo.copy(phone = it) },
                    placeholder = "+1 (XXX) XXX-XXXX", // ⬅️ Corrigido para formato EUA
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
            MtmTextField(
                label = "Email Address",
                value = viewModel.personalInfo.email,
                onValueChange = { viewModel.personalInfo = viewModel.personalInfo.copy(email = it) },
                placeholder = "email@example.com"
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Address",
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "Street",
                    value = viewModel.address.street,
                    onValueChange = { viewModel.address = viewModel.address.copy(street = it) },
                    placeholder = "Street",
                    modifier = Modifier.weight(0.7f)
                )
                MtmTextField(
                    label = "Number",
                    value = viewModel.address.number,
                    onValueChange = { viewModel.address = viewModel.address.copy(number = it) },
                    placeholder = "Apt/Suite",
                    modifier = Modifier.weight(0.3f)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "City",
                    value = viewModel.address.city,
                    onValueChange = { viewModel.address = viewModel.address.copy(city = it) },
                    placeholder = "City",
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "State",
                    value = viewModel.address.state,
                    onValueChange = { viewModel.address = viewModel.address.copy(state = it) },
                    placeholder = "State",
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "Zip Code",
                    value = viewModel.address.zipCode,
                    onValueChange = { newZip ->
                        viewModel.onZipCodeChanged(newZip) // ⬅️ A função do ViewModel cuida do .copy() internamente
                    },
                    placeholder = "ZIP",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

        }

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
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary),
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

@Composable
fun GenderRadioButton(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        RadioButton(
            selected = selected,
            onClick = onClick,
            colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colorScheme.primary)
        )
        Text(text = text, fontSize = 14.sp)
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserProfStep3ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserProfStep3Screen(
                viewModel = RegisterViewModel(),
                onBack = {},
                onNext = {}
            )
        }
    }
}