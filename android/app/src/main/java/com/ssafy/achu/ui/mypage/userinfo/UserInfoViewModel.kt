package com.ssafy.achu.ui.mypage.userinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.auth.ChangePasswordRequest
import com.ssafy.achu.data.model.auth.ChangePhoneNumberRequest
import com.ssafy.achu.data.model.auth.NickNameRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "UserInfoViewModel 안주현"

class UserInfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserInfoUIState())
    val uiState: StateFlow<UserInfoUIState> = _uiState.asStateFlow()


    fun updateShowNickNameUpdateDialog(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(showNickNameUpdateDialog = show)
        }
    }

    fun updateShowPasswordUpdateDialog(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(showPasswordUpdateDialog = show)
        }
    }

    fun updateLogoutDialog(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(logoutDialog = show)
        }
    }

    fun updateDeleteUserDialog(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(deleteUserDialog = show)
        }
    }


    fun updatePhoneNumber(phoneNumberInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                phoneNumber = phoneNumberInput
            )
        }
    }


    fun updateNickname(nicknameInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                newNickname = nicknameInput,
            )
        }
    }

    fun oldPwd(oldPwdInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                oldPassword = oldPwdInput,
            )
        }
    }

    fun newPwd(newPwdInput: String) {
        Log.d(TAG, "newPwd 호출됨: $newPwdInput")
        Log.d(TAG, "newPwd 변경 전: ${_uiState.value.newPassword}") // 여기 변경

        _uiState.update { currentState ->
            currentState.copy(
                newPassword = newPwdInput,
            )
        }

        Log.d(TAG, "newPwd 변경 후: ${_uiState.value.newPassword}") // 즉시 반영되지 않을 가능성이 있음
    }

    fun newPwdCheck(newPwdCheckInput: String) {
        _uiState.update { currentState ->
            currentState.copy(
                newPasswordCheck = newPwdCheckInput,
            )
        }
    }

    fun allPWDDateDelete() {
        _uiState.update { currentState ->
            currentState.copy(
                oldPassword = "",
                newPassword = "",
                newPasswordCheck = ""
            )
        }
    }

    fun validateAndConfirm(onConfirm: () -> Unit) {
        val state = _uiState.value
        Log.d(TAG, "validateAndConfirm: 함수실행")
        val passwordRegex = "^[A-Za-z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>?/`~]{8,16}$".toRegex()
        val isValid = passwordRegex.matches(state.newPassword)
        val isMatched = state.newPassword == state.newPasswordCheck
        Log.d(TAG, "validateAndConfirm: ${state.newPassword} ${state.newPasswordCheck}")

        if (isValid && isMatched) {
            changePWD(onConfirm)
            allPWDDateDelete()
            Log.d(TAG, "validateAndConfirm: 유효${isValid} 일치${isMatched}")

        } else {
            _uiState.update {
                it.copy(
                    isCorrectPWD = isValid,
                    isPasswordMatch = isMatched,
                    newPassword = if (!isValid) "" else it.newPassword,  // 비밀번호가 유효하지 않다면 초기화
                    newPasswordCheck = if (!isMatched) "" else it.newPasswordCheck  // 확인 비밀번호가 다르면 초기화
                )
            }
            Log.d(TAG, "validateAndConfirm: 유효${isValid} 일치${isMatched}")
        }

    }


    fun mismatchOldPWD() {
        _uiState.update {
            it.copy(
                isCorrectOldPWD = false
            )
        }
    }


    fun changePWD(onConfirm: () -> Unit) {
        viewModelScope.launch {

            userRepository.changePassword(
                ChangePasswordRequest(
                    oldPassword = uiState.value.oldPassword,
                    newPassword = uiState.value.newPassword
                )
            )
                .onSuccess {
                    if (it.result == "SUCCESS") {
                        Log.d(TAG, "changePWD: ${it}")
                        onConfirm()
                    }
                }.onFailure {
                    Log.d(TAG, "changePWD: ${it.message}")
                    mismatchOldPWD()
                }

        }
    }

    fun changeNickname() {
        viewModelScope.launch {

            userRepository.changeNickname(
                NickNameRequest
                    (
                    nickname = uiState.value.newNickname
                )
            )
                .onSuccess {
                    if (it.result == "SUCCESS") {
                        Log.d(TAG, "changeNickname: ${it}")
                    }
                }.onFailure {
                    Log.d(TAG, "changeNickname: ${it.message}")

                }

        }
    }

    fun confirmUniqueNickname() {
        viewModelScope.launch {

            userRepository.checkNicknameUnique(
                nickname = uiState.value.newNickname
            )
                .onSuccess {
                    if (it.result == "SUCCESS") {
                        Log.d(TAG, "confirmUniqueNickname: ${it}")
                        _uiState.update {
                            it.copy(
                                isUniqueNickname = "완료",
                            )
                        }

                    }
                }
                .onFailure {
                    Log.d(TAG, "confirmUniqueNickname: ${it.message}")
                    _uiState.update {
                        it.copy(
                            isUniqueNickname = "중복",
                            newNickname = ""
                        )
                    }

                }
        }
    }


    fun confirmNickname() {
        val state = _uiState.value
        val nicknameRegex = "^[가-힣A-Za-z]{2,6}$".toRegex()
        val isValid = nicknameRegex.matches(state.newNickname)

        if (isValid) {
            confirmUniqueNickname()
        } else {
            _uiState.update {
                it.copy(
                    isCorrectNickname = isValid,
                    newNickname = if (!isValid) "" else it.newNickname,  // 비밀번호가 유효하지 않다면 초기화

                )
            }
        }

    }


    fun changePhoneNumber() {
        viewModelScope.launch {

            userRepository.changePhoneNumber(
                ChangePhoneNumberRequest(
                    phoneNumber = uiState.value.phoneNumber,
                    phoneVerificationCode = ""
                )
            )
                .onSuccess {
                    if (it.result == "SUCCESS") {
                        Log.d(TAG, "changePhoneNumber: ${it}")
                    }
                }.onFailure {
                    Log.d(TAG, "changePhoneNumber: ${it.message}")
                    mismatchOldPWD()
                }

        }
    }

    fun changeProfile(img: MultipartBody.Part) {
        viewModelScope.launch {
            userRepository.uploadProfileImage(
                profileImage = img
            ).onSuccess {
            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "changeProfile: ${errorResponse}")
            }
        }
    }


}