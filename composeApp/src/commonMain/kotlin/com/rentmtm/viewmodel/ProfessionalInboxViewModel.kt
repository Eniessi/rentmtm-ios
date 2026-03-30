package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfessionalInboxViewModel : ViewModel() {

    private val _uiState = MutableStateFlow<ProfessionalInboxUiState>(ProfessionalInboxUiState.Loading)
    val uiState: StateFlow<ProfessionalInboxUiState> = _uiState.asStateFlow()

    init {
        loadIncomingRequests()
    }

    fun loadIncomingRequests() {
        viewModelScope.launch {
            _uiState.value = ProfessionalInboxUiState.Loading

            // Simulando busca no banco de dados por orçamentos com status PENDING
            // que foram atribuídos a este profissional.
            delay(1500)

            val mockRequests = listOf(
                IncomingBudgetUiModel(
                    budgetId = 101,
                    clientName = "Alice Freeman",
                    serviceTitle = "Kitchen Pipe Leak",
                    locationSummary = "Downtown",
                    timestamp = "10 min ago"
                ),
                IncomingBudgetUiModel(
                    budgetId = 102,
                    clientName = "John Doe",
                    serviceTitle = "Living Room Chandelier Installation",
                    locationSummary = "Saint Peter",
                    timestamp = "1h ago"
                )
            )

            if (mockRequests.isNotEmpty()) {
                _uiState.value = ProfessionalInboxUiState.Success(mockRequests)
            } else {
                _uiState.value = ProfessionalInboxUiState.Empty
            }
        }
    }
}