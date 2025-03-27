package com.ssafy.achu.ui.auth.signup

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

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

    private fun updateButtonState() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonState = uiState.value.idState && uiState.value.pwdState && uiState.value.nicknameState && uiState.value.phoneNumberState
            )
        }
    }
}