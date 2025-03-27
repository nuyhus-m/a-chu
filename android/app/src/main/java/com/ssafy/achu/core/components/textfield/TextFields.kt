package com.ssafy.achu.core.components.textfield

import android.R.attr.singleLine
import android.R.attr.textStyle
import android.graphics.drawable.Icon
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink

// 기본 TextField
@Composable
fun BasicTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    placeholderColor: Color,
    borderColor: Color,
    radius: Int = 30,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false
) {
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
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
    )
}

// 비밀번호 TextField
@Composable
fun PasswordTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String = stringResource(R.string.enter_password)
) {
    var passwordVisible by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        placeholder = {
            Text(
                text = placeholder,
                style = AchuTheme.typography.regular16.copy(color = PointBlue)
            )
        },
        textStyle = AchuTheme.typography.regular16,
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = PointBlue,
            unfocusedBorderColor = PointBlue,
            cursorColor = Color.Black
        ),
        trailingIcon = {
            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                Icon(
                    painter = if (passwordVisible) painterResource(R.drawable.ic_eye) else painterResource(
                        R.drawable.ic_eye_off
                    ),
                    tint = PointBlue,
                    contentDescription = "Toggle password visibility"
                )
            }
        },
        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation('●'),
    )
}

// 연필 아이콘 TextField
@Composable
fun ClearTextField(
    value: String,
    onValueChange: (String) -> Unit,
    pointColor: Color = PointBlue,
    modifier: Modifier = Modifier // modifier를 외부에서 설정할 수 있도록 추가
    , icon: Int = R.drawable.ic_write
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = modifier
            .height(50.dp),
        textStyle = AchuTheme.typography.regular16,
        singleLine = true,
        shape = RoundedCornerShape(30.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = pointColor,
            unfocusedBorderColor = pointColor,
            cursorColor = Color.Black
        ),
        trailingIcon = {
            IconButton(onClick = { onValueChange("") }) {
                Icon(
                    painter = painterResource(icon),
                    tint = pointColor,
                    contentDescription = "Clear text"
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun BasicTextFieldPreview() {
    AchuTheme {
        BasicTextField(
            value = "",
            onValueChange = {},
            placeholder = "글 제목",
            placeholderColor = LightGray,
            borderColor = PointBlue,
            radius = 8
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PasswordTextFieldPreview() {
    AchuTheme {
        PasswordTextField(
            value = "",
            onValueChange = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ClearTextFieldPreview() {
    AchuTheme {
        ClearTextField(
            value = "",
            onValueChange = {},
            pointColor = PointPink
        )
    }
}


