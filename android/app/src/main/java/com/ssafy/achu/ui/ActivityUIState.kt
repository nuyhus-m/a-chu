package com.ssafy.achu.ui

import com.ssafy.achu.data.model.auth.UserInfoResponse
import com.ssafy.achu.data.model.baby.BabyResponse

data class ActivityUIState(
    val user: UserInfoResponse? = null,

    val babyList: List<BabyResponse> = emptyList(),
    val selectedBaby: BabyResponse? = null

)