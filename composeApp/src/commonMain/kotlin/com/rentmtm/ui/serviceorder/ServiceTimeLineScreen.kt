package com.rentmtm.ui.serviceorder

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.model.enums.ServiceOrderStatus
import com.rentmtm.ui.components.MtmTimelineNode
import com.rentmtm.viewmodel.ServiceTimelineUiState
import com.rentmtm.viewmodel.ServiceTimelineViewModel
import com.rentmtm.viewmodel.TimelineStep

@Composable
fun ServiceTimelineScreen(
    viewModel: ServiceTimelineViewModel,
    onBack: () -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    ServiceTimelineContent(
        state = state,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceTimelineContent(
    state: ServiceTimelineUiState,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Service Timeline", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Cabeçalho com o Status em Destaque
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
                elevation = CardDefaults.cardElevation(0.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Order #${state.orderId}", fontSize = 14.sp, color = MaterialTheme.colorScheme.onSecondaryContainer.copy(alpha = 0.7f))
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(state.finalTitle, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onSecondaryContainer)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Chip de Status
                    val (statusColor, statusText) = when (state.currentStatus) {
                        ServiceOrderStatus.WAITING_START -> Color(0xFFFFA000) to "Waiting Start"
                        ServiceOrderStatus.IN_PROGRESS -> MaterialTheme.colorScheme.primary to "In Progress"
                        ServiceOrderStatus.COMPLETED -> Color(0xFF388E3C) to "Completed"
                        ServiceOrderStatus.CANCELED -> MaterialTheme.colorScheme.error to "Canceled"
                        ServiceOrderStatus.DISPUTE -> MaterialTheme.colorScheme.error to "In Dispute"
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(16.dp))
                            .background(statusColor.copy(alpha = 0.1f))
                            .padding(horizontal = 12.dp, vertical = 6.dp)
                    ) {
                        Text(statusText, color = statusColor, fontWeight = FontWeight.Bold, fontSize = 14.sp)
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))
            Text("Progress Tracking", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(24.dp))

            // A Lista da Timeline
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                itemsIndexed(state.steps) { index, step ->
                    MtmTimelineNode(
                        step = step,
                        isLastNode = index == state.steps.lastIndex
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun ServiceTimelineContentPreview() {
    MaterialTheme {
        ServiceTimelineContent(
            state = ServiceTimelineUiState(
                orderId = 1002,
                finalTitle = "Bathroom Plumbing Installation",
                currentStatus = ServiceOrderStatus.IN_PROGRESS,
                steps = listOf(
                    TimelineStep("Order Created", "Service order was generated and payment authorized.", "09:00 AM", isCompleted = true, isCurrent = false),
                    TimelineStep("Waiting for Start", "Waiting for the professional to check-in at the location.", "09:15 AM", isCompleted = true, isCurrent = false),
                    TimelineStep("Service in Progress", "Professional is currently executing the service.", null, isCompleted = false, isCurrent = true),
                    TimelineStep("Service Completed", "The service was successfully finished and validated.", null, isCompleted = false, isCurrent = false)
                )
            ),
            onBack = {}
        )
    }
}