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

    fun validateAndConfirm(onConfirm: () -> Unit) {
        val state = _uiState.value
        val passwordRegex = "^[A-Za-z0-9!@#\$%^&*()_+\\-=\\[\\]{};':\",.<>?/`~]{8,16}$".toRegex()
        val isValid = passwordRegex.matches(state.newPassword)
        val isMatched = state.newPassword == state.newPasswordCheck

        if (isValid && isMatched) {
            allPWDDateDelete()
            onConfirm
        } else {
            _uiState.update {
                it.copy(
                    isUnCorrectPWD = !isValid,
                    isPasswordMismatch = !isMatched,
                    newPassword = if (!isValid) "" else it.newPassword,  // 비밀번호가 유효하지 않다면 초기화
                    newPasswordCheck = if (!isMatched) "" else it.newPasswordCheck  // 확인 비밀번호가 다르면 초기화
                )
            }
        }
    }

     fun confirmNickname(onConfirm: () -> Unit) {
         val state = _uiState.value
         val nicknameRegex = "^[가-힣A-Za-z]{2,6}$".toRegex()
         val isValid = nicknameRegex.matches(state.newNickname)
         val isUnique = false

         if (isValid && isUnique) {
             allPWDDateDelete()
             onConfirm
         } else {
             _uiState.update {
                 it.copy(
                     isUniqueNickname = isUnique,
                     isCorrectNickname = isValid,
                     newNickname = if (!isValid && !isUnique) "" else it.newNickname,  // 비밀번호가 유효하지 않다면 초기화

                 )
             }
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