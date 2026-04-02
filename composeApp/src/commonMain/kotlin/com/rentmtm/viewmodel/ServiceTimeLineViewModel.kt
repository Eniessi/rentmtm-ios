package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.model.enums.ServiceOrderStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class TimelineStep(
    val title: String,
    val description: String,
    val timestamp: String?,
    val isCompleted: Boolean,
    val isCurrent: Boolean,
    val isError: Boolean = false // Para status como CANCELED ou DISPUTE
)

data class ServiceTimelineUiState(
    val isLoading: Boolean = false,
    val orderId: Long = 0L,
    val finalTitle: String = "",
    val currentStatus: ServiceOrderStatus = ServiceOrderStatus.WAITING_START,
    val steps: List<TimelineStep> = emptyList()
)

class ServiceTimelineViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ServiceTimelineUiState())
    val uiState: StateFlow<ServiceTimelineUiState> = _uiState.asStateFlow()

    fun loadServiceTimeline(orderId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            // TODO: Buscar ServiceOrder do repositório
            // Simulando os dados retornados do banco:
            val mockTitle = "Electrical Panel Upgrade"
            val mockStatus = ServiceOrderStatus.IN_PROGRESS

            _uiState.update {
                it.copy(
                    isLoading = false,
                    orderId = orderId,
                    finalTitle = mockTitle,
                    currentStatus = mockStatus,
                    steps = generateTimelineSteps(mockStatus)
                )
            }
        }
    }

    // A Mágica de UI: Transformar o Enum de Domínio num Array Visual
    private fun generateTimelineSteps(status: ServiceOrderStatus): List<TimelineStep> {
        val steps = mutableListOf<TimelineStep>()

        // Passo 1: Criação (Sempre concluído se a OS existe)
        steps.add(
            TimelineStep(
                title = "Order Created",
                description = "Service order was generated and payment authorized.",
                timestamp = "09:00 AM",
                isCompleted = true,
                isCurrent = false
            )
        )

        // Verificamos casos de interrupção (Canceled/Dispute)
        if (status == ServiceOrderStatus.CANCELED || status == ServiceOrderStatus.DISPUTE) {
            steps.add(
                TimelineStep(
                    title = if (status == ServiceOrderStatus.CANCELED) "Order Canceled" else "Dispute Opened",
                    description = "Service execution was interrupted.",
                    timestamp = "10:30 AM",
                    isCompleted = false,
                    isCurrent = true,
                    isError = true
                )
            )
            return steps
        }

        // Fluxo Normal (Waiting -> In Progress -> Completed)
        val isWaiting = status == ServiceOrderStatus.WAITING_START
        steps.add(
            TimelineStep(
                title = "Waiting for Start",
                description = "Waiting for the professional to check-in at the location.",
                timestamp = if (!isWaiting) "09:15 AM" else null,
                isCompleted = status != ServiceOrderStatus.WAITING_START,
                isCurrent = isWaiting
            )
        )

        val isInProgress = status == ServiceOrderStatus.IN_PROGRESS
        steps.add(
            TimelineStep(
                title = "Service in Progress",
                description = "Professional is currently executing the service.",
                timestamp = if (status == ServiceOrderStatus.COMPLETED) "10:00 AM" else null,
                isCompleted = status == ServiceOrderStatus.COMPLETED,
                isCurrent = isInProgress
            )
        )

        val isCompleted = status == ServiceOrderStatus.COMPLETED
        steps.add(
            TimelineStep(
                title = "Service Completed",
                description = "The service was successfully finished and validated.",
                timestamp = if (isCompleted) "02:00 PM" else null,
                isCompleted = isCompleted,
                isCurrent = isCompleted
            )
        )

        return steps
    }
}