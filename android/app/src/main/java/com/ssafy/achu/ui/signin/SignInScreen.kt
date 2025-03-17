package com.ssafy.achu.ui.signin

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTextField
import com.ssafy.achu.core.components.PasswordTextField
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun SignInScreen(modifier: Modifier = Modifier) {

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        // 배경 이미지 추가
        Image(
            painter = painterResource(id = R.drawable.img_sign_in),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 80.dp),
            contentScale = ContentScale.FillBounds  // 화면에 꽉 차도록 설정
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 270.dp, start = 24.dp, end = 24.dp)
        ) {
            Text(
                text = "환영합니다!",
                style = AchuTheme.typography.bold24
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row {
                Text(
                    text = "회원이 아니신가요?",
                    style = AchuTheme.typography.regular18
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "회원가입하기",
                    style = AchuTheme.typography.semiBold18.copy(color = PointBlue)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "아이디",
                style = AchuTheme.typography.regular18
            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = "",
                onValueChange = {},
                placeholder = "아이디를 입력하세요.",
                placeholderColor = PointBlue,
                borderColor = PointBlue
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "비밀번호",
                style = AchuTheme.typography.regular18
            )

            Spacer(modifier = Modifier.height(8.dp))

            PasswordTextField(
                value = "",
                onValueChange = {},
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "아이디, 비밀번호를 잊으셨나요?",
                style = AchuTheme.typography.semiBold16.copy(color = PointBlue),
                modifier = Modifier.align(Alignment.End)
            )

            Spacer(modifier = Modifier.height(32.dp))

            PointBlueButton(
                buttonText = "로그인",
                onClick = { /* 로그인 로직 구현 */ },
                radius = 30.dp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SignInScreenPreview() {
    AchuTheme {
        SignInScreen()
    }
}