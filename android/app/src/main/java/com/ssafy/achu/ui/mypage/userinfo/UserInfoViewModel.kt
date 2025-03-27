package com.ssafy.achu.ui.mypage.userinfo

import androidx.lifecycle.ViewModel
import com.ssafy.achu.data.model.auth.UserInfoResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

private const val TAG = "UserInfoViewModel"

class UserInfoViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(UserInfoUIState())
    val uiState: StateFlow<UserInfoUIState> = _uiState.asStateFlow()

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
        _uiState.update { currentState ->
            currentState.copy(
                newPassword = newPwdInput,
            )
        }
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

    fun validateAndConfirm(onSuccess: () -> Unit) {
        val state = _uiState.value
        if (state.newPassword == state.newPasswordCheck) {
            onSuccess()
            allPWDDateDelete()
        } else {
            _uiState.update { it.copy(isPasswordMismatch = true) }
            newPwdCheck("")
        }
    }


//
//    fun changePWD() {
//        viewModelScope.launch {
//
//            runCatching {
//                RetrofitUtil.userService.changePassword(
//                    ChangePasswordRequest(
//                        oldPassword = uiState.value.oldPassword,
//                        newPassword = uiState.value.newPassword
//                    )
//                )
//            }.onSuccess {
//                if (it.result == "SUCCESS"){
//
//                }
//            }.onFailure {
//                Log.d(TAG, "changePWD: ${it.message}")
//            }
//
//        }
//    }


}