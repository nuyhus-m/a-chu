package com.ssafy.achu.data.model.baby

data class BabyRequest(
    val birth: List<Int>,
    val gender: String,
    val nickname: String
)