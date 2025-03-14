package com.ssafy.achu.core.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue

@Composable
fun TextFieldBasic(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    placeholderColor: Color,
    borderColor: Color,
    radius: Int = 30,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    var textState by remember { mutableStateOf("") }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        placeholder = {
            Text(
                text = placeholder,
                style = AchuTheme.typography.regular16.copy(color = placeholderColor)
            )
        },
        textStyle = AchuTheme.typography.regular16,
        singleLine = true,
        shape = RoundedCornerShape(radius.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = borderColor,
            unfocusedBorderColor = borderColor,
            cursorColor = Color.Black
        ),
        trailingIcon = trailingIcon
    )
}

@Preview(showBackground = true)
@Composable
fun TextFieldBasicPreview() {
    AchuTheme {
        TextFieldBasic(
            value = "",
            onValueChange = {},
            placeholder = "비밀번호를 입력해주세요",
            placeholderColor = LightGray,
            borderColor = PointBlue,
            radius = 8,
            trailingIcon = {
                IconButton(onClick = { /* 아이콘 클릭 시 동작 */ }) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear text"
                    )
                }
            }
        )
    }
}