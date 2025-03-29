package com.ssafy.achu.ui.mypage.baby

import com.ssafy.achu.data.model.baby.BabyResponse
import java.util.Calendar

data class BabyUIState (
    val selectedBaby: BabyResponse? = null,

    val babyNickname: String = "",
    val calendar: Calendar = Calendar.getInstance(),
    val babyBirth: List<Int> = listOf(
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,
        calendar.get(Calendar.DAY_OF_MONTH)
    ),
    val babyGender: String = "",
    val babyImgUrl: String = "",

    val isCorrectNickname: Boolean = true,
)
