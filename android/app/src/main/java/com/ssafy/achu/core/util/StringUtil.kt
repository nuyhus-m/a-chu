package com.ssafy.achu.core.util

fun formatPhoneNumber(number: String): String {
    return if (number.length == 11 && number.startsWith("010")) {
        "${number.substring(0, 3)}-${number.substring(3, 7)}-${number.substring(7, 11)}"
    } else {
        number
    }
}