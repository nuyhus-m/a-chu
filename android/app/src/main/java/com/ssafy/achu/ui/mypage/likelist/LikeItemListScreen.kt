package com.ssafy.achu.ui.mypage.likelist

import LargeLikeItem
import android.util.Log
import android.widget.Toast
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "LikeItemListScreen_안주현"
@Composable
fun LikeItemListScreen(
    viewModel: LikeItemListViewModel = viewModel(),
    onNavigateToProductDetail: () -> Unit,
    activityViewModel: ActivityViewModel
) {

    val uiState by activityViewModel.uiState.collectAsState()

    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getLikeItemList()
    }

    LaunchedEffect(Unit) {
        activityViewModel.errorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        activityViewModel.getProductSuccess.collectLatest {
            if (it) {
                onNavigateToProductDetail()
            }
        }
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
                                .height(60.dp)
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
                    val listState = rememberLazyListState()

                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        state = listState
                    ) {
                        itemsIndexed(likeItemList.chunked(2)) { _, rowItems ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 2.dp)
                                    .clickable { },
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                rowItems.forEach { item ->
                                    LargeLikeItem(
                                        img = item.imgUrl.toUri(),
                                        state = item.tradeStatus,
                                        productName = item.title,
                                        price = item.price,
                                        onClickItem = { activityViewModel.getProductDetail(item.id) },
                                        productLike = { viewModel.likeItem(item.id,uiState.selectedBaby!!.id) },
                                        productUnlike = { viewModel.unlikeItem(item.id) }
                                    )
                                }
                                if (rowItems.size == 1) {
                                    Spacer(modifier = Modifier.weight(1f))
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                        }

                        // 로딩 중일 때 표시할 로딩 인디케이터
                        item {
                            if (viewModel.isLoading.collectAsState().value) {
                                CircularProgressIndicator(modifier = Modifier.padding(horizontal = 8.dp))
                            }
                        }
                    }

                    LaunchedEffect(listState) {
                        snapshotFlow {
                            listState.layoutInfo.visibleItemsInfo
                        }.collect { visibleItemsInfo ->
                            Log.d(TAG, "Visible Items Info: $visibleItemsInfo")

                            val lastVisibleItem = visibleItemsInfo.lastOrNull()
                            val lastVisibleIndex = lastVisibleItem?.index

                            Log.d(TAG, "Last Visible Index: $lastVisibleIndex")

                            // 마지막에서 두 번째 아이템에 도달했을 때 추가 데이터를 로드
                            if (lastVisibleIndex != null && lastVisibleIndex*2 >= likeItemList.size - 2) {
                                Log.d(TAG, "Loading more items. Last visible index: $lastVisibleIndex")
                                viewModel.loadMoreItems()
                            }
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

        }, activityViewModel = viewModel())
    }
}