package com.ssafy.achu.ui.mypage.likelist

import LargeLikeItem
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.navigation.Route
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.White


@Composable
fun LikeItemListScreen(
    viewModel: LikeItemListViewModel = viewModel(),
    onNavigateToProductDetail: () -> Unit
) {


    LaunchedEffect(Unit) {
        viewModel.getLikeItemList()
    }

    val likeItemList by viewModel.likeItemList.collectAsState()
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .navigationBarsPadding()
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box() {
                BasicTopAppBar(
                    title = "찜한 상품",
                    onBackClick = {
                        backPressedDispatcher?.onBackPressed()
                    }
                )
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                if (likeItemList.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 150.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.img_crying_face),
                            contentDescription = "Crying Face",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = "찜한 상품이 없습니다.\n관심있는 상품을 추가해보세요.",
                            style = AchuTheme.typography.semiBold18,
                            lineHeight = 30.sp,
                            color = FontGray,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        itemsIndexed(likeItemList.chunked(2)) { _, rowItems ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 2.dp)
                                    .clickable { }, // 좌우 여백 추가
                                horizontalArrangement = Arrangement.SpaceBetween // 아이템 간 간격 추가
                            ) {
                                rowItems.forEach { item ->
                                    LargeLikeItem(
                                        img = item.imgUrl.toUri(),
                                        state = item.tradeStatus,
                                        productName = item.title,
                                        price = "${item.price}원",
                                        onClickItem = {
                                            onNavigateToProductDetail()
                                        },
                                        productLike = {
                                            viewModel.likeItem(
                                                item.id
                                            )
                                        },
                                        productUnlike = {
                                            viewModel.unlikeItem(
                                                item.id
                                            )
                                        }
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
    }
}


@Preview
@Composable
fun LikeItemListScreenPreview() {
    AchuTheme {
        LikeItemListScreen(onNavigateToProductDetail = {

        })
    }
}