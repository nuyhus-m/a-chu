package com.ssafy.achu.core.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.ssafy.achu.R
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontPink

//pinkText여기 넣는 글씨는 핑크색, 큰 크기로 조절됨
//이 텍스트 바로옆에 붙는 글씨는 textLine1(핑크랑 같은 라인)여기에 입력해야함
//그냥 다 검정 글씨면 text이걸로 하면됨 앞에 애들은 안 넣으면 null이 디폴트임!
@Composable
fun BasicDialog(
    img: Painter? = null,
    pinkText: String? = null,
    textLine1: String? =null,
    text: String,
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
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {

                Spacer(modifier = Modifier.height(16.dp))
                img?.let {

                    Image(
                        painter = img,
                        contentDescription = null,
                        modifier = Modifier
                            .width(80.dp)
                            .height(80.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                if (pinkText != null && textLine1 != null) {
                    Row(
                        modifier = Modifier.fillMaxWidth().height(30.dp),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = pinkText,
                            style = AchuTheme.typography.semiBold20.copy(lineHeight = 30.sp), // 🔹 줄 간격 조정
                            color = FontPink
                        )

                        Text(
                            text = textLine1,
                            style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp), // 🔹 줄 간격 조정
                        )
                        Spacer(modifier = Modifier.height(12.dp))

                    }


                }

                // 텍스트
                Text(
                    text = text,
                    style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp), // 🔹 줄 간격 조정
                    modifier = Modifier.padding(bottom = 24.dp), // 텍스트와 버튼 간격
                    color = Color.Black,
                    textAlign = androidx.compose.ui.text.style.TextAlign.Center // 텍스트를 중앙 정렬
                )

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
                            .weight(1.0f)
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

                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }
}

@Preview
@Composable
fun pre() {
    AchuTheme {
        BasicDialog(
            img = painterResource(id = R.drawable.crying_face),
            "A - Chu",
            "와 함께한",
            text = "모든 추억이 삭제됩니다.\n정말 탈퇴하시겠습니까??",
            onDismiss = { /* 취소 클릭 시 동작 */ },
            onConfirm = { /* 확인 클릭 시 동작 */ }
        )
    }
}
