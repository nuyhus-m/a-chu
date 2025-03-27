package com.ssafy.achu.ui.mypage.userinfo

import com.ssafy.achu.data.model.auth.UserInfoResponse

data class UserInfoUIState (
    val oldPassword : String = "",
    val newPassword : String = "",
    val newPasswordCheck : String = "",
    val newNickname : String = "",
    val phoneNumber : String = "",
    val isPasswordMismatch: Boolean = false,
    val User : UserInfoResponse? = null,
    val isPhoneNumberValid: Boolean = true,
    val isUnCorrectPWD: Boolean = false
)