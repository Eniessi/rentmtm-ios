package com.rentmtm.ui.inbox

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.rentmtm.viewmodel.ProfessionalInboxUiState
import com.rentmtm.viewmodel.IncomingBudgetUiModel
import com.rentmtm.viewmodel.ProfessionalInboxViewModel
import com.rentmtm.ui.components.MtmHeadsUpNotification

// -------------------------------------------------------------
// Container Stateful (Acoplado ao ViewModel para Navegação)
// -------------------------------------------------------------
@Composable
fun ProfessionalInboxScreen(
    viewModel: ProfessionalInboxViewModel,
    onBudgetSelected: (Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    ProfessionalInboxContent(
        state = state,
        onBudgetSelected = onBudgetSelected
    )
}

// -------------------------------------------------------------
// Container Stateless (Puro, pronto para o Preview interativo)
// -------------------------------------------------------------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfessionalInboxContent(
    state: ProfessionalInboxUiState,
    onBudgetSelected: (Long) -> Unit
) {
    // Estado local exclusivo para a apresentação da notificação mockada
    var showMockNotification by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("New Requests", fontWeight = FontWeight.Bold) },
                actions = {
                    // O Botão Mágico: Clicar aqui exibe ou esconde a notificação
                    IconButton(onClick = { showMockNotification = !showMockNotification }) {
                        Icon(Icons.Default.Notifications, contentDescription = "Trigger Mock Notification")
                    }
                }
            )
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding).fillMaxSize()) {

            // Camada 1: O Conteúdo da Tela
            when (val currentState = state) {
                is ProfessionalInboxUiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is ProfessionalInboxUiState.Empty -> {
                    EmptyInboxView()
                }
                is ProfessionalInboxUiState.Success -> {
                    RequestsList(
                        requests = currentState.requests,
                        onSelect = onBudgetSelected
                    )
                }
                is ProfessionalInboxUiState.Error -> {
                    Text("Error: ${currentState.message}", color = MaterialTheme.colorScheme.error)
                }
            }

            // Camada 2: A Notificação Flutuante
            AnimatedVisibility(
                visible = showMockNotification,
                enter = slideInVertically(initialOffsetY = { -it }) + fadeIn(),
                exit = slideOutVertically(targetOffsetY = { -it }) + fadeOut(),
                modifier = Modifier
                    .align(Alignment.TopCenter)
                    .padding(top = 8.dp)
                    .zIndex(1f)
            ) {
                MtmHeadsUpNotification(
                    title = "New Service Request!",
                    message = "Alice Freeman needs help with a 'Kitchen Pipe Leak' in Downtown.",
                    timeLabel = "now",
                    onClick = {
                        showMockNotification = false
                        onBudgetSelected(101L)
                    },
                    onDismiss = { showMockNotification = false }
                )
            }
        }
    }
}

// -------------------------------------------------------------
// Componentes Privados
// -------------------------------------------------------------

@Composable
private fun RequestsList(
    requests: List<IncomingBudgetUiModel>,
    onSelect: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(requests) { request ->
            BudgetRequestCard(request = request, onClick = { onSelect(request.budgetId) })
        }
    }
}

@Composable
private fun BudgetRequestCard(
    request: IncomingBudgetUiModel,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.size(48.dp).clip(CircleShape).background(MaterialTheme.colorScheme.secondary),
                contentAlignment = Alignment.Center
            ) {
                Text(request.clientName.take(1), color = Color.White, fontWeight = FontWeight.Bold)
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = request.clientName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "• ${request.timestamp}",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                }

                Text(
                    text = request.serviceTitle,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(vertical = 2.dp)
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Default.Place,
                        contentDescription = null,
                        modifier = Modifier.size(14.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = " ${request.locationSummary}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

@Composable
private fun EmptyInboxView() {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("No new requests yet.", style = MaterialTheme.typography.titleMedium)
        Text("We will notify you when someone needs your services.", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}

// ==========================================
// PREVIEWS
// ==========================================

@Preview(showBackground = true)
@Composable
fun Preview_FullInboxScreen_Interactive() {
    MaterialTheme {
        // Agora passamos a tela COMPLETA para o Preview
        ProfessionalInboxContent(
            state = ProfessionalInboxUiState.Success(
                requests = listOf(
                    IncomingBudgetUiModel(101, "Alice Freeman", "Kitchen Pipe Leak", "Downtown", "5m ago"),
                    IncomingBudgetUiModel(102, "Bob Builder", "New AC Unit Installation", "West Mall", "2h ago")
                )
            ),
            onBudgetSelected = {}
        )
    }
}