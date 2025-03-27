package com.ssafy.achu.ui.auth.signup

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
import com.ssafy.achu.core.components.textfield.TextFieldWithLabelAndBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: SignUpViewModel = viewModel()
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
            text = stringResource(R.string.sign_up),
            style = AchuTheme.typography.bold24,
            modifier = Modifier.padding(top = 68.dp, bottom = 48.dp)
        )

        // 아이디
        TextFieldWithLabelAndBtn(
            value = uiState.id,
            onValueChange = { viewModel.updateId(it) },
            label = stringResource(R.string.id),
            placeholder = stringResource(R.string.enter_id),
            buttonText = stringResource(R.string.check),
            onClick = {},
            errorMessage = uiState.idMessage,
            enabled = uiState.idState,
            buttonEnabled = uiState.idButtonState
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호
        PwdTextFieldWithLabel(
            value = uiState.pwd,
            onValueChange = { viewModel.updatePwd(it) },
            label = stringResource(R.string.password),
            errorMessage = uiState.pwdMessage,
            enabled = uiState.pwdState
        )

        Spacer(modifier = Modifier.height(space))

        // 비밀번호 확인
        PwdTextFieldWithLabel(
            value = uiState.pwdCheck,
            onValueChange = { viewModel.updatePwdCheck(it) },
            label = stringResource(R.string.password_check),
            errorMessage = uiState.pwdCheckMessage,
            enabled = uiState.pwdState
        )

        Spacer(modifier = Modifier.height(space))

        // 닉네임
        TextFieldWithLabelAndBtn(
            value = uiState.nickname,
            onValueChange = { viewModel.updateNickname(it) },
            label = stringResource(R.string.nickname),
            placeholder = stringResource(R.string.nickname_ex),
            buttonText = stringResource(R.string.check),
            onClick = {},
            errorMessage = uiState.nicknameMessage,
            enabled = uiState.nicknameState,
            buttonEnabled = uiState.nicknameButtonState
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 전화번호
        TextFieldWithLabelAndBtn(
            value = uiState.phoneNumber,
            onValueChange = { viewModel.updatePhoneNumber(it) },
            label = stringResource(R.string.phone_number),
            placeholder = stringResource(R.string.phone_number_ex),
            buttonText = stringResource(R.string.auth),
            onClick = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            errorMessage = uiState.phoneNumberMessage,
            enabled = uiState.phoneNumberState,
            buttonEnabled = uiState.phoneNumberButtonState
        )

        Spacer(modifier = Modifier.weight(1f))

        // 회원가입 버튼
        Box(
            modifier = Modifier
                .navigationBarsPadding()
                .padding(bottom = 40.dp)
        ) {
            PointBlueButton(
                buttonText = stringResource(R.string.sign_up),
                onClick = {},
                enabled = uiState.buttonState
            )
        }
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