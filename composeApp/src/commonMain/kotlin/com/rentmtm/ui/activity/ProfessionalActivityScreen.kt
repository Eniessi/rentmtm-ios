package com.rentmtm.ui.activity

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.WorkOutline
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.model.enums.ProfessionalType
import com.rentmtm.ui.components.MtmTextArea
import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.viewmodel.ProfessionalActivityUiState
import com.rentmtm.viewmodel.ProfessionalActivityViewModel

@Composable
fun ProfessionalActivityScreen(
    viewModel: ProfessionalActivityViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    LaunchedEffect(state.isSuccess) {
        if (state.isSuccess) onSuccess()
    }

    ProfessionalActivityContent(
        state = state,
        onShortDescriptionChanged = viewModel::onShortDescriptionChanged,
        onDetailedDescriptionChanged = viewModel::onDetailedDescriptionChanged,
        onSubmit = viewModel::submitActivity,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalActivityContent(
    state: ProfessionalActivityUiState,
    onShortDescriptionChanged: (String) -> Unit,
    onDetailedDescriptionChanged: (String) -> Unit,
    onSubmit: () -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Add Activity", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    Button(
                        onClick = onSubmit,
                        enabled = state.isSubmitEnabled && !state.isLoading,
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        if (state.isLoading) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp), color = MaterialTheme.colorScheme.onPrimary)
                        } else {
                            Text("Save Activity", fontSize = 16.sp)
                        }
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // ==========================================
            // 1. PROFESSIONAL CARD
            // ==========================================
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.WorkOutline,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = state.professionalType.name.replace("_", " "),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp,
                            color = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    }
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = state.baseServiceDescription,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.8f),
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // ==========================================
            // 2. ACTIVITY FORM
            // ==========================================
            Text(
                text = "Activity Details",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(16.dp))

            MtmTextField(
                label = "Short Description",
                value = state.shortDescription,
                onValueChange = onShortDescriptionChanged,
                placeholder = "e.g., Pipe leak repair",
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "${state.shortDescription.length}/50",
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.align(Alignment.End).padding(top = 4.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            MtmTextArea(
                label = "Detailed Description",
                value = state.detailedDescription,
                onValueChange = onDetailedDescriptionChanged,
                placeholder = "Describe the steps taken, tools used, and the final outcome of the activity...",
                modifier = Modifier.fillMaxWidth().height(150.dp)
            )

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}

// ==========================================
// PREVIEW
// ==========================================
@Preview
@Composable
fun ProfessionalActivityContentPreview() {
    MaterialTheme {
        ProfessionalActivityContent(
            state = ProfessionalActivityUiState(
                professionalType = ProfessionalType.PLUMBER,
                baseServiceDescription = "Installation, repair, and maintenance of pipes and fixtures.",
                shortDescription = "Fixing kitchen sink leak",
                detailedDescription = "Replaced the P-trap and sealed the joints. Tested for leaks under high pressure."
            ),
            onShortDescriptionChanged = {},
            onDetailedDescriptionChanged = {},
            onSubmit = {},
            onBack = {}
        )
    }
}