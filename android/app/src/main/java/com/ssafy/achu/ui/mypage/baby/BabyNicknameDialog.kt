package com.ssafy.achu.ui.mypage.baby

import android.R.attr.onClick
import android.R.attr.radius
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.ui.mypage.userinfo.NicknameUpdateDialog
import com.ssafy.achu.ui.mypage.userinfo.UserInfoViewModel

@Composable
fun BabyNicknameDialog(
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    color: Color,
    viewModel: BabyViewModel = viewModel(),
    type: String = ""
) {
    val uiState by viewModel.babyUiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경 어두운 오버레이 추가
            .padding(32.dp)
            .clickable(
                indication = null, // 리플 효과 제거
                interactionSource = remember { MutableInteractionSource() }
            ) { }, // 다이얼로그 주변 여백
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
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = if (uiState.selectedBaby == null) "새로운 닉네임을 입력하세요." else "닉네임을 입력하세요",
                    style = AchuTheme.typography.medium18,
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "*영문,한글 2-6자",
                    style = AchuTheme.typography.regular16.copy(color = color)
                )

                Spacer(modifier = Modifier.height(16.dp))

                OutlinedTextField(
                    value = uiState.babyNickname,
                    onValueChange = { viewModel.updateBabyNickname(it) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .height(50.dp),
                    placeholder = {
                        Text(
                            text = if (!uiState.isCorrectNickname) "닉네임 양식확인" else if (uiState.selectedBaby == null) "닉네임 입력" else "새 닉네임 입력",
                            style = AchuTheme.typography.regular16.copy(color = if (!uiState.isCorrectNickname) FontPink else color)
                        )
                    },
                    textStyle = AchuTheme.typography.regular16,
                    singleLine = true,
                    shape = RoundedCornerShape(radius.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = color,
                        unfocusedBorderColor = color,
                        cursorColor = Color.Black
                    ),
                    keyboardOptions = KeyboardOptions.Default,

                    )



                Spacer(modifier = Modifier.height(24.dp))


                // 버튼들
                Row(
                    modifier = Modifier.width(220.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 취소 버튼 (Box로 구현)
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(Color.White, shape = RoundedCornerShape(8.dp))
                            .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                            .clickable(onClick = {onDismiss()
                               viewModel.updateCorrectNickname(true)
                                viewModel.updateBabyNickname("")
                            })
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "취소",
                            style = AchuTheme.typography.semiBold16,
                            color = Color.Gray
                        )
                    }

                    // 확인 버튼 (Box로 구현)
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(color, shape = RoundedCornerShape(8.dp))
                            .clickable(
                                onClick = {
                                    viewModel.confirmNickname()
                                    if (uiState.isCorrectNickname){
                                    onConfirm()
                                    }
                                }
                            )
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
fun NicknameDialogPreview() {
    AchuTheme {
        BabyNicknameDialog(
            onDismiss = { /* 취소 클릭 시 동작 */ },
            onConfirm = { /* 확인 클릭 시 동작 */ },
            color = PointPink,

            )
    }
}
