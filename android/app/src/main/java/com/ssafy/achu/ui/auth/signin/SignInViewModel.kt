package com.ssafy.achu.ui.auth.signin

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUIState())
    val uiState: StateFlow<SignInUIState> = _uiState.asStateFlow()

    fun updateId(idInput: String) {
        _uiState.update { currentState ->
            currentState.copy(id = idInput)
        }
    }

    fun updatePwd(pwdInput: String) {
        _uiState.update { currentState ->
            currentState.copy(pwd = pwdInput)
        }
    }
}