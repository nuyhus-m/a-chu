package com.ssafy.achu.ui.auth.signup

data class SignUpUIState(
    val id: String = "",
    val pwd: String = "",
    val pwdCheck: String = "",
    val nickname: String = "",
    val phoneNumber: String = ""
)
