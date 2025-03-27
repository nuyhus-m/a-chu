package com.ssafy.achu.ui.mypage.userinfo

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.PointBlue

@Composable
fun PhoneNumberTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = "전화번호 입력",
    pointColor: Color = PointBlue,
    modifier: Modifier = Modifier,
    icon: Int = R.drawable.ic_write
) {
    var cursorPosition by remember { mutableStateOf(0) }

    // 전화번호 변경 시 포맷과 커서 위치 업데이트
    val onPhoneNumberChange: (String) -> Unit = { input ->
        val (formattedPhoneNumber, newCursorPos) = formatPhoneNumber(input, cursorPosition)
        onValueChange(formattedPhoneNumber)
        cursorPosition = newCursorPos
    }

    OutlinedTextField(
        value = value,
        onValueChange = onPhoneNumberChange,
        modifier = modifier.height(50.dp),
        textStyle = AchuTheme.typography.regular16,
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        placeholder = { Text(placeholder, style = AchuTheme.typography.regular16) },
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = pointColor,
            unfocusedBorderColor = pointColor,
            cursorColor = Color.Black,
            focusedLabelColor = pointColor,
            unfocusedLabelColor = pointColor
        ),
        trailingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(icon),
                    tint = pointColor,
                    contentDescription = "Clear text"
                )
            }
        }
    )
}

fun formatPhoneNumber(input: String, currentCursorPos: Int): Pair<String, Int> {
    // 공백 및 특수문자 제거
    val digits = input.replace(Regex("[^0-9]"), "")

    // 포맷팅된 전화번호 생성
    var formattedPhoneNumber = buildString {
        var count = 0
        for (char in digits) {
            // 하이픈을 추가할 위치
            if (count == 3 || count == 7) {
                append('-')
            }
            append(char)
            count++
        }
    }

    // 마지막에 추가된 '-'가 불필요할 수 있으므로 제거
    if (formattedPhoneNumber.endsWith('-')) {
        formattedPhoneNumber = formattedPhoneNumber.dropLast(1)
    }

    // 커서 위치 재조정
    val hyphensBeforeCursor = input.substring(0, currentCursorPos).count { it == '-' }
    val newCursorPos = currentCursorPos +
            (if (currentCursorPos >= 3) 1 else 0) +
            (if (currentCursorPos >= 7) 1 else 0) -
            hyphensBeforeCursor

    return Pair(formattedPhoneNumber, newCursorPos)
} 