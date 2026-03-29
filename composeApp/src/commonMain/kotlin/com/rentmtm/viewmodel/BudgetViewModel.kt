package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import com.rentmtm.model.Budget
import com.rentmtm.model.enums.BudgetStatus
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

enum class ViewerRole { CLIENT, PROFESSIONAL }

data class BudgetUiState(
    val role: ViewerRole = ViewerRole.CLIENT,
    val draftBudget: Budget = Budget(
        customerId = 0L,
        professionalId = 0L,
        status = BudgetStatus.PENDING
    ),
    // Buffers para evitar conflitos de tipos (Address/Double) na UI
    val estimatedValueInput: String = "",
    val serviceLocationInput: String = ""
)

class BudgetViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    // --- Client Actions ---
    fun onServiceTitleChanged(title: String) {
        _uiState.update { it.copy(draftBudget = it.draftBudget.copy(serviceTitle = title)) }
    }

    fun onServiceDescriptionChanged(description: String) {
        _uiState.update { it.copy(draftBudget = it.draftBudget.copy(serviceDescription = description)) }
    }

    fun onServiceLocationChanged(location: String) {
        // Atualizamos o buffer de String.
        // A conversão para o objeto Address acontecerá na persistência.
        _uiState.update { it.copy(serviceLocationInput = location) }
    }

    fun onScheduledDateChanged(date: String) {
        _uiState.update { it.copy(draftBudget = it.draftBudget.copy(scheduledDate = date)) }
    }

    fun submitBudgetRequest() {
        println("Tech Lead Log -> Persisting budget to database with status PENDING")
        // Aqui chamaremos o SQLDelight no futuro
    }

    // --- Professional Actions ---
    fun onAdditionalNotesChanged(notes: String) {
        _uiState.update { it.copy(draftBudget = it.draftBudget.copy(additionalNotes = notes)) }
    }

    fun onQuoteValueChanged(text: String) {
        // Validação para aceitar apenas números e um ponto
        if (text.isEmpty() || text.matches(Regex("^\\d*\\.?\\d*$"))) {
            val doubleValue = text.toDoubleOrNull() ?: 0.0
            _uiState.update {
                it.copy(
                    estimatedValueInput = text,
                    draftBudget = it.draftBudget.copy(estimatedValue = doubleValue)
                )
            }
        }
    }

    fun sendQuote() {
        _uiState.update {
            it.copy(draftBudget = it.draftBudget.copy(status = BudgetStatus.NEGOTIATING))
        }
        println("Tech Lead Log -> Budget updated to NEGOTIATING status")
    }

    fun acceptBudget() {
        _uiState.update {
            it.copy(draftBudget = it.draftBudget.copy(status = BudgetStatus.ACCEPTED))
        }
        println("Tech Lead Log -> Budget ACCEPTED by client")
    }
}