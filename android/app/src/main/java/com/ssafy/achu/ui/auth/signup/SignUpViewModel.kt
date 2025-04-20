package com.ssafy.achu.ui.auth.signup

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.core.util.Constants
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.auth.CheckPhoneAuthRequest
import com.ssafy.achu.data.model.auth.PhoneAuthRequest
import com.ssafy.achu.data.model.auth.SignUpRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

private const val TAG = "SignUpViewModel"

class SignUpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(SignUpUIState())
    val uiState: StateFlow<SignUpUIState> = _uiState.asStateFlow()

    private val _toastMessage = MutableSharedFlow<String>()
    val toastMessage: SharedFlow<String> = _toastMessage

    private var _phoneAuthId: String = ""
    private val phoneAuthId: String
        get() = _phoneAuthId

    private val idRegex = Regex("^[a-zA-Z0-9]{4,16}$")
    private val pwdRegex = Regex("^[A-Za-z0-9!@#$%^&*()_+\\-=~]{8,16}$")
    private val nicknameRegex = Regex("^[a-zA-Z0-9가-힣]{2,6}$")
    private val phoneNumberRegex = Regex("^[0-9]{11}$")
    val numericRegex = Regex("[^0-9]")

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
        val stripped = numericRegex.replace(phoneNumberInput, "")
        val value =
            if (stripped.length >= 11) {
                stripped.substring(0..10)
            } else {
                stripped
            }
        _uiState.update { currentState ->
            currentState.copy(
                phoneNumber = value,
                phoneNumberMessage = "",
                phoneNumberState = false,
                phoneNumberButtonState = value.matches(phoneNumberRegex)
            )
        }
        updateButtonState()
    }

    fun checkIdUnique() {
        _uiState.update { currentState ->
            currentState.copy(
                idButtonState = false
            )
        }
        viewModelScope.launch {
            userRepository.checkIdUnique(uiState.value.id)
                .onSuccess { response ->
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                idState = response.data.isUnique,
                                idMessage =
                                    if (response.data.isUnique) {
                                        "* 사용 가능한 아이디입니다."
                                    } else {
                                        "* 이미 사용중인 아이디입니다."
                                    }
                            )
                        }
                    }
                    updateButtonState()
                }.onFailure {
                    val errorMessage = it.getErrorResponse(retrofit)
                    Log.d(TAG, "checkIdUnique error: $errorMessage")
                    Log.d(TAG, "checkIdUnique errorCode: ${it.message}")
                }
        }
    }

    fun checkNicknameUnique() {
        _uiState.update { currentState ->
            currentState.copy(
                nicknameButtonState = false
            )
        }
        viewModelScope.launch {
            userRepository.checkNicknameUnique(uiState.value.nickname)
                .onSuccess { response ->
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                nicknameState = response.data.isUnique,
                                nicknameMessage =
                                    if (response.data.isUnique) {
                                        "* 사용 가능한 닉네임입니다."
                                    } else {
                                        "* 이미 사용중인 닉네임입니다."
                                    }
                            )
                        }
                    }
                    updateButtonState()
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "checkNicknameUnique errorResponse: $errorResponse")
                    Log.d(TAG, "checkNicknameUnique error: ${it.message}")
                }
        }
    }

    fun sendPhoneAuth() {
        _uiState.update { currentState ->
            currentState.copy(
                phoneNumberButtonState = false
            )
        }
        viewModelScope.launch {
            val phoneAuthRequest = PhoneAuthRequest(
                phoneNumber = uiState.value.phoneNumber,
                purpose = "SIGN_UP"
            )
            userRepository.sendPhoneAuthRequest(phoneAuthRequest)
                .onSuccess { response ->
                    Log.d(TAG, "sendPhoneAuth: $response")
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showDialog = true,
                                phoneNumberButtonState = false
                            )
                        }
                        _phoneAuthId = response.data.id
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(
                                phoneNumberMessage = "* 전화번호 인증 요청을 실패했습니다."
                            )
                        }
                        _uiState.update { currentState ->
                            currentState.copy(
                                phoneNumberButtonState = uiState.value.phoneNumberState
                            )
                        }
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "sendPhoneAuth errorResponse: $errorResponse")
                    Log.d(TAG, "sendPhoneAuth error: ${it.message}")
                    _uiState.update { currentState ->
                        currentState.copy(
                            phoneNumberMessage = "* 전화번호 인증 요청을 실패했습니다."
                        )
                    }
                    _uiState.update { currentState ->
                        currentState.copy(
                            phoneNumberButtonState = uiState.value.phoneNumberState
                        )
                    }
                }
        }
    }

    fun checkPhoneAuth() {
        viewModelScope.launch {
            val checkPhoneAuthRequest = CheckPhoneAuthRequest(
                id = phoneAuthId,
                code = uiState.value.authCode
            )
            userRepository.checkPhoneAuth(checkPhoneAuthRequest)
                .onSuccess { response ->
                    Log.d(TAG, "confirmDialog: $response")
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                showDialog = false,
                                phoneNumberState = true,
                                phoneNumberMessage = "* 전화번호 인증에 성공했습니다."
                            )
                        }
                        dismissDialog()
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(
                                phoneNumberMessage = "* 전화번호 인증에 실패했습니다."
                            )
                        }
                        dismissDialog()
                    }
                    updateButtonState()
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "confirmDialog errorResponse: $errorResponse")
                    Log.d(TAG, "confirmDialog error: ${it.message}")
                    _uiState.update { currentState ->
                        currentState.copy(
                            phoneNumberMessage = "* 전화번호 인증에 실패했습니다."
                        )
                    }
                    dismissDialog()
                }
        }
    }

    fun updateAuthCode(code: String) {
        _uiState.update { currentState ->
            currentState.copy(
                authCode = code
            )
        }
    }

    fun dismissDialog() {
        _uiState.update { currentState ->
            currentState.copy(
                showDialog = false
            )
        }
    }

    private fun updateButtonState() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonState = uiState.value.idState && uiState.value.pwdState && uiState.value.nicknameState && uiState.value.phoneNumberState
            )
        }
    }

    fun signUp() {
        _uiState.update { currentState ->
            currentState.copy(
                buttonState = false
            )
        }
        viewModelScope.launch {
            val signUpRequest =
                SignUpRequest(
                    nickname = uiState.value.nickname,
                    password = uiState.value.pwd,
                    phoneNumber = uiState.value.phoneNumber,
                    username = uiState.value.id,
                    verificationCodeId = phoneAuthId
                )
            userRepository.signUp(signUpRequest)
                .onSuccess { response ->
                    Log.d(TAG, "signUp: $response")
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                signUpSuccess = true
                            )
                        }
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "signUp errorResponse: $errorResponse")
                    Log.d(TAG, "signUp error: ${it.message}")
                    updateButtonState()
                    _toastMessage.emit("회원가입에 실패했습니다. 다시 시도해주세요.")
                }
        }
    }
}