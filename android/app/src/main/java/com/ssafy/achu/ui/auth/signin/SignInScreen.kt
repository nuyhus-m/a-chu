package com.ssafy.achu.ui.auth.signin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.achu.R
import com.ssafy.achu.core.components.textfield.BasicTextField
import com.ssafy.achu.core.components.textfield.PasswordTextField
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel(),
    onNavigateToSignUp: () -> Unit = {},
    onNavigateToFindAccount: () -> Unit = {}
) {

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        // 배경 이미지 추가
        Image(
            painter = painterResource(id = R.drawable.img_sign_in),
            contentDescription = null,
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentScale = ContentScale.FillBounds
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {

            Spacer(modifier = Modifier.weight(0.3f))

            Column(
                modifier = Modifier.weight(0.7f)
            ) {
                Text(
                    text = stringResource(R.string.welcome),
                    style = AchuTheme.typography.bold24
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    Text(
                        text = stringResource(R.string.question_not_user),
                        style = AchuTheme.typography.regular18
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = stringResource(R.string.go_sign_up),
                        style = AchuTheme.typography.semiBold18.copy(color = PointBlue),
                        modifier = Modifier.clickable(onClick = onNavigateToSignUp)
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = stringResource(R.string.id),
                    style = AchuTheme.typography.regular16
                )

                Spacer(modifier = Modifier.height(8.dp))

                BasicTextField(
                    value = uiState.id,
                    onValueChange = { viewModel.updateId(it) },
                    placeholder = stringResource(R.string.enter_id),
                    placeholderColor = PointBlue,
                    borderColor = PointBlue
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.password),
                    style = AchuTheme.typography.regular16
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = uiState.pwd,
                    onValueChange = { viewModel.updatePwd(it) },
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = stringResource(R.string.question_forget_account),
                    style = AchuTheme.typography.semiBold14PointBlue,
                    modifier = Modifier
                        .align(Alignment.End)
                        .clickable(onClick = onNavigateToFindAccount)
                )

                Spacer(modifier = Modifier.height(36.dp))

                PointBlueButton(
                    buttonText = stringResource(R.string.login),
                    onClick = { /* 로그인 로직 구현 */ }
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    AchuTheme {
        SignInScreen()
    }
}