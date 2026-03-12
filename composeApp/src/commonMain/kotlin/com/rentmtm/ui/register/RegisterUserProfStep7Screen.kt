package com.rentmtm.ui.register

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
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
import rentmtm.composeapp.generated.resources.ic_step_07

import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.ui.components.MtmDateField
import com.rentmtm.ui.components.MtmTextArea
import com.rentmtm.viewmodel.RegisterViewModel

@Composable
fun RegisterUserProfStep7Screen(
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
                    painter = painterResource(Res.drawable.ic_step_07),
                    contentDescription = "Step Indicator",
                    modifier = Modifier.height(32.dp).fillMaxWidth(0.12f),
                    contentScale = ContentScale.Fit
                )
            }

            HorizontalDivider(color = Color.Gray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Employment & Work History",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(32.dp))

            // ==========================================
            // SEÇÃO: LISTA DINÂMICA DE EMPREGOS
            // ==========================================
            viewModel.employmentHistory.experiences.forEachIndexed { index, entry ->

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    val blockTitle = if (index == 0) "Most Recent Employer" else "Previous Employer $index"

                    Text(
                        text = blockTitle,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    if (viewModel.employmentHistory.experiences.size > 1) {
                        TextButton(onClick = {
                            viewModel.removeEmployment(index) // ⬅️ Função do ViewModel
                        }) {
                            Text("Remove", color = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MtmTextField(
                        label = "Employer Name",
                        value = entry.employerName,
                        onValueChange = { newValue ->
                            // ⬅️ Cria cópia do emprego e manda o ViewModel atualizar a lista
                            viewModel.updateEmployment(index, entry.copy(employerName = newValue))
                        },
                        placeholder = "GlobalTech Inc.",
                        modifier = Modifier.weight(1f)
                    )
                    MtmTextField(
                        label = "Position Held",
                        value = entry.position,
                        onValueChange = { newValue ->
                            viewModel.updateEmployment(index, entry.copy(position = newValue))
                        },
                        placeholder = "Project Manager",
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    MtmDateField(
                        label = "Start Date",
                        value = entry.startDate,
                        onDateSelected = { newValue ->
                            viewModel.updateEmployment(index, entry.copy(startDate = newValue))
                        },
                        modifier = Modifier.weight(1f)
                    )
                    MtmDateField(
                        label = "End Date (if applicable)",
                        value = entry.endDate,
                        onDateSelected = { newValue ->
                            viewModel.updateEmployment(index, entry.copy(endDate = newValue))
                        },
                        modifier = Modifier.weight(1f)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))

                MtmTextField(
                    label = "Responsibilities",
                    value = entry.responsibilities,
                    onValueChange = { newValue ->
                        viewModel.updateEmployment(index, entry.copy(responsibilities = newValue))
                    },
                    placeholder = "Team coordination, reporting..."
                )
                Spacer(modifier = Modifier.height(12.dp))

                MtmTextArea(
                    label = "Reason for Leaving",
                    value = entry.reasonForLeaving,
                    onValueChange = { newValue ->
                        viewModel.updateEmployment(index, entry.copy(reasonForLeaving = newValue))
                    },
                    placeholder = "Seeking new challenges..."
                )

                Spacer(modifier = Modifier.height(24.dp))

                if (index < viewModel.employmentHistory.experiences.size - 1) {
                    HorizontalDivider(color = Color.LightGray)
                    Spacer(modifier = Modifier.height(24.dp))
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedButton(
                onClick = { viewModel.addEmployment() }, // ⬅️ Função do ViewModel
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp)
            ) {
                Icon(Icons.Default.Add, contentDescription = "Add Experience")
                Spacer(Modifier.width(8.dp))
                Text("Add Another Experience")
            }

            Spacer(modifier = Modifier.height(32.dp))
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
fun RegisterUserProfStep7ScreenPreview() {
    MaterialTheme {
        Surface(color = MaterialTheme.colorScheme.background) {
            RegisterUserProfStep7Screen(
                viewModel = RegisterViewModel(),
                onBack = {},
                onNext = {}
            )
        }
    }
}