package com.ssafy.achu.core.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontPink

@Composable
fun BasicDialog(text: String, onDismiss: () -> Unit, onConfirm: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경 어두운 오버레이 추가
            .padding(32.dp), // 다이얼로그 주변 여백
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .background(Color.White, shape = RoundedCornerShape(16.dp))
                .padding(24.dp) // 다이얼로그 내부 여백
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {
                // 텍스트
                Text(
                    text = text,
                    style = AchuTheme.typography.semiBold18,
                    modifier = Modifier.padding(bottom = 24.dp), // 텍스트와 버튼 간격
                    color = Color.Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center // 텍스트를 중앙 정렬
                )

                // 버튼들
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 취소 버튼 (Box로 구현)
                    Box(
                        modifier = Modifier
                            .weight(1f)
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

                    // 확인 버튼 (Box로 구현)
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .background(FontPink, shape = RoundedCornerShape(8.dp))
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
            }
        }
    }
}


@Preview
@Composable
fun pre() {
    AchuTheme {
        BasicDialog(
            text = "정말 삭제하시겠습니까?",
            onDismiss = { /* 취소 클릭 시 동작 */ },
            onConfirm = { /* 확인 클릭 시 동작 */ }
        )
    }
}
