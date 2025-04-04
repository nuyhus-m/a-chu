package com.ssafy.achu.core.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.NumberFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

fun formatPhoneNumber(number: String): String {
    return if (number.length == 11 && number.startsWith("010")) {
        "${number.substring(0, 3)}-${number.substring(3, 7)}-${number.substring(7, 11)}"
    } else {
        number
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatRelativeTime(dateTimeStr: String): String {
    try {
        // 한국 시간대 설정
        val koreaZoneId = ZoneId.of("Asia/Seoul")

        // 입력된 문자열에서 날짜 파싱 (한국 시간대로 변환)
        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val dateTime = LocalDateTime.parse(dateTimeStr, formatter)
        val koreaDateTime = ZonedDateTime.of(dateTime, koreaZoneId)

        // 현재 한국 시간
        val nowKorea = ZonedDateTime.now(koreaZoneId)

        // 시간 차이 계산 (현재 시간 - 주어진 시간)
        var secondsDiff = ChronoUnit.SECONDS.between(koreaDateTime, nowKorea)
        val minutesDiff = ChronoUnit.MINUTES.between(koreaDateTime, nowKorea)
        val hoursDiff = ChronoUnit.HOURS.between(koreaDateTime, nowKorea)
        val daysDiff = ChronoUnit.DAYS.between(koreaDateTime, nowKorea)

        if (secondsDiff < 0) {
            secondsDiff = 0
        }

        return when {
            // 1분 이하: n초 전
            secondsDiff < 60 -> "${secondsDiff}초 전"

            // 1시간 이하: n분 전
            minutesDiff < 60 -> "${minutesDiff}분 전"

            // 24시간 이하: n시간 전
            hoursDiff < 24 -> "${hoursDiff}시간 전"

            // 7일 이하: n일 전
            daysDiff <= 7 -> "${daysDiff}일 전"

            // 그 외: yyyy.MM.dd 형식
            else -> koreaDateTime.format(DateTimeFormatter.ofPattern("yyyy.MM.dd"))
        }
    } catch (e: Exception) {
        return "날짜 형식 오류"
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatDate(input: String): String {
    val inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    val outputFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd")

    val dateTime = LocalDateTime.parse(input, inputFormatter)
    return dateTime.format(outputFormatter)
}

@RequiresApi(Build.VERSION_CODES.O)
fun formatChatRoomTime(timestamp: String): String {
    val formatter = DateTimeFormatter.ISO_DATE_TIME
    val parsedDateTime = LocalDateTime.parse(timestamp, formatter)
    val zonedDateTime = parsedDateTime.atZone(ZoneId.of("Asia/Seoul"))
    val now = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))

    val timeFormatter = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREAN)
    val monthDayFormatter = DateTimeFormatter.ofPattern("M월 d일", Locale.KOREAN)
    val fullDateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd", Locale.KOREAN)

    return when {
        zonedDateTime.toLocalDate() == now.toLocalDate() -> {
            // 같은 날이면 "오전/오후 h:mm"
            zonedDateTime.format(timeFormatter)
        }

        zonedDateTime.toLocalDate() == now.minusDays(1).toLocalDate() -> {
            // 어제면 "어제"
            "어제"
        }

        zonedDateTime.year == now.year -> {
            // 같은 연도면 "M월 d일"
            zonedDateTime.format(monthDayFormatter)
        }

        else -> {
            // 다른 연도면 "yyyy.MM.dd"
            zonedDateTime.format(fullDateFormatter)
        }
    }
}

fun formatPrice(price: Int): String {
    val formattedPrice = NumberFormat.getNumberInstance(Locale.KOREA).format(price)
    return "${formattedPrice}원"
}