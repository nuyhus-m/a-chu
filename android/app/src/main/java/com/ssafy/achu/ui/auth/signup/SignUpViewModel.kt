package com.ssafy.achu.ui.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.core.util.Constants
import com.ssafy.achu.core.util.getErrorMessage
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SignUpViewModel"

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    private val idRegex = Regex("^[a-zA-Z0-9]{4,16}$")
    private val pwdRegex = Regex("^[A-Za-z0-9!@#$%^&*()_+\\-=~]{8,16}$")
    private val nicknameRegex = Regex("^[a-zA-Z0-9가-힣]{2,6}$")
    private val phoneNumberRegex = Regex("^[0-9]{11}$")

    fun updateId(idInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                id = idInput,
                idMessage =
                    if (!idInput.matches(idRegex)) {
                        "* 4~16자 영문, 숫자만 사용 가능합니다."
                    } else {
                        ""
                    },
                idState = false,
                idButtonState = idInput.matches(idRegex)
            )
        }
        updateButtonState()
    }

    fun updatePwd(pwdInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                pwd = pwdInput,
                pwdMessage =
                    if (!pwdInput.matches(pwdRegex)) {
                        "* 8~16자 영문, 숫자, 특수문자만 사용 가능합니다."
                    } else {
                        ""
                    },
                pwdCheckMessage =
                    if (uiState.value.pwdCheck.isNotEmpty() && pwdInput != uiState.value.pwdCheck && pwdInput.matches(
                            pwdRegex
                        )
                    ) {
                        "* 비밀번호가 일치하지 않습니다."
                    } else {
                        ""
                    },
                pwdState =
                    if (pwdInput.matches(pwdRegex) && uiState.value.pwdCheck == pwdInput) {
                        true
                    } else {
                        false
                    }
            )
        }
        updateButtonState()
    }

    fun updatePwdCheck(pwdCheckInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                pwdCheck = pwdCheckInput,
                pwdCheckMessage =
                    if (uiState.value.pwd.matches(pwdRegex) && pwdCheckInput != uiState.value.pwd) {
                        "* 비밀번호가 일치하지 않습니다."
                    } else {
                        ""
                    },
                pwdState =
                    if (pwdCheckInput.matches(pwdRegex) && pwdCheckInput == uiState.value.pwd) {
                        true
                    } else {
                        false
                    }
            )
        }
        updateButtonState()
    }

    fun updateNickname(nicknameInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                nickname = nicknameInput,
                nicknameMessage =
                    if (!nicknameInput.matches(nicknameRegex)) {
                        "* 2~6자 한글, 영문, 숫자만 사용 가능합니다."
                    } else {
                        ""
                    },
                nicknameState = false,
                nicknameButtonState = nicknameInput.matches(nicknameRegex)
            )
        }
        updateButtonState()
    }

    fun updatePhoneNumber(phoneNumberInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phoneNumber = phoneNumberInput,
                phoneNumberMessage =
                    if (!phoneNumberInput.matches(phoneNumberRegex)) {
                        "* 11자 숫자만 사용 가능합니다."
                    } else {
                        ""
                    },
                phoneNumberState = false,
                phoneNumberButtonState = phoneNumberInput.matches(phoneNumberRegex)
            )
        }
        updateButtonState()
    }

    fun checkIdUnique() {
        viewModelScope.launch {
            userRepository.checkIdUnique(uiState.value.id)
                .onSuccess { response ->
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                idState = response.data.isUnique,
                                idButtonState = false,
                                idMessage =
                                    if (response.data.isUnique) {
                                        "* 사용 가능한 아이디입니다."
                                    } else {
                                        "* 이미 사용중인 아이디입니다."
                                    }
                            )
                        }
                    }
                }.onFailure {
                    val errorMessage = it.getErrorMessage(retrofit)
                    Log.d(TAG, "checkIdUnique error: $errorMessage")
                    Log.d(TAG, "checkIdUnique errorCode: ${it.message}")
                }
        }
    }

    fun checkNicknameUnique() {
        viewModelScope.launch {
            userRepository.checkNicknameUnique(uiState.value.nickname)
                .onSuccess { response ->
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                nicknameState = response.data.isUnique,
                                nicknameButtonState = false,
                                nicknameMessage =
                                    if (response.data.isUnique) {
                                        "* 사용 가능한 닉네임입니다."
                                    } else {
                                        "* 이미 사용중인 닉네임입니다."
                                    }
                            )
                        }
                    }
                }.onFailure {
                    val errorMessage = it.getErrorMessage(retrofit)
                    Log.d(TAG, "checkNicknameUnique error: $errorMessage")
                    Log.d(TAG, "checkNicknameUnique errorCode: ${it.message}")
                }
        }
    }

    private fun updateButtonState() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonState = uiState.value.idState && uiState.value.pwdState && uiState.value.nicknameState && uiState.value.phoneNumberState
            )
        }
    }
}