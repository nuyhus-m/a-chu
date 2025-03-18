package com.ssafy.achu.ui.mypage

import LargeLikeItem
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White


@Composable
fun LikeItemListScreen() {

    var likeItemList = remember {
        mutableListOf(
            LikeItem(R.drawable.img_miffy_doll, true, "판매중","토끼 인형", "3,000원"),
            LikeItem(R.drawable.img_miffy_doll, true, "거래완료","곰인형", "2,500원"),
            LikeItem(R.drawable.img_miffy_doll, true, "판매중","곰인형", "2,500원"),
            LikeItem(R.drawable.img_miffy_doll, true, "판매중","곰인형", "2,500원"),
            LikeItem(R.drawable.img_miffy_doll, true, "거래완료","곰인형", "2,500원")
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp)
        ) {
            Box() {
                BasicTopAppBar(
                    title = "찜한 상품",
                    onBackClick = {
                    }
                )
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                itemsIndexed(likeItemList.chunked(2)) { _, rowItems ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 2.dp), // 좌우 여백 추가
                        horizontalArrangement = Arrangement.SpaceBetween // 아이템 간 간격 추가
                    ) {
                        rowItems.forEach { item ->
                            LargeLikeItem(
                                img = item.img?.let { painterResource(id = it) }, // 🔹 Int? -> Painter? 변환
                                isLiked = item.like,
                                state = item.sate,
                                productName = item.productName,
                                price = item.price,
                                onClickItem = {}, // 클릭 이벤트
                                onClickHeart = {

                                },
                            )
                        }

                        // 홀수 개일 경우 빈 공간 추가하여 정렬 유지
                        if (rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp)) // 줄 간 간격 추가
                }
            }


        }

    }

}

data class LikeItem(
    val img: Int?, // 이미지 리소스 ID
    val like: Boolean,
    val sate: String,// 제품 상태 (판매중/구매중 등)
    val productName: String, // 제품명
    val price: String // 가격
)

@Preview
@Composable
fun LikeItemListScreenPreview() {
    AchuTheme {
        LikeItemListScreen()
    }
}