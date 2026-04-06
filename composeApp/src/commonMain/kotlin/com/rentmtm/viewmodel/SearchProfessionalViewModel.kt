package com.rentmtm.viewmodel

import IBudgetRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.model.enums.BudgetStatus
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchProfessionalsViewModel(
    private val repository: IBudgetRepository // Adicione o repositório no construtor
) : ViewModel() {

    private val _recommendedProfessional = MutableStateFlow<ProfessionalUiModel?>(null)
    val recommendedProfessional = _recommendedProfessional.asStateFlow()

    private val _uiState = MutableStateFlow<ProfessionalSearchUiState>(ProfessionalSearchUiState.Idle)
    val uiState: StateFlow<ProfessionalSearchUiState> = _uiState.asStateFlow()

    fun startSearchForService(serviceCategory: String) {
        viewModelScope.launch {
            // Transita para o estado de busca (Ativa a animação de radar)
            _uiState.value = ProfessionalSearchUiState.Searching

            // TODO: Substituir pelo UseCase real que busca no banco/API por proximidade (Raio)
            println("Tech Lead Log -> Searching for professionals near user location for category: $serviceCategory")
            delay(3500) // Fake network/location delay

            // Mocking the result matching your rule: Up to 3 professionals
            val mockedResults = listOf(
                ProfessionalUiModel(1, "Carlos Silva", "Master Electrician", 1.2, 4.9, 142),
                ProfessionalUiModel(2, "Ana Souza", "Residential Electrician", 3.5, 4.7, 89),
                ProfessionalUiModel(3, "Marcos Paulo", "Wiring Specialist", 5.0, 4.8, 210)
            )

            if (mockedResults.isNotEmpty()) {
                _uiState.value = ProfessionalSearchUiState.Success(mockedResults)
            } else {
                _uiState.value = ProfessionalSearchUiState.Empty
            }
        }
    }

    fun checkForBudgetResponse(budgetId: Long) {
        viewModelScope.launch {
            // Vai no banco Fake consultar o orçamento
            val budget = repository.getBudgetById(budgetId)

            println("Tech Lead Log -> Buscando resposta para Budget ID: $budgetId")
            println("Tech Lead Log -> Status atual do Budget no banco: ${budget?.status}")

            // Se o profissional enviou o preço, o status será QUOTED
            if (budget?.status == com.rentmtm.model.enums.BudgetStatus.QUOTED) {
                _recommendedProfessional.value = ProfessionalUiModel(
                    id = budget.professionalId,
                    name = "Recommended: Prof. Allocated",
                    professionTitle = "Service Quote: $${budget.estimatedValue}", // O preço aparece aqui!
                    rating = 5.0,
                    distanceKm = 1.2,
                    totalJobs = 42
                )
                println("Tech Lead Log -> Profissional recomendado CRIADO NA UI com sucesso!")
            } else {
                println("Tech Lead Log -> O status não é QUOTED. Nenhuma recomendação criada.")
            }
        }
    }

    fun resetSearch() {
        _uiState.value = ProfessionalSearchUiState.Idle
    }
}