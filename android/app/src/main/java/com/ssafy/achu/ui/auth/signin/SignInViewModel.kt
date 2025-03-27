package com.ssafy.achu.ui.auth.signin

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.authRepository
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.achu.core.util.Constants
import com.ssafy.achu.core.util.getErrorMessage
import com.ssafy.achu.data.model.auth.SignInRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SignInViewModel"

class SignInViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignInUIState())
    val uiState: StateFlow<SignInUIState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage

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
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                signInSuccess = true
                            )
                        }
                        sharedPreferencesUtil.saveTokens(response.data)
                    }
                }.onFailure {
                    val errorMessage = it.getErrorMessage(retrofit)
                    Log.d(TAG, "signIn error: $errorMessage")
                    Log.d(TAG, "signIn errorCode: ${it.message}")

                    _toastMessage.emit(errorMessage)

                    _uiState.update { currentState ->
                        currentState.copy(
                            signInSuccess = false
                        )
                    }
                }
        }
    }
}