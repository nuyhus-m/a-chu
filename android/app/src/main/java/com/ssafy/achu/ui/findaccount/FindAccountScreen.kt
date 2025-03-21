package com.ssafy.achu.ui.findaccount

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
import com.ssafy.achu.core.components.TextFieldWithLabel
import com.ssafy.achu.core.components.TextFieldWithLabelAndBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FindAccountScreen(modifier: Modifier = Modifier) {

    val space = 16.dp

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
            .padding(horizontal = 24.dp)
    ) {

        Text(
            text = stringResource(R.string.find_account),
            style = AchuTheme.typography.bold24,
            modifier = Modifier.padding(vertical = 48.dp)
        )

        // 전화번호
        TextFieldWithLabelAndBtn(
            value = "",
            onValueChange = { },
            label = stringResource(R.string.phone_number),
            placeholder = stringResource(R.string.phone_number_ex),
            buttonText = stringResource(R.string.auth),
            onClick = {}
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 아이디
        TextFieldWithLabel(
            value = "",
            onValueChange = { },
            label = stringResource(R.string.id)
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호
        PwdTextFieldWithLabel(
            value = "",
            onValueChange = { },
            label = stringResource(R.string.reenter_password)
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호 확인
        PwdTextFieldWithLabel(
            value = "",
            onValueChange = { },
            label = stringResource(R.string.password_check)
        )

        Spacer(modifier = Modifier.height(96.dp))

        // 회원가입 버튼
        PointBlueButton(
            buttonText = stringResource(R.string.complete),
            onClick = {}
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun FindAccountScreenPreview() {
    AchuTheme {
        FindAccountScreen()
    }
}