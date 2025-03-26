package com.ssafy.achu.ui.auth.findaccount

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.achu.R
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.textfield.PwdTextFieldWithLabel
import com.ssafy.achu.core.components.textfield.TextFieldWithLabel
import com.ssafy.achu.core.components.textfield.TextFieldWithLabelAndBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FindAccountScreen(
    modifier: Modifier = Modifier,
    viewModel: FindAccountViewModel = viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()
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
            modifier = Modifier.padding(top = 68.dp, bottom = 48.dp)
        )

        // 전화번호
        TextFieldWithLabelAndBtn(
            value = uiState.phoneNumber,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = stringResource(R.string.phone_number),
            placeholder = stringResource(R.string.phone_number_ex),
            buttonText = stringResource(R.string.auth),
            onClick = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 아이디
        TextFieldWithLabel(
            value = uiState.id,
            onValueChange = { viewModel.updateId(it) },
            label = stringResource(R.string.id)
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호 재설정
        PwdTextFieldWithLabel(
            value = uiState.pwd,
            onValueChange = { viewModel.updatePwd(it) },
            label = stringResource(R.string.reenter_password)
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호 확인
        PwdTextFieldWithLabel(
            value = uiState.pwdCheck,
            onValueChange = { viewModel.updatePwdCheck(it) },
            label = stringResource(R.string.password_check)
        )

        Spacer(modifier = Modifier.height(96.dp))

        Spacer(modifier = Modifier.weight(1f))

        // 완료 버튼
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 40.dp)
        ) {
            PointBlueButton(
                buttonText = stringResource(R.string.complete),
                onClick = {}
            )
        }
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