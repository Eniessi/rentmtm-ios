package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchProfessionalsViewModel : ViewModel() {

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

    fun resetSearch() {
        _uiState.value = ProfessionalSearchUiState.Idle
    }
}