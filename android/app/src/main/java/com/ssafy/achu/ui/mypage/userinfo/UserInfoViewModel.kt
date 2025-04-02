package com.ssafy.achu.ui.mypage.userinfo

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.achu.core.ApplicationClass.Companion.retrofit
import com.ssafy.achu.core.ApplicationClass.Companion.userRepository
import com.ssafy.achu.core.util.Constants
import com.ssafy.achu.core.util.getErrorResponse
import com.ssafy.achu.data.model.auth.ChangePasswordRequest
import com.ssafy.achu.data.model.auth.ChangePhoneNumberRequest
import com.ssafy.achu.data.model.auth.CheckPhoneAuthRequest
import com.ssafy.achu.data.model.auth.NickNameRequest
import com.ssafy.achu.data.model.auth.PhoneAuthRequest
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.MultipartBody

private const val TAG = "UserInfoViewModel 안주현"

class UserInfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserInfoUIState())
    val uiState: StateFlow<UserInfoUIState> = _uiState.asStateFlow()

    private val _isChanged = MutableSharedFlow<Boolean>()
    val isChanged: SharedFlow<Boolean> = _isChanged

    private var _phoneAuthId: String = ""
    private val phoneAuthId: String
        get() = _phoneAuthId


    fun updateToastMessage(message: String) {
        _uiState.update { currentState ->
            currentState.copy(
                toastMessage = message
            )
        }
    }

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

    fun updateVerifyNumber(text: String){
        _uiState.update { currentState ->
            currentState.copy(verifyNumber = text)
        }
    }

    fun updatePhoneNumberDialog(show: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(verifyPhoneNumberDialog = show)
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

                    _isChanged.emit(true)
                    updateToastMessage("닉네임이 변경되었습니다")
                }.onFailure {
                    Log.d(TAG, "changeNickname: ${it.message}")
                    _isChanged.emit(false)
                    updateToastMessage("닉네임 변경 실패")

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
                    phoneVerificationCode = phoneAuthId
                )
            )
                .onSuccess {
                    Log.d(TAG, "changePhoneNumber: ${it}")
                    updateToastMessage("핸드폰번호가 변경되었습니다")
                    _isChanged.emit(true)
                }.onFailure {
                    Log.d(TAG, "changePhoneNumber: ${it.message}")
                    mismatchOldPWD()
                    updateToastMessage("핸드폰번호 변경실패")
                    _isChanged.emit(false)
                }

        }
    }

    fun changeProfile(img: MultipartBody.Part) {
        Log.d(TAG, "changeProfile: ${img}")
        viewModelScope.launch {
            userRepository.uploadProfileImage(
                profileImage = img
            ).onSuccess {
                Log.d(TAG, "changeProfile: ${it}")
                updateToastMessage("프로필이 변경되었습니다.")
                _isChanged.emit(true)


            }.onFailure {
                val errorResponse = it.getErrorResponse(retrofit)
                Log.d(TAG, "changeProfile: ${errorResponse}")
                _isChanged.emit(false)
                updateToastMessage("프로필 변경실패")

                Log.d(TAG, "changeProfile error: ${it.message}")
            }
        }
    }



    fun sendPhoneAuth() {

        viewModelScope.launch {

            val phoneAuthRequest = PhoneAuthRequest(
                phoneNumber = uiState.value.phoneNumber.replace("-", ""),
                purpose = "CHANGE_PHONE_NUMBER"
            )
            userRepository.sendPhoneAuthRequest(phoneAuthRequest)
                .onSuccess { response ->
                    Log.d(TAG, "sendPhoneAuth: $response")
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                verifyPhoneNumberDialog = true,
                            )
                        }
                        _phoneAuthId = response.data.id
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(
                                toastMessage = "전화번호 인증 요청을 실패했습니다."
                            )
                        }
                        _isChanged.emit(false)
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "sendPhoneAuth errorResponse: $errorResponse")
                    Log.d(TAG, "sendPhoneAuth error: ${it.message}")
                    _uiState.update { currentState ->
                        currentState.copy(
                            toastMessage = "전화번호 인증 요청을 실패했습니다."
                        )
                    }
                    _isChanged.emit(false)

                }
        }
    }

    fun checkPhoneAuth() {
        viewModelScope.launch {
            val checkPhoneAuthRequest = CheckPhoneAuthRequest(
                id = phoneAuthId,
                code = uiState.value.verifyNumber
            )
            userRepository.checkPhoneAuth(checkPhoneAuthRequest)
                .onSuccess { response ->
                    Log.d(TAG, "confirmDialog: $response")
                    if (response.result == Constants.SUCCESS) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                toastMessage = "전화번호 인증에 성공했습니다."
                            )
                        }
                        changePhoneNumber()
                    } else {
                        _uiState.update { currentState ->
                            currentState.copy(

                                toastMessage = "전화번호 인증에 실패했습니다."
                            )
                        }

                        Log.d(TAG, "checkPhoneAuth: ${uiState.value.verifyNumber}")
                        Log.d(TAG, "checkPhoneAuth: ${uiState.value.authCode}")
                    }
                }.onFailure {
                    val errorResponse = it.getErrorResponse(retrofit)
                    Log.d(TAG, "checkPhoneAuth: ${uiState.value.verifyNumber}")
                    Log.d(TAG, "checkPhoneAuth: ${uiState.value.authCode}")
                    Log.d(TAG, "confirmDialog errorResponse: $errorResponse")
                    Log.d(TAG, "confirmDialog error: ${it.message}")
                    _uiState.update { currentState ->
                        currentState.copy(
                            toastMessage = "전화번호 인증에 실패했습니다."
                        )
                    }
                }
        }
    }


}