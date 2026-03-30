package com.rentmtm.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rentmtm.model.enums.BudgetStatus
import com.rentmtm.ui.components.MtmTextArea
import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.viewmodel.BudgetViewModel
import com.rentmtm.viewmodel.ViewerRole
import com.rentmtm.viewmodel.BudgetUiState

@Composable
fun BudgetScreen(
    viewModel: BudgetViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    BudgetContent(
        state = state,
        onServiceTitleChange = viewModel::onServiceTitleChanged,
        onServiceDescriptionChange = viewModel::onServiceDescriptionChanged,
        onServiceLocationChange = viewModel::onServiceLocationChanged,
        onScheduledDateChange = viewModel::onScheduledDateChanged,
        onPaymentMethodChange = viewModel::onPaymentMethodSelected,
        onAdditionalNotesChange = viewModel::onAdditionalNotesChanged,
        onQuoteValueChange = viewModel::onQuoteValueChanged,
        onSubmitRequest = viewModel::submitBudgetRequest,
        onSendQuote = viewModel::sendQuote
    )
}

@Composable
fun BudgetContent(
    state: BudgetUiState,
    onServiceTitleChange: (String) -> Unit = {},
    onServiceDescriptionChange: (String) -> Unit = {},
    onServiceLocationChange: (String) -> Unit = {},
    onScheduledDateChange: (String) -> Unit = {},
    onPaymentMethodChange: (Long) -> Unit = {},
    onAdditionalNotesChange: (String) -> Unit = {},
    onQuoteValueChange: (String) -> Unit = {},
    onSubmitRequest: () -> Unit = {},
    onSendQuote: () -> Unit = {}
) {
    Scaffold(
        modifier = Modifier.fillMaxSize().systemBarsPadding(),
        topBar = {
            Text(
                text = if (state.role == ViewerRole.CLIENT) "Request Service" else "Service Quote",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(24.dp)
            )
        },
        bottomBar = {
            BudgetBottomActions(
                state = state,
                onSubmitRequest = onSubmitRequest,
                onSendQuote = onSendQuote
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            when (state.role) {
                ViewerRole.CLIENT -> {
                    ClientDraftingView(
                        state = state,
                        onServiceTitleChange = onServiceTitleChange,
                        onServiceDescriptionChange = onServiceDescriptionChange,
                        onServiceLocationChange = onServiceLocationChange,
                        onScheduledDateChange = onScheduledDateChange,
                        onPaymentMethodChange = onPaymentMethodChange
                    )
                }
                ViewerRole.PROFESSIONAL -> {
                    ProfessionalRespondingView(
                        state = state,
                        onAdditionalNotesChange = onAdditionalNotesChange,
                        onQuoteValueChange = onQuoteValueChange
                    )
                }
            }
        }
    }
}

// -------------------------------------------------------------
// Private UI Components
// -------------------------------------------------------------

@Composable
private fun ClientDraftingView(
    state: BudgetUiState,
    onServiceTitleChange: (String) -> Unit,
    onServiceDescriptionChange: (String) -> Unit,
    onServiceLocationChange: (String) -> Unit,
    onScheduledDateChange: (String) -> Unit,
    onPaymentMethodChange: (Long) -> Unit
) {
    MtmTextField(
        label = "What do you need?",
        value = state.serviceTitle,
        onValueChange = onServiceTitleChange,
        placeholder = "E.g.: Shower wiring replacement",
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    MtmTextArea(
        label = "Problem Details",
        value = state.serviceDescription,
        onValueChange = onServiceDescriptionChange,
        placeholder = "Explain the context, equipment brands, etc.",
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    MtmTextField(
        label = "Where will the service be?",
        value = state.serviceLocationInput,
        onValueChange = onServiceLocationChange,
        placeholder = "Street, Neighborhood, City",
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    MtmTextField(
        label = "Desired Date",
        value = state.scheduledDate,
        onValueChange = onScheduledDateChange,
        placeholder = "MM/DD/YYYY or 'As soon as possible'",
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))

    Text("Preferred Payment Method", style = MaterialTheme.typography.labelLarge)
    Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.padding(top = 8.dp)) {
        // The color change happens because of the 'selected' boolean expression below
        FilterChip(
            selected = state.selectedPaymentMethodId == 1L,
            onClick = { onPaymentMethodChange(1L) },
            label = { Text("Credit Card") }
        )
        FilterChip(
            selected = state.selectedPaymentMethodId == 3L,
            onClick = { onPaymentMethodChange(3L) },
            label = { Text("Cash") }
        )
    }
}

@Composable
private fun ProfessionalRespondingView(
    state: BudgetUiState,
    onAdditionalNotesChange: (String) -> Unit,
    onQuoteValueChange: (String) -> Unit
) {
    Text("Client Request: ${state.serviceTitle}", fontWeight = FontWeight.Bold)
    Text(state.serviceDescription, modifier = Modifier.padding(vertical = 8.dp))
    Text("Location: ${state.serviceLocationInput}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
    Text("Requested Date: ${state.scheduledDate.ifBlank { "Flexible" }}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)

    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

    MtmTextArea(
        label = "Your Conditions / Required Materials",
        value = state.additionalNotes,
        onValueChange = onAdditionalNotesChange,
        placeholder = "E.g.: I will need you to buy XYZ parts before my arrival...",
        modifier = Modifier.fillMaxWidth()
    )
    Spacer(modifier = Modifier.height(16.dp))
    MtmTextField(
        label = "Your Final Quote ($)",
        value = state.estimatedValueInput,
        onValueChange = onQuoteValueChange,
        placeholder = "0.00",
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun BudgetBottomActions(
    state: BudgetUiState,
    onSubmitRequest: () -> Unit,
    onSendQuote: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(24.dp)
    ) {
        when (state.role) {
            ViewerRole.CLIENT -> {
                Button(
                    onClick = onSubmitRequest,
                    enabled = state.isSubmitEnabled,
                    modifier = Modifier.fillMaxWidth().height(48.dp)
                ) {
                    Text("Send Request")
                }
            }
            ViewerRole.PROFESSIONAL -> {
                Button(
                    onClick = onSendQuote,
                    modifier = Modifier.fillMaxWidth().height(48.dp),
                    enabled = state.estimatedValueInput.isNotBlank()
                ) {
                    Text("Send Quote")
                }
            }
        }
    }
}

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true)
@Composable
fun Preview_01_Client_Creating_Request() {
    MaterialTheme {
        BudgetContent(
            state = BudgetUiState(
                role = ViewerRole.CLIENT,
                status = BudgetStatus.PENDING,
                serviceTitle = "Kitchen Pipe Leak",
                serviceDescription = "Water is dripping under the sink cabinet.",
                serviceLocationInput = "Independence Avenue, 1000",
                selectedPaymentMethodId = 1L,
                isSubmitEnabled = true
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun Preview_02_Professional_Responding() {
    MaterialTheme {
        BudgetContent(
            state = BudgetUiState(
                role = ViewerRole.PROFESSIONAL,
                status = BudgetStatus.PENDING,
                serviceTitle = "Kitchen Pipe Leak",
                serviceDescription = "Water is dripping under the sink cabinet.",
                serviceLocationInput = "Independence Avenue, 1000",
                scheduledDate = "Tomorrow morning"
            )
        )
    }
}