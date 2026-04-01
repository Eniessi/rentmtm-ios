package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.model.enums.ProfessionalType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfessionalActivityUiState(
    val isLoading: Boolean = false,
    val professionalId: Long = 0L,
    val professionalType: ProfessionalType = ProfessionalType.OTHER,
    val baseServiceDescription: String = "",
    val shortDescription: String = "",
    val detailedDescription: String = "",
    val isSuccess: Boolean = false
) {
    // SOLID: Encapsulating validation logic within the state
    val isSubmitEnabled: Boolean
        get() = shortDescription.isNotBlank() && detailedDescription.isNotBlank()
}

class ProfessionalActivityViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfessionalActivityUiState())
    val uiState: StateFlow<ProfessionalActivityUiState> = _uiState.asStateFlow()

    fun loadProfessionalContext(professionalId: Long) {
        // TODO: Inject repository to fetch professional type and base description
        _uiState.update {
            it.copy(
                professionalId = professionalId,
                professionalType = ProfessionalType.PLUMBER, // Mock
                baseServiceDescription = "Installation, repair, and maintenance of pipes, valves, fittings, drainage systems, and fixtures in commercial and residential structures." // Mock
            )
        }
    }

    fun onShortDescriptionChanged(value: String) {
        // Limit to, say, 50 characters for a short description
        if (value.length <= 50) {
            _uiState.update { it.copy(shortDescription = value) }
        }
    }

    fun onDetailedDescriptionChanged(value: String) {
        _uiState.update { it.copy(detailedDescription = value) }
    }

    fun submitActivity() {
        if (!_uiState.value.isSubmitEnabled) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            // TODO: Persist the ProfessionalActivity in the repository
            println("Tech Lead Log -> Saving Activity: ${_uiState.value.shortDescription}")
            delay(1000) // Simulating network/db latency

            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }
}