package com.ssafy.achu.ui.auth.findaccount

data class FindAccountUIState(
    val phoneNumber: String = "",
    val id: String = "",
    val pwd: String = "",
    val pwdCheck: String = ""
)
