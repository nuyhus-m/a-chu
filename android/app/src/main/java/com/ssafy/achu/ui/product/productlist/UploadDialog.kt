package com.ssafy.achu.ui.product.productlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontPink

@Composable
fun UploadDialog(
    productName: String,
    babyName: String,
    onUpload: () -> Unit,
    onUploadWithMemory: () -> Unit,
    onBackgroundClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(alpha = 0.5f)) // 배경 어두운 오버레이 추가
            .padding(32.dp)
            .clickable(
                indication = null, // 리플 효과 제거
                interactionSource = remember { MutableInteractionSource() }
            ) {onBackgroundClick() }, // 다이얼로그 주변 여백
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .wrapContentHeight()
                .background(Color.White, shape = RoundedCornerShape(30.dp))
                .padding(24.dp) // 다이얼로그 내부 여백
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally, // 수평 중앙 정렬
                verticalArrangement = Arrangement.Center // 수직 중앙 정렬
            ) {
                Image(
                    painter = painterResource(R.drawable.img_baby_feet),
                    contentDescription = null,
                    modifier = Modifier
                        .width(80.dp)
                        .height(80.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = productName,
                        style = AchuTheme.typography.semiBold20.copy(lineHeight = 30.sp),
                        color = FontPink,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        softWrap = false,
                        modifier = Modifier.weight(1f, fill = false)// 🔹 가변 너비로 설정
                    )

                    Text(
                        text = "과 함께한",
                        style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp),
                        maxLines = 1,
                        softWrap = false,
                        overflow = TextOverflow.Clip, // 이건 생략해도 기본값이라 괜찮음
                    )
                }

                // 텍스트
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(30.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = babyName,
                        style = AchuTheme.typography.semiBold20.copy(lineHeight = 30.sp), // 🔹 줄 간격 조정
                        color = FontPink
                    )

                    Text(
                        text = "의",
                        style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp), // 🔹 줄 간격 조정
                    )
                }
                Text(
                    text = "추억을 등록할까요?",
                    style = AchuTheme.typography.medium18.copy(lineHeight = 30.sp), // 🔹 줄 간격 조정
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
                            .clickable(onClick = onUpload)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "물건만 업로드",
                            style = AchuTheme.typography.semiBold16,
                            color = Color.Gray
                        )
                    }

                    // 확인 버튼 (Box로 구현)
                    Box(
                        modifier = Modifier
                            .weight(1.0f)
                            .background(FontPink, shape = RoundedCornerShape(8.dp))
                            .clickable(onClick = onUploadWithMemory)
                            .padding(vertical = 12.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "추억 등록하기",
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
fun PreviewUploadDialog() {
    AchuTheme {
        UploadDialog(
            productName = "여아 원피스djfj",
            babyName = "두식이",
            onUpload = {},
            onUploadWithMemory = {}
            , onBackgroundClick = {}
        )
    }
}