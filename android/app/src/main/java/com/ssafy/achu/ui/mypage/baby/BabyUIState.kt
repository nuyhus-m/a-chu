package com.ssafy.achu.ui.mypage.baby

import com.ssafy.achu.data.model.baby.BabyResponse
import okhttp3.MultipartBody
import retrofit2.http.Multipart
import java.util.Calendar

data class BabyUIState (
    val selectedBaby: BabyResponse? = null,
    val selectedPhoto: MultipartBody.Part? = null,

    val babyNickname: String = "",
    val resisterNickname: String = "",
    val calendar: Calendar = Calendar.getInstance(),
    val babyBirth: List<Int> = emptyList<Int>(),
    val babyGender: String = "",
    val babyImgUrl: String = "",
    val isCorrectNickname: Boolean = true,

    val toastString: String = "",
    val isButtonAble: Boolean = true




    )
