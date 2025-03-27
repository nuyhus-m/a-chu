package com.ssafy.achu.ui.auth.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.authRepository
import com.ssafy.achu.data.model.auth.SignInRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SignInViewModel"

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUIState())
    val uiState: StateFlow<SignInUIState> = _uiState.asStateFlow()

    fun updateId(idInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                id = idInput,
                buttonState = idInput.isNotEmpty() && currentState.pwd.isNotEmpty()
            )
        }
    }

    fun updatePwd(pwdInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                pwd = pwdInput,
                buttonState = currentState.id.isNotEmpty() && pwdInput.isNotEmpty()
            )
        }
    }

    fun signIn() {
        viewModelScope.launch {
            val signInRequest = SignInRequest(
                username = uiState.value.id,
                password = uiState.value.pwd
            )
            authRepository.signIn(signInRequest)
                .onSuccess { response ->
                    Log.d(TAG, "signIn: $response")
                    if (response.result == "SUCCESS") {
                        _uiState.update { currentState ->
                            currentState.copy(
                                signInSuccess = true
                            )
                        }
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(
                                signInSuccess = false
                            )
                        }
                    }
                }.onFailure {
                    Log.d(TAG, "signIn: $it")
                    _uiState.update { currentState ->
                        currentState.copy(
                            signInSuccess = false
                        )
                    }
                }
        }
    }
}