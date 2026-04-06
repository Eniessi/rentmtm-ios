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
import org.jetbrains.compose.resources.painterResource
import rentmtm.composeapp.generated.resources.Res
import rentmtm.composeapp.generated.resources.ic_step_08

import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.ui.components.MtmDateField
import com.rentmtm.ui.components.MtmCheckbox
import com.rentmtm.ui.components.MtmRadioButton
import com.rentmtm.ui.components.MtmTextArea
import com.rentmtm.viewmodel.RegisterViewModel

@Composable
fun RegisterUserProfStep8Screen(
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
            Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                Image(
                    painter = painterResource(Res.drawable.ic_step_08),
                    contentDescription = "Step Indicator",
                    modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                    contentScale = ContentScale.Fit
                )
            }

            HorizontalDivider(color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Additional Information",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ==========================================
            // SEÇÃO: DISPONIBILIDADE
            // ==========================================
            MtmDateField(
                label = "Availability to Start Work:",
                value = viewModel.additionalInfo.availabilityDate,
                onDateSelected = { viewModel.additionalInfo = viewModel.additionalInfo.copy(availabilityDate = it) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text("Preferred Work Schedule:", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                MtmCheckbox("Flexible", viewModel.additionalInfo.isFlexible, { viewModel.additionalInfo = viewModel.additionalInfo.copy(isFlexible = it) })
                MtmCheckbox("Fixed Hours", viewModel.additionalInfo.isFixedHours, { viewModel.additionalInfo = viewModel.additionalInfo.copy(isFixedHours = it) })
            }

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: PERGUNTAS SIM/NÃO
            // ==========================================
            MtmYesNoQuestion(
                question = "Willingness to Relocate:",
                selectedOption = viewModel.additionalInfo.relocate,
                onOptionSelected = { viewModel.additionalInfo = viewModel.additionalInfo.copy(relocate = it) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            MtmYesNoQuestion(
                question = "Do you have a valid Driver's License?",
                selectedOption = viewModel.additionalInfo.validLicense,
                onOptionSelected = { viewModel.additionalInfo = viewModel.additionalInfo.copy(validLicense = it) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            MtmYesNoQuestion(
                question = "Are you willing to travel for work?",
                selectedOption = viewModel.additionalInfo.travel,
                onOptionSelected = { viewModel.additionalInfo = viewModel.additionalInfo.copy(travel = it) }
            )
            Spacer(modifier = Modifier.height(16.dp))

            MtmYesNoQuestion(
                question = "Do you have any pending legal work restrictions?",
                selectedOption = viewModel.additionalInfo.legalRestrictions,
                onOptionSelected = { viewModel.additionalInfo = viewModel.additionalInfo.copy(legalRestrictions = it) }
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            // ==========================================
            // SEÇÃO: COMENTÁRIOS E ASSINATURA
            // ==========================================
            MtmTextArea(
                label = "Additional Comments:",
                value = viewModel.additionalInfo.additionalComments,
                onValueChange = { viewModel.additionalInfo = viewModel.additionalInfo.copy(additionalComments = it) },
                placeholder = "Provide any additional relevant information here..."
            )

            Spacer(modifier = Modifier.height(24.dp))

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                MtmTextField(
                    label = "Signature:",
                    value = viewModel.additionalInfo.signature,
                    onValueChange = { viewModel.additionalInfo = viewModel.additionalInfo.copy(signature = it) },
                    placeholder = "Your full name",
                    modifier = Modifier.weight(1.5f)
                )
                MtmDateField(
                    label = "Date:",
                    value = viewModel.additionalInfo.signatureDate,
                    onDateSelected = { viewModel.additionalInfo = viewModel.additionalInfo.copy(signatureDate = it) },
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
                    onClick = onNext, // ⬅️ No futuro, aqui chamará o viewModel.finalizeRegistration()
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                    modifier = Modifier.width(130.dp).height(48.dp)
                ) {
                    Text("Finish") // Alterado para "Finish" por ser a última tela
                    Spacer(Modifier.width(8.dp))
                    Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = null, modifier = Modifier.size(18.dp))
                }
            }
        }
    }
}

// O componente MtmYesNoQuestion permanece igual, mas agora é controlado pelo ViewModel acima.
@Composable
fun MtmYesNoQuestion(
    question: String,
    selectedOption: String,
    onOptionSelected: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = question, fontSize = 14.sp, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            MtmRadioButton("Yes", selectedOption == "Yes", { onOptionSelected("Yes") })
            MtmRadioButton("No", selectedOption == "No", { onOptionSelected("No") })
        }
    }
}

@Preview(showBackground = true, showSystemUi = true, device = "id:pixel_5")
@Composable
fun RegisterUserProfStep8ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserProfStep8Screen(
                viewModel = RegisterViewModel(),
                onBack = {},
                onNext = {}
            )
        }
    }
}