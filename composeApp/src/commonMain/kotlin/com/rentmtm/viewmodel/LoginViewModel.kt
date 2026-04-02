package com.rentmtm.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rentmtm.repository.AuthRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class LoginUiState(
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isSuccess: Boolean = false
)

class LoginViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState.asStateFlow()

    fun login(email: String, passwordHash: String) {
        _uiState.update { it.copy(isLoading = true, errorMessage = null) }

        // 🔥 O "PULO DO GATO" PARA DESENVOLVIMENTO
        if (email.trim().equals("1", ignoreCase = true) &&
            passwordHash.trim() == "1") {

            println("Tech Lead Log -> Bypass acionado com sucesso!")
            _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            return
        }

        viewModelScope.launch {
            val success = repository.performLogin(email, passwordHash)

            if (success) {
                _uiState.update { it.copy(isLoading = false, isSuccess = true) }
            } else {
                _uiState.update { it.copy(isLoading = false, errorMessage = "Email ou senha incorretos.") }
            }
        }
    }

    fun resetState() {
        _uiState.update { LoginUiState() }
    }
}