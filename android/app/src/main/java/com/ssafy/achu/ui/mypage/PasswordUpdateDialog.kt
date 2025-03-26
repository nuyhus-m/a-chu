package com.ssafy.achu.ui.mypage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.components.textfield.PasswordTextField
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.PointBlue

@Composable
fun PasswordUpdateDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경 어두운 오버레이 추가
            .padding(32.dp), // 다이얼로그 주변 여백
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(30.dp))
                .padding(24.dp) // 다이얼로그 내부 여백
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "기존 비밀번호 입력",
                    style = AchuTheme.typography.medium18,
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = "",
                    onValueChange = {},
                    placeholder ="●●●●●"
                )


                Spacer(modifier = Modifier.height(16.dp))


                Text(
                    text = "새 비밀번호 입력",
                    style = AchuTheme.typography.medium18,
                )
                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "*영문,숫자,특수문자 포함 8~20자리",
                    style = AchuTheme.typography.semiBold14PointBlue,
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = "",
                    onValueChange = {},
                    placeholder ="●●●●●"
                )


                Spacer(modifier = Modifier.height(24.dp))



                Text(
                    text = "새 비밀번호 확인",
                    style = AchuTheme.typography.medium18,
                )

                Spacer(modifier = Modifier.height(8.dp))

                PasswordTextField(
                    value = "",
                    onValueChange = {},
                    placeholder ="●●●●●"
                )


                Spacer(modifier = Modifier.height(24.dp))





                // 버튼들
                Row(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 취소 버튼 (Box로 구현)
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .clickable(onClick = onDismiss)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "취소",
                            style = AchuTheme.typography.semiBold16,
                            color = Color.Gray
                        )
                    }

                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(PointBlue, shape = RoundedCornerShape(8.dp))
                            .clickable(onClick = onConfirm)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "확인",
                            style = AchuTheme.typography.semiBold16White
                        )
                    }

                }

                Spacer(modifier = Modifier.height(8.dp))

            }
        }
    }
}

@Preview
@Composable
fun PasswordUpdateDialog() {
    AchuTheme {
        PasswordUpdateDialog(
            onDismiss = { /* 취소 클릭 시 동작 */ },
            onConfirm = { /* 확인 클릭 시 동작 */ }
        )

    }
}


