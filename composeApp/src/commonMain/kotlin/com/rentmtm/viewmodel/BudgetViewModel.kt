package com.rentmtm.viewmodel

import IBudgetRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.model.Budget
import com.rentmtm.model.enums.BudgetStatus
import com.rentmtm.utils.ImageStorage
import kotlinx.coroutines.delay
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

    val isLoading: Boolean = false,
    val budget: Budget? = null, // Model que você já possui em com.rentmtm.model.Budget
    val error: String? = null,
    val isProfessionalView: Boolean = false,

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

class BudgetViewModel(
    private val repository: IBudgetRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow(BudgetUiState())
    val uiState: StateFlow<BudgetUiState> = _uiState.asStateFlow()

    private val _createdBudgetId = MutableStateFlow<Long?>(null)
    val createdBudgetId: StateFlow<Long?> = _createdBudgetId.asStateFlow()

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

    fun initializeBudget(budgetId: Long?) {
        if (budgetId == null) {
            _uiState.update { it.copy(role = ViewerRole.CLIENT, budget = null) }
        } else {
            viewModelScope.launch {
                _uiState.update { it.copy(isLoading = true, role = ViewerRole.PROFESSIONAL) }
                val budget = repository.getBudgetById(budgetId)
                if (budget != null) {
                    _uiState.update {
                        it.copy(
                            isLoading = false,
                            budget = budget, // FIX CRÍTICO: Agora o ID estará disponível na UI
                            serviceTitle = budget.serviceTitle ?: "",
                            serviceDescription = budget.serviceDescription ?: "",
                            serviceLocationInput = budget.serviceLocation?.toString() ?: ""
                        )
                    }
                }
            }
        }
    }

    fun loadBudget(budgetId: Long) {
        // Evita recarregar se já estiver processando
        if (_uiState.value.isLoading) return

        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }

            try {
                // Simulação de chamada ao Repository/DB (Budget.sq)
                // Em produção: val result = repository.getBudgetById(budgetId)
                delay(1000)

                // Mock de retorno para teste
                val mockBudget = Budget(
                    id = budgetId,
                    serviceTitle = "Kitchen Pipe Leak",
                    serviceDescription = "Emergency repair needed in the main sink.",
                    customerId = 123L,
                    professionalId = 456L,
                )

                _uiState.update { it.copy(
                    isLoading = false,
                    budget = mockBudget,
                    // Aqui definimos se é visão do profissional.
                    // Em produção, cheque o perfil do usuário logado no AuthRepository.
                    isProfessionalView = true
                )}
            } catch (e: Exception) {
                _uiState.update { it.copy(
                    isLoading = false,
                    error = "Failed to load budget: ${e.message}"
                )}
            }
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
        if (_uiState.value.role != ViewerRole.CLIENT) return
        viewModelScope.launch {
            val currentState = _uiState.value
            val newBudgetRequest = Budget(
                customerId = 99L,
                professionalId = 0L,
                serviceTitle = currentState.serviceTitle,
                serviceDescription = currentState.serviceDescription,
                status = BudgetStatus.PENDING
            )
            // Salva e guarda o ID gerado para a navegação
            val id = repository.createClientRequest(newBudgetRequest)
            _createdBudgetId.value = id
        }
    }

    fun sendQuote(budgetId: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            val value = _uiState.value.estimatedValueInput.toDoubleOrNull() ?: 0.0
            val notes = _uiState.value.additionalNotes

            // 1. Salva no banco de dados "Fake"
            repository.submitProfessionalQuote(budgetId, value, notes)

            // 2. Atualiza o estado da tela
            _uiState.update { it.copy(status = BudgetStatus.QUOTED) }

            // 3. Avisa a UI que pode navegar
            onSuccess()
        }
    }

    fun acceptBudgetQuote(budgetId: Long, onSuccess: (Long) -> Unit) {
        viewModelScope.launch {
            // 1. Aceita a cotação no banco e gera a Ordem de Serviço
            val newOrderId = repository.acceptQuoteAndCreateOrder(budgetId)

            // 2. Atualiza o estado
            _uiState.update { it.copy(status = BudgetStatus.ACCEPTED) }

            // 3. Avisa a UI passando o ID da nova Ordem de Serviço
            onSuccess(newOrderId)
        }
    }

    fun acceptBudget() {
        _uiState.update { it.copy(status = BudgetStatus.ACCEPTED) }
        println("Tech Lead Log -> Budget ACCEPTED")
    }
}