package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.model.ProfessionalActivity
import com.rentmtm.model.enums.ProfessionalType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ActivityListUiState(
    val isLoading: Boolean = false,
    val selectedType: ProfessionalType? = null, // null significa "All"
    val activities: List<ProfessionalActivity> = emptyList(),
    val availableTypes: List<ProfessionalType> = ProfessionalType.entries
)

class ProfessionalActivityListViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(ActivityListUiState())
    val uiState: StateFlow<ActivityListUiState> = _uiState.asStateFlow()

    fun loadActivities(professionalId: Long) {
        _uiState.update { it.copy(isLoading = true) }
        viewModelScope.launch {
            // Em produção, aqui chamaria o repository.getActivities(professionalId, type)
            // Simulando dados baseados no tipo selecionado
            val mockData = generateMockActivities(professionalId, _uiState.value.selectedType)

            _uiState.update { it.copy(
                isLoading = false,
                activities = mockData
            ) }
        }
    }

    fun onTypeSelected(type: ProfessionalType?) {
        _uiState.update { it.copy(selectedType = if (it.selectedType == type) null else type) }
        // Recarregar a lista baseada no novo filtro
        loadActivities(0L) // Usando ID fictício para o exemplo
    }

    private fun generateMockActivities(profId: Long, type: ProfessionalType?): List<ProfessionalActivity> {
        // Lógica de mock para o Preview e testes iniciais
        return listOf(
            ProfessionalActivity(1, profId, ProfessionalType.PLUMBER, "Sink Repair", "Fixed a leaking sink in the kitchen.", "2024-03-20"),
            ProfessionalActivity(2, profId, ProfessionalType.ELECTRICIAN, "Panel Upgrade", "Upgraded the main electrical panel.", "2024-03-21")
        ).filter { type == null || it.professionalType == type }
    }
}