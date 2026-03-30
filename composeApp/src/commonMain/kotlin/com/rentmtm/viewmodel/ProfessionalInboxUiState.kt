package com.rentmtm.viewmodel

data class IncomingBudgetUiModel(
    val budgetId: Long,
    val clientName: String,
    val serviceTitle: String,
    val locationSummary: String,
    val timestamp: String
)

sealed interface ProfessionalInboxUiState {
    data object Loading : ProfessionalInboxUiState
    data class Success(val requests: List<IncomingBudgetUiModel>) : ProfessionalInboxUiState
    data object Empty : ProfessionalInboxUiState
    data class Error(val message: String) : ProfessionalInboxUiState
}