package com.ssafy.achu.ui.mypage.tradelist

import android.net.Uri
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
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatPrice
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TradeListScreen(
    viewModel: TradeListViewModel = viewModel(),
    onNavigateToProductDetail: () -> Unit,
    activityViewModel: ActivityViewModel
) {
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.getPurchaseList()
        viewModel.getSaleList()
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

    val saleList by viewModel.saleList.collectAsState()
    val purchaseList by viewModel.purchaseList.collectAsState()

    var selectedTab by remember { mutableStateOf("purchase") }


    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .navigationBarsPadding()
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Box() {
                BasicTopAppBar(
                    title = "거래목록",
                    onBackClick = {
                        backPressedDispatcher?.onBackPressed()

                    }
                )
            }

            Box(
                modifier = Modifier.padding(horizontal = 24.dp)
            ) {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {


                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (selectedTab == "purchase") PointBlue else White,
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                )
                                .height(50.dp)
                                .weight(1f)
                                .clickable {
                                    selectedTab = "purchase"
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "구매",
                                style = AchuTheme.typography.semiBold20White,
                                color = if (selectedTab == "purchase") White else PointBlue
                            )
                        }

                        Box(
                            modifier = Modifier
                                .background(
                                    color = if (selectedTab == "sale") PointBlue else White,
                                    shape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
                                )
                                .height(50.dp)
                                .weight(1f)
                                .clickable {
                                    selectedTab = "sale"
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "판매",
                                style = AchuTheme.typography.semiBold20White,
                                color = if (selectedTab == "sale") White else PointBlue
                            )
                        }
                    }

                    Box(
                        modifier = Modifier
                            .height(4.dp)
                            .fillMaxWidth()
                            .background(color = PointBlue)
                    )

                    if (selectedTab == "purchase" && purchaseList.isNullOrEmpty()) {


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
                                text = "구매내역이 없습니다.",
                                style = AchuTheme.typography.semiBold18,
                                lineHeight = 30.sp,
                                color = FontGray,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else if (selectedTab != "purchase" && saleList.isNullOrEmpty()) {

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
                                text = "판매내역이 없습니다.",
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
                                .padding(top = 16.dp),
                            state = rememberLazyListState()
                        ) {
                            val listToDisplay =
                                if (selectedTab == "purchase") purchaseList else saleList

                            itemsIndexed(listToDisplay) { index, productItem ->
                                ListItem(
                                    img = productItem.imgUrl.toUri(),
                                    state = if (selectedTab == "purchase") "구매완료" else if (productItem.tradeStatus == "SELLING") "판매중" else "판매완료",
                                    productName = productItem.title,
                                    price = formatPrice(productItem.price),
                                    onClick = {
                                        activityViewModel.getProductDetail(productItem.id)
                                    },

                                    )
                                Spacer(modifier = Modifier.height(8.dp))
                                if (index == listToDisplay.size - 3) {
                                    LaunchedEffect(index) {
                                        if (selectedTab == "purchase") viewModel.loadMorePurchaseItems() else viewModel.loadMoreSaleItems()
                                    }
                                }
                            }

                            // 로딩 중일 때 표시할 로딩 인디케이터
                            item {
                                if (viewModel.isPurchaseLoading.collectAsState().value) {
                                    CircularProgressIndicator(
                                        modifier = Modifier.padding(horizontal = 8.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }


    }
}


@Composable
fun ListItem(
    img: Uri,
    state: String,
    productName: String,
    price: String,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
            .background(color = White, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = img,
                contentDescription = null,
                modifier = Modifier
                    .background(color = LightGray, shape = RoundedCornerShape(8.dp))
                    .width(100.dp)
                    .height(100.dp)
                    .clip(shape = RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )


            Spacer(modifier = Modifier.width(16.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(top = 8.dp, bottom = 8.dp)
            ) {
                Text(
                    text = state,
                    style = AchuTheme.typography.semiBold14PointBlue,
                    color = if (state == "구매완료") FontPink else FontBlue
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = productName,
                    style = AchuTheme.typography.semiBold18,
                    color = FontBlack,
                    maxLines = 1, // 한 줄만 표시
                    overflow = TextOverflow.Ellipsis // 넘치는 부분은 "..."으로 표시
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = price,
                    style = AchuTheme.typography.semiBold16,
                    fontSize = 14.sp,
                    color = FontBlack,
                    maxLines = 1, // 한 줄만 표시
                    overflow = TextOverflow.Ellipsis // 넘치는 부분은 "..."으로 표시
                )
            }
        }
    }
}


@Preview
@Composable
fun TradeListScreenPreview() {
    AchuTheme {
        TradeListScreen(onNavigateToProductDetail = {

        }, activityViewModel = viewModel())
//        ListItem(R.drawable.img_miffy_doll, "판매중", "토끼인형", "판매가: 3,000원", onClick = {})
    }
}
