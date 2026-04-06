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
import com.rentmtm.ui.components.MtmCheckbox
import com.rentmtm.ui.components.MtmRadioButton
import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.viewmodel.RegisterViewModel
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.ic_step_05

@Composable
fun RegisterUserProfStep5Screen(
    viewModel: RegisterViewModel,
    profileTitle: String = "Professional Allocated",
    onBack: () -> Unit,
    onNext: () -> Unit
) {

    Column(modifier = Modifier.fillMaxSize()) {

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
                    painter = painterResource(Res.drawable.ic_step_05),
                    contentDescription = "Step Indicator",
                    modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                    contentScale = ContentScale.Fit
                )
            }

            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Professional Information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ==========================================
            // SEÇÃO: OCCUPATION DETAILS
            // ==========================================
            Text("Occupation Details", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))

            MtmTextField(
                label = "Professional Title/Occupation",
                value = viewModel.professionalInfo.occupationTitle,
                onValueChange = { viewModel.professionalInfo = viewModel.professionalInfo.copy(occupationTitle = it) },
                placeholder = "Occupation"
            )
            Spacer(modifier = Modifier.height(12.dp))
            MtmTextField(
                label = "Field of Work/Industry",
                value = viewModel.professionalInfo.fieldOfWork,
                onValueChange = { viewModel.professionalInfo = viewModel.professionalInfo.copy(fieldOfWork = it) },
                placeholder = "Field of Work"
            )
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "Years of Experience",
                    value = viewModel.professionalInfo.yearsExperience,
                    onValueChange = { viewModel.professionalInfo = viewModel.professionalInfo.copy(yearsExperience = it) },
                    placeholder = "e.g. 5",
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "Company/Business Name",
                    value = viewModel.professionalInfo.companyName,
                    onValueChange = { viewModel.professionalInfo = viewModel.professionalInfo.copy(companyName = it) },
                    placeholder = "Company Name",
                    modifier = Modifier.weight(1.5f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text("Employment Type", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("Full-time", viewModel.professionalInfo.isFullTime, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isFullTime = it) }, Modifier.weight(1f))
                MtmCheckbox("Part-time", viewModel.professionalInfo.isPartTime, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isPartTime = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("Freelancer", viewModel.professionalInfo.isFreelancer, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isFreelancer = it) }, Modifier.weight(1f))
                MtmCheckbox("Contractor", viewModel.professionalInfo.isContractor, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isContractor = it) }, Modifier.weight(1f))
            }
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                MtmCheckbox("Other:", viewModel.professionalInfo.isOtherEmployment, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isOtherEmployment = it) }, Modifier.width(100.dp))
                if (viewModel.professionalInfo.isOtherEmployment) {
                    MtmTextField("", viewModel.professionalInfo.otherEmploymentText, { viewModel.professionalInfo = viewModel.professionalInfo.copy(otherEmploymentText = it) }, "Specify", Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: BUSINESS/EMPLOYER ADDRESS
            // ==========================================
            Text("Business/Employer Address (if applicable)", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "Street",
                    value = viewModel.businessAddress.street,
                    onValueChange = { viewModel.businessAddress = viewModel.businessAddress.copy(street = it) },
                    placeholder = "Street",
                    modifier = Modifier.weight(0.7f)
                )
                MtmTextField(
                    label = "Number",
                    value = viewModel.businessAddress.number,
                    onValueChange = { viewModel.businessAddress = viewModel.businessAddress.copy(number = it) },
                    placeholder = "Number",
                    modifier = Modifier.weight(0.3f)
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "City",
                    value = viewModel.businessAddress.city,
                    onValueChange = { viewModel.businessAddress = viewModel.businessAddress.copy(city = it) },
                    placeholder = "City",
                    modifier = Modifier.weight(1f)
                )
                MtmTextField(
                    label = "State",
                    value = viewModel.businessAddress.state,
                    onValueChange = { viewModel.businessAddress = viewModel.businessAddress.copy(state = it) },
                    placeholder = "State",
                    modifier = Modifier.weight(1f)
                )

                MtmTextField(
                    label = "Zip Code",
                    value = viewModel.businessAddress.zipCode,
                    onValueChange = { novoCep -> viewModel.onBusinessZipCodeChanged(novoCep) },
                    placeholder = "ZIP",
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: WORK AUTHORIZATION
            // ==========================================
            Text("Work Authorization", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(12.dp))

            Row(modifier = Modifier.fillMaxWidth()) {
                MtmCheckbox("Citizen", viewModel.professionalInfo.isCitizen, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isCitizen = it) }, Modifier.weight(1f))
                MtmCheckbox("Permanent Resident", viewModel.professionalInfo.isPermanentResident, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isPermanentResident = it) }, Modifier.weight(1f))
            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                MtmCheckbox("Work Visa Holder:", viewModel.professionalInfo.isVisaHolder, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isVisaHolder = it) }, Modifier.width(170.dp))
                if (viewModel.professionalInfo.isVisaHolder) {
                    MtmTextField("", viewModel.professionalInfo.visaType, { viewModel.professionalInfo = viewModel.professionalInfo.copy(visaType = it) }, "Specify Type (e.g. H-1B)", Modifier.weight(1f))
                }
            }

            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                MtmCheckbox("Other:", viewModel.professionalInfo.isOtherAuth, { viewModel.professionalInfo = viewModel.professionalInfo.copy(isOtherAuth = it) }, Modifier.width(100.dp))
                if (viewModel.professionalInfo.isOtherAuth) {
                    MtmTextField("", viewModel.professionalInfo.otherAuthText, { viewModel.professionalInfo = viewModel.professionalInfo.copy(otherAuthText = it) }, "Specify", Modifier.weight(1f))
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text("Expected Salary/Rate (if applicable)", fontSize = 18.sp, fontWeight = FontWeight.Medium, color = MaterialTheme.colorScheme.onSurface)
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                MtmRadioButton("Hour", viewModel.professionalInfo.salaryPeriod == "Hour", { viewModel.professionalInfo = viewModel.professionalInfo.copy(salaryPeriod = "Hour") })
                MtmRadioButton("Month", viewModel.professionalInfo.salaryPeriod == "Month", { viewModel.professionalInfo = viewModel.professionalInfo.copy(salaryPeriod = "Month") })
                MtmRadioButton("Year", viewModel.professionalInfo.salaryPeriod == "Year", { viewModel.professionalInfo = viewModel.professionalInfo.copy(salaryPeriod = "Year") })
            }

            Spacer(modifier = Modifier.height(16.dp))

            MtmTextField(
                label = "Amount",
                value = viewModel.professionalInfo.salaryAmount,
                onValueChange = { viewModel.professionalInfo = viewModel.professionalInfo.copy(salaryAmount = it) },
                placeholder = "Enter amount",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 3. BARRA DE BOTÕES INFERIOR
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

// ==========================================
// PREVIEW
// ==========================================
@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserProfStep5ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserProfStep5Screen(
                viewModel = RegisterViewModel(),
                onBack = {},
                onNext = {}
            )
        }
    }
}