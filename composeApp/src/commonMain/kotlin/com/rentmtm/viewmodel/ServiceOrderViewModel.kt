package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import com.rentmtm.model.enums.ServiceOrderStatus
import com.rentmtm.model.enums.PaymentStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// O estado reflete apenas o que a UI precisa saber, separando a camada de domínio da apresentação.
data class ServiceOrderUiState(
    val isLoading: Boolean = false,
    val role: ViewerRole = ViewerRole.CLIENT, // Reutilizando o enum do Budget

    // Dados Imutáveis (Herdados do Budget)
    val orderId: Long = 0L,
    val finalTitle: String = "",
    val finalValue: Double = 0.0,

    // Dados Mutáveis (O Formulário Real da OS)
    val scheduledStartTime: String = "",
    val checkInCode: String = "",
    val professionalNotes: String = "",
    val status: ServiceOrderStatus = ServiceOrderStatus.WAITING_START,
    val paymentStatus: PaymentStatus = PaymentStatus.PENDING
)

class ServiceOrderViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ServiceOrderUiState())
    val uiState: StateFlow<ServiceOrderUiState> = _uiState.asStateFlow()

    // Na vida real, você passaria o budgetId no construtor ou navegação para buscar no repositório.
    fun loadOrderFromBudget(budgetId: Long) {
        _uiState.update { it.copy(isLoading = true) }

        // Simulação de mapeamento: Budget -> ServiceOrder
        _uiState.update {
            it.copy(
                isLoading = false,
                orderId = 999L,
                finalTitle = "Kitchen Pipe Leak Repair",
                finalValue = 150.00,
                scheduledStartTime = "2026-04-01 14:00"
            )
        }
    }

    fun onScheduledTimeChanged(time: String) {
        _uiState.update { it.copy(scheduledStartTime = time) }
    }

    fun onCheckInCodeChanged(code: String) {
        _uiState.update { it.copy(checkInCode = code) }
    }

    fun onNotesChanged(notes: String) {
        _uiState.update { it.copy(professionalNotes = notes) }
    }

    fun onStatusChanged(newStatus: ServiceOrderStatus) {
        _uiState.update { it.copy(status = newStatus) }
    }

    fun saveServiceOrder() {
        // Aqui você converteria o UiState de volta para a entidade ServiceOrder
        // e chamaria o repositório para dar UPDATE (ex: updateServiceProgression).
        println("Tech Lead Log -> Persistindo OS no banco. Status atual: ${_uiState.value.status}")
    }
}