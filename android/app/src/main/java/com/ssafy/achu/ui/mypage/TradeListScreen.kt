package com.ssafy.achu.ui.mypage

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@Composable
fun TradeListScreen() {

    val saleList = remember {
        mutableListOf(
            ProductItem(R.drawable.img_miffy_doll, "판매중", "토끼 인형", "판매가: 3,000원"),
            ProductItem(R.drawable.img_miffy_doll, "판매완료", "곰인형", "판매가: 2,500원")
        )
    }

    val purchaseList = remember {
        mutableListOf(
            ProductItem(R.drawable.img_miffy_doll, "구매완료", "인형", "구매가: 3,000원"),
            ProductItem(R.drawable.img_miffy_doll, "구매완료", "장난감", "구매가: 1,500원")
        )
    }

    var selectedTab by remember { mutableStateOf("purchase") }
//    var showDialog by remember { mutableStateOf(false) }
//    var selectedProduct by remember { mutableStateOf<ProductItem?>(null) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {
            Box() {
                BasicTopAppBar(
                    title = "거래목록",
                    onBackClick = {

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
                                fontSize = 20.sp,
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
                                fontSize = 20.sp,
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

                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 16.dp)
                    ) {
                        val listToDisplay =
                            if (selectedTab == "purchase") purchaseList else saleList

                        items(listToDisplay) { productItem ->
                            ListItem(
                                img = productItem.img,
                                state = productItem.state,
                                productName = productItem.productName,
                                price = productItem.price,
                                onClick = {
                                },
//                            onDeleteClick = {
//                                selectedProduct = productItem
//                                showDialog = true
//                            }
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }


    }

//    if (showDialog && selectedProduct != null) {
//        BasicDialog(
//            img = null,
//            pinkText = null,
//            textLine1 = null,
//            text = "${selectedProduct?.productName}의 판매를\n정말 중지하시겠습니까?",
//            onDismiss = { showDialog = false },
//            onConfirm = {
//                showDialog = false
//                // 판매 중지 로직 추가 가능
//            }
//        )
//    }
}

@Composable
fun ListItem(
    img: Int? = null,
    state: String,
    productName: String,
    price: String,
    onClick: () -> Unit,
//    onDeleteClick: () -> Unit
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
            if (img != null) {
                Image(
                    painter = painterResource(id = img),
                    contentDescription = null,
                    modifier = Modifier
                        .background(color = LightGray, shape = RoundedCornerShape(8.dp))
                        .width(100.dp)
                        .height(100.dp)
                        .clip(shape = RoundedCornerShape(8.dp)),
                    contentScale = ContentScale.Crop
                )
            }

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
                    color = FontBlack
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = price,
                    style = AchuTheme.typography.semiBold16,
                    fontSize = 14.sp,
                    color = FontBlack
                )
            }
        }

//        if (state == "판매중") {
//            Icon(
//                painter = painterResource(id = R.drawable.ic_outline_clear_24),
//                contentDescription = "Arrow",
//                tint = FontGray,
//                modifier = Modifier
//                    .height(40.dp)
//                    .align(Alignment.TopEnd)
//                    .padding(top = 16.dp, end = 16.dp)
//                    .clickable {
////                        onDeleteClick()
//                    }
//            )
//        }
    }

}


data class ProductItem(
    val img: Int?, // 이미지 리소스 ID
    val state: String, // 제품 상태 (판매중/구매중 등)
    val productName: String, // 제품명
    val price: String // 가격
)

@Preview
@Composable
fun TradeListScreenPreview() {
    AchuTheme {
        TradeListScreen()
//        ListItem(R.drawable.img_miffy_doll, "판매중", "토끼인형", "판매가: 3,000원", onClick = {})
    }
}
