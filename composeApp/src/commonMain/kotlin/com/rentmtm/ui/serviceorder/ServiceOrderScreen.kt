package com.rentmtm.ui.serviceorder

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Message
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.rentmtm.model.enums.ServiceOrderStatus
import com.rentmtm.ui.components.MtmDateField
import com.rentmtm.ui.components.MtmDropdownField
import com.rentmtm.ui.components.MtmTextArea
import com.rentmtm.ui.components.MtmTextField
import com.rentmtm.viewmodel.ServiceOrderViewModel
import com.rentmtm.viewmodel.ViewerRole

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ServiceOrderScreen(
    viewModel: ServiceOrderViewModel,
    onBack: () -> Unit,
    onOpenChat: () -> Unit,
    onNavigateToReview: (isCustomer: Boolean, orderId: Long) -> Unit
) {
    val state by viewModel.uiState.collectAsState()

    // Opções de status formatadas para o Dropdown
    val statusOptions = ServiceOrderStatus.entries.map { it.name }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Service Order #${state.orderId}", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    // ⬅️ BOTÃO DE CHAT NO CABEÇALHO
                    IconButton(onClick = onOpenChat) {
                        Icon(Icons.AutoMirrored.Filled.Message, contentDescription = "Open Chat")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
            )
        },
        bottomBar = {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shadowElevation = 8.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Box(modifier = Modifier.padding(24.dp)) {
                    Button(
                        // --- ALTERAÇÃO AQUI NO ONCLICK ---
                        onClick = {
                            viewModel.saveServiceOrder()
                            // Verifica se quem está a ver é o cliente ou o profissional
                            val isCustomer = state.role == ViewerRole.CLIENT
                            // Aciona a navegação passando o ID da ordem
                            onNavigateToReview(isCustomer, state.orderId)
                        },
                        // ---------------------------------
                        modifier = Modifier.fillMaxWidth().height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text(if (state.role == ViewerRole.PROFESSIONAL) "Update Order" else "Confirm Execution", fontSize = 16.sp)
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

            Text(
                text = "Service Details",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Campos "Read-Only" (Simulados desabilitando a edição com os componentes atuais)
            MtmTextField(
                label = "Service Title",
                value = state.finalTitle,
                onValueChange = {}, // Imutável na view
                placeholder = "",
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            MtmTextField(
                label = "Final Agreed Value ($)",
                value = state.finalValue.toString(),
                onValueChange = {}, // Imutável na view
                placeholder = "",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))
            HorizontalDivider(color = Color.LightGray)
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Execution Tracking",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            MtmDateField(
                label = "Scheduled Start Time",
                value = state.scheduledStartTime,
                onDateSelected = { viewModel.onScheduledTimeChanged(it) }
            )
            Spacer(modifier = Modifier.height(12.dp))

            MtmDropdownField(
                label = "Order Status",
                options = statusOptions,
                selectedOption = state.status.name,
                onOptionSelected = { selectedStr ->
                    viewModel.onStatusChanged(ServiceOrderStatus.valueOf(selectedStr))
                },
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(12.dp))

            // Campo crítico de validação de início do serviço
            MtmTextField(
                label = if (state.role == ViewerRole.CLIENT) "Your Check-In Code (Share with Pro)" else "Enter Client Check-In Code",
                value = state.checkInCode,
                onValueChange = { viewModel.onCheckInCodeChanged(it) },
                placeholder = "e.g., 4829",
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(24.dp))

            if (state.role == ViewerRole.PROFESSIONAL || state.professionalNotes.isNotEmpty()) {
                MtmTextArea(
                    label = "Professional Execution Notes",
                    value = state.professionalNotes,
                    onValueChange = { viewModel.onNotesChanged(it) },
                    placeholder = "Describe what was done, materials used, etc...",
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}