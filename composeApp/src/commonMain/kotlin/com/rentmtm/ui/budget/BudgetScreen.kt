package com.rentmtm.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import com.rentmtm.model.Budget
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
        onAdditionalNotesChange = viewModel::onAdditionalNotesChanged,
        onQuoteValueChange = viewModel::onQuoteValueChanged,
        onSubmitRequest = viewModel::submitBudgetRequest,
        onSendQuote = viewModel::sendQuote,
        onAcceptBudget = viewModel::acceptBudget,
        onBack = onBack
    )
}

@Composable
fun BudgetContent(
    state: BudgetUiState,
    onServiceTitleChange: (String) -> Unit,
    onServiceDescriptionChange: (String) -> Unit,
    onServiceLocationChange: (String) -> Unit,
    onScheduledDateChange: (String) -> Unit,
    onAdditionalNotesChange: (String) -> Unit,
    onQuoteValueChange: (String) -> Unit,
    onSubmitRequest: () -> Unit,
    onSendQuote: () -> Unit,
    onAcceptBudget: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        Text(
            text = if (state.role == ViewerRole.CLIENT) "Request a Service" else "Service Request",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(24.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            when (state.role) {
                ViewerRole.CLIENT -> ClientView(
                    state = state,
                    onServiceTitleChange = onServiceTitleChange,
                    onServiceDescriptionChange = onServiceDescriptionChange,
                    onServiceLocationChange = onServiceLocationChange,
                    onScheduledDateChange = onScheduledDateChange,
                    onSubmitRequest = onSubmitRequest,
                    onAcceptBudget = onAcceptBudget
                )
                ViewerRole.PROFESSIONAL -> ProfessionalView(
                    state = state,
                    onAdditionalNotesChange = onAdditionalNotesChange,
                    onQuoteValueChange = onQuoteValueChange,
                    onSendQuote = onSendQuote
                )
            }
        }
    }
}

@Composable
private fun ClientView(
    state: BudgetUiState,
    onServiceTitleChange: (String) -> Unit,
    onServiceDescriptionChange: (String) -> Unit,
    onServiceLocationChange: (String) -> Unit,
    onScheduledDateChange: (String) -> Unit,
    onSubmitRequest: () -> Unit,
    onAcceptBudget: () -> Unit
) {
    val budget = state.draftBudget

    if (budget.status == BudgetStatus.PENDING && (budget.estimatedValue == null || budget.estimatedValue == 0.0)) {
        MtmTextField(
            label = "Service Title",
            value = budget.serviceTitle ?: "",
            onValueChange = onServiceTitleChange,
            placeholder = "E.g., Electrical Repair",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        MtmTextArea(
            label = "Service Description",
            value = budget.serviceDescription ?: "",
            onValueChange = onServiceDescriptionChange,
            placeholder = "Describe the problem...",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        MtmTextField(
            label = "Service Location",
            value = state.serviceLocationInput,
            onValueChange = onServiceLocationChange,
            placeholder = "Address or Neighborhood",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        MtmTextField(
            label = "Scheduled Date",
            value = budget.scheduledDate ?: "",
            onValueChange = onScheduledDateChange,
            placeholder = "YYYY-MM-DD",
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onSubmitRequest, modifier = Modifier.fillMaxWidth().height(48.dp)) {
            Text("Find Professionals")
        }
    }
    else if (budget.status == BudgetStatus.NEGOTIATING) {
        Text("Professional Notes:", fontWeight = FontWeight.Bold)
        Text(budget.additionalNotes ?: "No specific notes provided.", color = Color.Gray)
        Spacer(modifier = Modifier.height(24.dp))
        Text("Quoted Price: $${budget.estimatedValue}", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(32.dp))
        Button(onClick = onAcceptBudget, modifier = Modifier.fillMaxWidth().height(48.dp)) {
            Text("Accept and Hire Now")
        }
    }
}

@Composable
private fun ProfessionalView(
    state: BudgetUiState,
    onAdditionalNotesChange: (String) -> Unit,
    onQuoteValueChange: (String) -> Unit,
    onSendQuote: () -> Unit
) {
    val budget = state.draftBudget
    Text("Client Request: ${budget.serviceTitle ?: ""}", fontWeight = FontWeight.Bold)
    Text(budget.serviceDescription ?: "", modifier = Modifier.padding(vertical = 8.dp))
    Text("Location: ${state.serviceLocationInput}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)
    Text("Requested Date: ${budget.scheduledDate ?: "Flexible"}", color = Color.Gray, style = MaterialTheme.typography.bodySmall)

    HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

    MtmTextArea(
        label = "Response / Doubts (Optional)",
        value = budget.additionalNotes ?: "",
        onValueChange = onAdditionalNotesChange,
        placeholder = "Ask for more details or set your conditions...",
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
    Spacer(modifier = Modifier.height(32.dp))
    Button(onClick = onSendQuote, modifier = Modifier.fillMaxWidth().height(48.dp)) {
        Text("Send Quote to Client")
    }
}

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true, name = "Client View - Drafting")
@Composable
fun PreviewClientDrafting() {
    val state = BudgetUiState(
        role = ViewerRole.CLIENT,
        draftBudget = Budget(customerId = 1, professionalId = 2, status = BudgetStatus.PENDING)
    )
    MaterialTheme { BudgetContent(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}) }
}

@Preview(showBackground = true, name = "Professional View - Responding")
@Composable
fun PreviewProfessionalResponding() {
    val state = BudgetUiState(
        role = ViewerRole.PROFESSIONAL,
        serviceLocationInput = "Downtown, New York",
        draftBudget = Budget(
            customerId = 1,
            professionalId = 2,
            serviceTitle = "Kitchen Pipe Leak",
            serviceDescription = "Water is dripping under the sink, might need a pipe replacement.",
            scheduledDate = "2026-05-20",
            status = BudgetStatus.PENDING
        )
    )
    MaterialTheme { BudgetContent(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}) }
}

@Preview(showBackground = true, name = "Client View - Negotiating")
@Composable
fun PreviewClientNegotiating() {
    val state = BudgetUiState(
        role = ViewerRole.CLIENT,
        draftBudget = Budget(
            customerId = 1,
            professionalId = 2,
            serviceTitle = "Kitchen Pipe Leak",
            estimatedValue = 120.0,
            additionalNotes = "I can be there at 10 AM. I'll need to buy a new U-joint.",
            status = BudgetStatus.NEGOTIATING
        )
    )
    MaterialTheme { BudgetContent(state, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}) }
}