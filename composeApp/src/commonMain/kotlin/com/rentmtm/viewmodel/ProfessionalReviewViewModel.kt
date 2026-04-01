package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.model.enums.ReviewerType
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ProfessionalReviewUiState(
    val isLoading: Boolean = false,
    val serviceOrderId: Long = 0L,
    val customerName: String = "",
    val rating: Int = 0,
    val comments: String = "",
    val isSubmitEnabled: Boolean = false,
    val isSuccess: Boolean = false
)

class ProfessionalReviewViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ProfessionalReviewUiState())
    val uiState: StateFlow<ProfessionalReviewUiState> = _uiState.asStateFlow()

    fun loadOrderData(serviceOrderId: Long) {
        _uiState.update {
            it.copy(
                serviceOrderId = serviceOrderId,
                customerName = "Maria Oliveira" // Mock data
            )
        }
    }

    fun onRatingChanged(newRating: Int) {
        val safeRating = newRating.coerceIn(1, 5)
        _uiState.update { state ->
            state.copy(
                rating = safeRating,
                isSubmitEnabled = true
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
            // TODO: Create ServiceFeedback instance here
            // val feedback = ServiceFeedback(
            //     serviceOrderId = currentState.serviceOrderId,
            //     reviewerId = currentProfessionalId,
            //     targetUserId = customerId,
            //     reviewerType = ReviewerType.PROFESSIONAL,
            //     rating = currentState.rating,
            //     comments = currentState.comments,
            //     createdAt = getCurrentTimestamp()
            // )

            println("Tech Lead Log -> Saving unified feedback as PROFESSIONAL.")
            delay(1000)

            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }
}