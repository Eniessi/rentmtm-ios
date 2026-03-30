package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import com.rentmtm.model.Config
import com.rentmtm.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

// O estado perfeito para a UI consumir (agrega os dois domínios)
data class MyAccountUiState(
    val user: User = User(
        firstName = "John",
        lastName = "Doe",
        email = "john.doe@rentmtm.com",
        phoneNumber = "+1 (555) 123-4567"
    ),
    val config: Config = Config(userId = 1L)
)

class ConfigViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(MyAccountUiState())
    val uiState: StateFlow<MyAccountUiState> = _uiState.asStateFlow()

    // Métodos para atualizar configurações na UI
    fun togglePushNotifications(enabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(config = currentState.config.copy(pushNotificationsEnabled = enabled))
        }
    }

    fun toggleDarkMode(enabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(config = currentState.config.copy(isDarkMode = enabled))
        }
    }

    fun toggleTwoFactorAuth(enabled: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(config = currentState.config.copy(twoFactorAuthEnabled = enabled))
        }
    }
}