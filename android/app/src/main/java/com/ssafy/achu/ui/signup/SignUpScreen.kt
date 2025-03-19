package com.ssafy.achu.ui.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTextField
import com.ssafy.achu.core.components.PasswordTextField
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PointBlueFlexibleBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen() {
    val margin = 16.dp
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding(horizontal = 24.dp)
    ) {
        Text(
            text = "회원가입",
            style = AchuTheme.typography.bold24,
            modifier = Modifier.padding(vertical = 48.dp)
        )

        // 아이디
        TextFieldWithBtn(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.id),
            placeholder = stringResource(R.string.enter_id),
            buttonText = stringResource(R.string.check),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(margin))

        // 비밀번호
        Text(
            text = stringResource(R.string.password),
            style = AchuTheme.typography.regular18
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            value = "",
            onValueChange = { },
            placeholder = stringResource(R.string.password_format)
        )

        Spacer(modifier = Modifier.height(margin))

        // 비밀번호 확인
        Text(
            text = stringResource(R.string.password_check),
            style = AchuTheme.typography.regular18
        )

        Spacer(modifier = Modifier.height(8.dp))

        PasswordTextField(
            value = "",
            onValueChange = { },
            placeholder = stringResource(R.string.password_format)
        )

        Spacer(modifier = Modifier.height(margin))

        // 닉네임
        TextFieldWithBtn(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.nickname),
            placeholder = stringResource(R.string.nickname_ex),
            buttonText = stringResource(R.string.check),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 전화번호
        TextFieldWithBtn(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.phone_number),
            placeholder = stringResource(R.string.phone_number_ex),
            buttonText = stringResource(R.string.auth),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(48.dp))

        // 회원가입 버튼
        PointBlueButton(
            buttonText = stringResource(R.string.sign_up),
            onClick = {}
        )
    }
}

@Composable
fun TextFieldWithBtn(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    placeholder: String,
    buttonText: String,
    errorMessage: String = "",
    onClick: () -> Unit,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    Column {

        Row {
            Text(
                text = label,
                style = AchuTheme.typography.regular18
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = errorMessage,
                style = AchuTheme.typography.regular12.copy(color = Color.Red)
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                BasicTextField(
                    value = value,
                    onValueChange = onValueChange,
                    placeholder = placeholder,
                    placeholderColor = PointBlue,
                    borderColor = PointBlue,
                    keyboardOptions = keyboardOptions
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            PointBlueFlexibleBtn(
                buttonText = buttonText,
                onClick = onClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    AchuTheme {
        SignUpScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun TextFieldWithBtnPreview() {
    AchuTheme {
//        TextFieldWithBtn()
    }
}