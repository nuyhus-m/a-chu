package com.ssafy.achu.ui.memory

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MemoryDetailScreen() {
    val pagerState = rememberPagerState()
    val images = listOf(
        R.drawable.img_test_baby_doll,
        R.drawable.img_test_baby_summer,
        R.drawable.img_test_sopung
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 40.dp, start = 24.dp, end = 24.dp, bottom = 8.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back_),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(30.dp)
                        .alignByBaseline(),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontBlack)
                )

                Spacer(modifier = Modifier.weight(1.0F))
                Image(
                    painter = painterResource(id = R.drawable.ic_edit_square),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(32.dp)
                        .alignByBaseline(),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(PointBlue)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(32.dp)
                        .alignByBaseline(),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(PointBlue)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 이미지 슬라이드
            HorizontalPager(
                count = images.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) { page ->
                Image(
                    painter = painterResource(id = images[page]),
                    contentDescription = "Memory Image",
                    modifier = Modifier.fillMaxSize(),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.Crop
                )
            }

            PageIndicator(
                totalPages = images.size,
                currentPage = pagerState.currentPage
            )




            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "첫 돌 기념 두식이!",
                style = AchuTheme.typography.semiBold20
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "2025.03.06",
                style = AchuTheme.typography.semiBold14PointBlue,
                color = FontGray

            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "정말정말 귀여워던 두식이! \n100일동안 건강하게 자라줘서 너무너무 고마워\n앞으로도 씩씩하게 지금처럼만 행복하자",
                style = AchuTheme.typography.regular18,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center

            )


        }
    }
}

@Composable
fun PageIndicator(totalPages: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp) // 여백 조정
    ) {
        // 각 점을 인디케이터로 표현
        repeat(totalPages) { index ->
            Spacer(modifier = Modifier.width(4.dp))
            // 선택된 페이지일 때와 아닐 때 점 색을 다르게 설정
            val color = if (index == currentPage) PointBlue else LightGray
            Box(
                modifier = Modifier
                    .size(10.dp) // 점 크기 증가
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}

@Preview
@Composable
fun MemoryDetailScreenPreview() {
    AchuTheme { MemoryDetailScreen() }
}
