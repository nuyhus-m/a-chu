package com.ssafy.achu.ui.signup

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PwdTextFieldWithLabel
import com.ssafy.achu.core.components.TextFieldWithLabelAndBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen() {

    val space = 16.dp

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
        TextFieldWithLabelAndBtn(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.id),
            placeholder = stringResource(R.string.enter_id),
            buttonText = stringResource(R.string.check),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호
        PwdTextFieldWithLabel(
            label = stringResource(R.string.password)
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호 확인
        PwdTextFieldWithLabel(
            label = stringResource(R.string.password_check)
        )

        Spacer(modifier = Modifier.height(space))

        // 닉네임
        TextFieldWithLabelAndBtn(
            value = "",
            onValueChange = {},
            label = stringResource(R.string.nickname),
            placeholder = stringResource(R.string.nickname_ex),
            buttonText = stringResource(R.string.check),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 전화번호
        TextFieldWithLabelAndBtn(
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

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SignUpScreenPreview() {
    AchuTheme {
        SignUpScreen()
    }
}