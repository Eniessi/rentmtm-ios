package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.model.Budget
import com.rentmtm.model.enums.BudgetStatus
import com.rentmtm.utils.ImageStorage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Clock

enum class ViewerRole { CLIENT, PROFESSIONAL }

// PURE UI State. No Domain or Database entities here.
data class BudgetUiState(
    val role: ViewerRole = ViewerRole.CLIENT,
    val status: BudgetStatus = BudgetStatus.PENDING,

    // UI Fields (Dados do Orçamento)
    val serviceTitle: String = "",
    val serviceDescription: String = "",
    val serviceLocationInput: String = "",
    val scheduledDate: String = "",
    val additionalNotes: String = "",
    val estimatedValueInput: String = "",
    val selectedPaymentMethodId: Long? = null,

    // NOVA PROPRIEDADE: Controla as 10 fotos. A chave é o index (0 a 9) e o valor é o caminho local.
    val capturedPhotos: Map<Int, String> = emptyMap(),

    // UI Metadata
    val isSubmitEnabled: Boolean = false
)

class BudgetViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    // Professional ID would typically be injected or passed via navigation args
    private val selectedProfessionalId: Long = 123L
    private val currentCustomerId: Long = 456L // Logged-in user

    fun onServiceTitleChanged(title: String) {
        _uiState.update { it.copy(serviceTitle = title) }
        validateForm()
    }

    fun onServiceDescriptionChanged(description: String) {
        _uiState.update { it.copy(serviceDescription = description) }
        validateForm()
    }

    fun onServiceLocationChanged(location: String) {
        _uiState.update { it.copy(serviceLocationInput = location) }
        validateForm()
    }

    fun onScheduledDateChanged(date: String) {
        _uiState.update { it.copy(scheduledDate = date) }
    }

    fun onPaymentMethodSelected(methodId: Long) {
        _uiState.update { it.copy(selectedPaymentMethodId = methodId) }
    }

    // --- Professional Actions ---
    fun onAdditionalNotesChanged(notes: String) {
        _uiState.update { it.copy(additionalNotes = notes) }
    }

    fun onQuoteValueChanged(text: String) {
        if (text.isEmpty() || text.matches(Regex("^\\d*\\.?\\d*$"))) {
            _uiState.update { it.copy(estimatedValueInput = text) }
        }
    }

    fun saveBudgetPhoto(slotIndex: Int, bytes: ByteArray) {
        viewModelScope.launch {
            val timestamp = Clock.System.now().toEpochMilliseconds()
            val fileName = "budget_photo_slot_${slotIndex}_${timestamp}.jpg"

            try {
                // Vai para a thread de I/O silenciosamente e salva no cache
                val savedPath = ImageStorage.saveImageToCache(bytes, fileName)

                // Atualizamos o mapa mantendo a imutabilidade do StateFlow
                _uiState.update { currentState ->
                    val newPhotosMap = currentState.capturedPhotos.toMutableMap()
                    newPhotosMap[slotIndex] = savedPath
                    currentState.copy(capturedPhotos = newPhotosMap)
                }
                println("Tech Lead Log -> Budget photo saved at: $savedPath for slot $slotIndex")
            } catch (e: Exception) {
                println("Tech Lead Log -> Error saving budget photo: ${e.message}")
            }
        }
    }

    private fun validateForm() {
        val isValid = uiState.value.serviceTitle.isNotBlank() &&
                uiState.value.serviceDescription.isNotBlank() &&
                uiState.value.serviceLocationInput.isNotBlank()
        _uiState.update { it.copy(isSubmitEnabled = isValid) }
    }

    fun submitBudgetRequest() {
        val currentState = uiState.value

        // Explicit mapping from UI State to Domain Entity
        val newBudget = Budget(
            customerId = currentCustomerId,
            professionalId = selectedProfessionalId,
            serviceTitle = currentState.serviceTitle,
            serviceDescription = currentState.serviceDescription,
            serviceLocation = null, // TODO: Implement Geocoding API to convert String to Address
            scheduledDate = currentState.scheduledDate,
            paymentMethodId = currentState.selectedPaymentMethodId,
            status = BudgetStatus.PENDING
        )

        println("Tech Lead Log -> Mapped to entity: $newBudget")
        println("Tech Lead Log -> Persisting budget to database with status PENDING")
    }

    fun sendQuote() {
        _uiState.update { it.copy(status = BudgetStatus.NEGOTIATING) }
        println("Tech Lead Log -> Budget updated to NEGOTIATING with value: ${uiState.value.estimatedValueInput}")
    }

    fun acceptBudget() {
        _uiState.update { it.copy(status = BudgetStatus.ACCEPTED) }
        println("Tech Lead Log -> Budget ACCEPTED")
    }
}