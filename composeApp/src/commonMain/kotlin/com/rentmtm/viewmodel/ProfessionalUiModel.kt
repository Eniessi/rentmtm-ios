package com.rentmtm.viewmodel

// UI Model: What the screen actually needs to render a card
data class ProfessionalUiModel(
    val id: Long,
    val name: String,
    val professionTitle: String,
    val distanceKm: Double,
    val rating: Double,
    val totalJobs: Int,
    val quoteValue: Double? = null
)

// The State Machine
sealed interface ProfessionalSearchUiState {
    data object Idle : ProfessionalSearchUiState
    data object Searching : ProfessionalSearchUiState
    data class Success(val professionals: List<ProfessionalUiModel>) : ProfessionalSearchUiState
    data class Error(val message: String) : ProfessionalSearchUiState
    data object Empty : ProfessionalSearchUiState // When no one is found
}