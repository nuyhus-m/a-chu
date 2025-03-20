package com.ssafy.achu.ui.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

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

    fun updatePwdCheck(pwdCheckInput: String) {
        _uiState.update { currentState ->
            currentState.copy(pwdCheck = pwdCheckInput)
        }
    }

    fun updateNickname(nicknameInput: String) {
        _uiState.update { currentState ->
            currentState.copy(nickname = nicknameInput)
        }
    }

    fun updatePhoneNumber(phoneNumberInput: String) {
        _uiState.update { currentState ->
            currentState.copy(phoneNumber = phoneNumberInput)
        }
    }
}