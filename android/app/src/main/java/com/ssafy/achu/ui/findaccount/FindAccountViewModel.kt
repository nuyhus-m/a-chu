package com.ssafy.achu.ui.findaccount

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class FindAccountViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(FindAccountUIState())
    val uiState: StateFlow<FindAccountUIState> = _uiState.asStateFlow()

    fun updatePhoneNumber(phoneNumberInput: String) {
        _uiState.update { currentState ->
            currentState.copy(phoneNumber = phoneNumberInput)
        }
    }

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
}