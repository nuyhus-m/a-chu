package com.ssafy.achu.ui.auth.signin

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import android.widget.Toast
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.achu.ui.MainActivity
import com.ssafy.achu.R
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.textfield.BasicTextField
import com.ssafy.achu.core.components.textfield.PasswordTextField
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "SignInScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignInScreen(
    modifier: Modifier = Modifier,
    viewModel: SignInViewModel = viewModel(),
    onNavigateToSignUp: () -> Unit
) {

    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()

    // 로그인 실패 시 Toast 메시지 띄우기
    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    // 로그인 성공 시 MainActivity로 이동
    LaunchedEffect(uiState.signInSuccess) {
        if (uiState.signInSuccess) {
            val options = ActivityOptions.makeCustomAnimation(context, 0, 0)
            Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }.also { intent ->
                context.startActivity(intent, options.toBundle())
            }
        }
    }

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

                Spacer(modifier = Modifier.height(36.dp))

                PointBlueButton(
                    buttonText = stringResource(R.string.login),
                    enabled = uiState.buttonState,
                    onClick = { viewModel.signIn() }
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
        SignInScreen(
            onNavigateToSignUp = {}
        )
    }
}