package com.rentmtm.ui.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.model.enums.BudgetStatus
import com.rentmtm.ui.components.MtmTextArea
import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.viewmodel.BudgetViewModel
import com.rentmtm.viewmodel.ViewerRole
import com.rentmtm.viewmodel.BudgetUiState

@Composable
fun MtmStatusBadge(status: BudgetStatus) {
    val (backgroundColor, textColor, label) = when (status) {
        BudgetStatus.PENDING -> Triple(Color(0xFFFFF3CD), Color(0xFF856404), "OPEN REQUEST")
        BudgetStatus.QUOTED -> Triple(Color(0xFFCCE5FF), Color(0xFF004085), "AWAITING APPROVAL")
        BudgetStatus.ACCEPTED -> Triple(Color(0xFFD4EDDA), Color(0xFF155724), "IN PROGRESS")
        BudgetStatus.COMPLETED -> Triple(Color(0xFFE2E3E5), Color(0xFF383D41), "CLOSED")
        else -> Triple(Color.LightGray, Color.DarkGray, "UNKNOWN")
    }

    Surface(
        color = backgroundColor,
        shape = RoundedCornerShape(4.dp),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text(
            text = label,
            color = textColor,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        )
    }
}

@Composable
fun BudgetScreen(
    viewModel: BudgetViewModel,
    onBack: () -> Unit,
    onNextToPhotos: () -> Unit,
    onQuoteSent: (Long) -> Unit, // NOVO
    onAcceptQuote: (Long) -> Unit // NOVO
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
        onSubmitRequest = onNextToPhotos,
        // Ao clicar, o ViewModel processa e devolve o sucesso para a navegação
        onSendQuote = {
            state.budget?.id?.let { id ->
                viewModel.sendQuote(budgetId = id, onSuccess = { onQuoteSent(id) })
            }
        },
        onAcceptQuote = {
            state.budget?.id?.let { id ->
                viewModel.acceptBudgetQuote(budgetId = id, onSuccess = { orderId -> onAcceptQuote(orderId) })
            }
        }
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
    onSendQuote: () -> Unit = {},
    onAcceptQuote: () -> Unit = {}
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
        if (state.isLoading) {
            Box(modifier = Modifier.fillMaxSize().padding(innerPadding), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            return@Scaffold // Evita renderizar o resto da tela enquanto carrega
        }
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 24.dp)
        ) {
            when (state.role) {
                ViewerRole.CLIENT -> {
                    // AQUI É A GRANDE MUDANÇA ARQUITETURAL
                    if (state.status == BudgetStatus.QUOTED) {
                        ClientReviewQuoteView(state = state, onAcceptQuote = onAcceptQuote)
                    } else {
                        ClientDraftingView( state = state,
                            onServiceTitleChange = onServiceTitleChange,
                            onServiceDescriptionChange = onServiceDescriptionChange,
                            onServiceLocationChange = onServiceLocationChange,
                            onScheduledDateChange = onScheduledDateChange,
                            onPaymentMethodChange = onPaymentMethodChange )
                    }
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
                    Text("Next: Add Photos")
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

@Composable
private fun ClientReviewQuoteView(
    state: BudgetUiState,
    onAcceptQuote: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        MtmStatusBadge(status = state.status)

        Text("Professional's Quote", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)

        Card(modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp)) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = "Estimated Cost: $${state.budget?.estimatedValue ?: "0.00"}",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text("Notes from Professional:", fontWeight = FontWeight.SemiBold)
                Text(state.budget?.additionalNotes ?: "No additional notes provided.")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "By confirming, you agree to hire this professional for the stated amount and initiate the Service Order.",
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = onAcceptQuote,
            modifier = Modifier.fillMaxWidth().height(50.dp)
        ) {
            Text("Accept Quote & Hire Professional")
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