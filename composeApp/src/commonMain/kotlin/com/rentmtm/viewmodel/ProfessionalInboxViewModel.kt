package com.rentmtm.viewmodel

import IBudgetRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProfessionalInboxViewModel(
    private val repository: IBudgetRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ProfessionalInboxUiState>(ProfessionalInboxUiState.Loading)
    val uiState: StateFlow<ProfessionalInboxUiState> = _uiState.asStateFlow()

    init {
        loadIncomingRequests()
    }

    fun loadIncomingRequests() {
        viewModelScope.launch {
            _uiState.value = ProfessionalInboxUiState.Loading

            // Busca os orçamentos no banco de dados "Fake"
            val budgets = repository.getPendingRequestsForProfessional()

            if (budgets.isNotEmpty()) {
                // Mapeamento de Model de Domínio (Budget) para Model de UI (IncomingBudgetUiModel)
                val uiModels = budgets.map { budget ->

                    // 1. Resolvemos o problema do Address.
                    // Tentamos pegar o Address e transformá-lo em String. Se for nulo, usamos o texto padrão.
                    val locationText = budget.serviceLocation?.toString() ?: "Location not provided"

                    IncomingBudgetUiModel(
                        budgetId = budget.id, // ID real do banco
                        clientName = "Client #${budget.customerId}", // Usando o ID do cliente real
                        serviceTitle = budget.serviceTitle ?: "No Title",
                        locationSummary = locationText, // Passando a String limpa que criamos acima
                        timestamp = budget.requestDate ?: "Just now" // Usando a data do Budget
                    )
                }

                _uiState.value = ProfessionalInboxUiState.Success(uiModels)
            } else {
                _uiState.value = ProfessionalInboxUiState.Empty
            }
        }
    }
}