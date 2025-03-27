package com.ssafy.achu.ui.memory

import android.R.attr.contentDescription
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
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
import com.ssafy.achu.data.model.memory.MemoryResponse

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MemoryDetailScreen(onNavigateToMemoryUpload: () -> Unit) {
    val pagerState = rememberPagerState()
    val images = listOf(
        R.drawable.img_test_baby_doll,
        R.drawable.img_test_baby_summer,
        R.drawable.img_test_sopung
    )

    val memory = MemoryResponse(
        content = "너무너무 귀여운 두식이의 첫돌에 입었던\n옷을 팔았다. 서운하면서도 후련하다\n이걸로 더 예쁜옷을 사줘야지",
        createdAt = "2024.06.03",
        id = 1,
        imgUrls = listOf(
            "https://loremflickr.com/600/400",
            "https://loremflickr.com/600/400",
            "https://loremflickr.com/600/400"
        ),
        title = "두식이의 첫돌",
        updatedAt = ""
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .navigationBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back_),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(30.dp)
                        .alignByBaseline()
                        .clickable{},
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontBlack)
                )

                Spacer(modifier = Modifier.weight(1.0F))
                Image(
                    painter = painterResource(id = R.drawable.ic_edit_square),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(32.dp)
                        .alignByBaseline()
                        .clickable {
                            onNavigateToMemoryUpload()
                        },
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(PointBlue)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(32.dp)
                        .alignByBaseline()
                        .clickable {  },
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
                    .height(350.dp)
            ) { page ->
                AsyncImage(
                    model = memory.imgUrls[page],
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
                text = memory.title,
                style = AchuTheme.typography.semiBold20
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = memory.createdAt,
                style = AchuTheme.typography.semiBold14PointBlue,
                color = FontGray

            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = memory.content,
                style = AchuTheme.typography.regular18,
                modifier = Modifier.align(Alignment.CenterHorizontally).fillMaxWidth().padding(horizontal = 24.dp),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,

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
    AchuTheme { MemoryDetailScreen {} }
}
