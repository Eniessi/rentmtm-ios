package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ServiceReviewUiState(
    val isLoading: Boolean = false,
    val serviceOrderId: Long = 0L,
    val professionalName: String = "",
    val rating: Int = 0,
    val comments: String = "",
    val isSubmitEnabled: Boolean = false,
    val isSuccess: Boolean = false
)

class ServiceReviewViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ServiceReviewUiState())
    val uiState: StateFlow<ServiceReviewUiState> = _uiState.asStateFlow()

    fun loadServiceDetails(serviceOrderId: Long) {
        // TODO: Injetar repositório e buscar dados reais da OS
        _uiState.update {
            it.copy(
                serviceOrderId = serviceOrderId,
                professionalName = "Carlos Silva" // Mock
            )
        }
    }

    fun onRatingChanged(newRating: Int) {
        val safeRating = newRating.coerceIn(1, 5)
        _uiState.update { state ->
            state.copy(
                rating = safeRating,
                isSubmitEnabled = true // Só permite submeter se tiver pelo menos 1 estrela
            )
        }
    }

    fun onCommentsChanged(text: String) {
        _uiState.update { it.copy(comments = text) }
    }

    fun submitReview() {
        val currentState = _uiState.value
        if (currentState.rating < 1) return

        _uiState.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            // TODO: Converter UiState para o model CustomerFeedback e persistir no repositório.
            // Exemplo:
            // val feedback = CustomerFeedback(..., rating = currentState.rating, comments = currentState.comments)
            // repository.saveFeedback(feedback)

            println("Tech Lead Log -> Persisting feedback. Rating: ${currentState.rating}, Comments: ${currentState.comments}")

            // Simula latência de rede
            kotlinx.coroutines.delay(1000)

            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }
}