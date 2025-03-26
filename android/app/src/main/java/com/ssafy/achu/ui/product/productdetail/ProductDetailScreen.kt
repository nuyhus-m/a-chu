package com.ssafy.achu.ui.product

import BasicLikeItem
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.Divider
import com.ssafy.achu.core.components.TopBarWithMenu
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.product.ProductResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailScreen() {

    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        // 탑바
        TopBarWithMenu(
            title = stringResource(R.string.product_detail),
            onBackClick = {},
            menuFirstText = stringResource(R.string.modify),
            menuSecondText = stringResource(R.string.delete),
            onMenuFirstItemClick = {},
            onMenuSecondItemClick = {}
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // 이미지 페이저
            ImagePager()

            // 프로필
            ProfileInfo()

            // 물품 정보
            ProductInfo()

            // 추천 리스트
            RecommendList()
        }

        BottomBar()
    }
}

@Composable
private fun BottomBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_favorite_line),
            contentDescription = stringResource(R.string.like),
            tint = FontGray,
            modifier = Modifier.size(32.dp)
        )
        Text(
            text = "11",
            style = AchuTheme.typography.regular18.copy(color = FontGray),
            modifier = Modifier.padding(start = 8.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(48.dp)
                .background(LightGray)
        )
        Text(
            text = "10,000원",
            style = AchuTheme.typography.semiBold20.copy(color = FontPink),
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = {},
            modifier = Modifier
                .height(50.dp)
        ) {
            Text(
                text = stringResource(R.string.go_chat),
                style = AchuTheme.typography.semiBold20White
            )
        }
    }
}

@Composable
private fun RecommendList() {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.recommend_title),
            style = AchuTheme.typography.semiBold18
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = stringResource(R.string.more)
        )
    }
    val productResponses = listOf(
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = false,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
    )
    Row(
        modifier = Modifier.padding(start = 24.dp)
    ) {
        RecommendList(
            items = productResponses
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
private fun ProductInfo() {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .defaultMinSize(minHeight = 220.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Text(
            text = "미피 인형",
            style = AchuTheme.typography.bold24
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = "장난감",
                style = AchuTheme.typography.regular14.copy(
                    color = FontGray,
                    textDecoration = TextDecoration.Underline
                )
            )
            Text(
                text = stringResource(R.string.symbol),
                style = AchuTheme.typography.regular14.copy(color = FontGray)
            )
            Text(
                text = "2025.03.07",
                style = AchuTheme.typography.regular14.copy(color = FontGray)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = "8/31일에 사고 2번 시착한 제품입니다.",
            style = AchuTheme.typography.regular18
        )
    }
    Divider()
}

@Composable
private fun ProfileInfo() {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp, start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_profile_test),
            contentDescription = stringResource(R.string.profile),
            modifier = Modifier
                .fillMaxWidth(0.16f)
                .aspectRatio(1f)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = "재영맘",
                style = AchuTheme.typography.semiBold20
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = stringResource(R.string.sale),
                style = AchuTheme.typography.semiBold14PointBlue
            )
        }
    }
    Divider()
}

@Composable
fun RecommendList(items: List<ProductResponse>) {
    LazyRow {
        items(items) { item ->
            BasicLikeItem(
                isLiked = item.likedByUser,
                onClickItem = {},
                onClickHeart = {},
                productName = item.title,
                state = "판매중",
                price = "${item.price}원",
                img = ColorPainter(LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun ImagePager() {
    // 이미지 리소스 리스트
    val images = listOf(
        R.drawable.img_miffy_doll,
        R.drawable.img_test_sopung,
        R.drawable.img_test_baby_summer,
        // 필요한 만큼 이미지 추가
    )

    // 페이저 상태
    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        // 이미지 페이저
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = images[page]),
                contentDescription = "이미지 ${page + 1}",
                modifier = Modifier
                    .fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 페이지 인디케이터
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                // 현재 페이지에 따라 점 색상 변경
                val color = if (pagerState.currentPage == index) Color.White else Color.Gray

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProductDetailScreen() {
    AchuTheme {
        ProductDetailScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBarWithMenu() {
    AchuTheme {
        TopBarWithMenu(
            title = "거래상세",
            onBackClick = {},
            menuFirstText = "수정",
            menuSecondText = "삭제",
            onMenuFirstItemClick = {},
            onMenuSecondItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    AchuTheme {
        BottomBar()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewImagePager() {
    ImagePager()
}
