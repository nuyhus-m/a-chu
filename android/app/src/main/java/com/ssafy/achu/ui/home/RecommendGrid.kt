package com.ssafy.achu.ui.home

import android.R.attr.onClick
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.Product

@Composable
fun RecommendGrid(products: List<Product>, onClick: (productId: Int) -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(278.dp) // Row의 전체 높이를 설정
    ) {
        // 첫 번째 이미지가 포함된 Box
        Box(
            modifier = Modifier
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                )
                .shadow(4.dp, RoundedCornerShape(8.dp))
                .fillMaxHeight() // Row의 높이에 맞게 이미지 크기 설정
                .weight(1.0f) // 가로 비율을 1로 설정하여 나머지 영역 차지
                .clickable {
                    onClick(products[0].id)
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.img_miffy_doll),
                contentDescription = "$products[0].title",
                modifier = Modifier
                    .fillMaxSize() // 박스를 꽉 채우도록 설정
                    .align(Alignment.Center) // 이미지를 중앙에 배치
                    .clip(RoundedCornerShape(8.dp)) // 이미지를 라운드 처리

                ,
                contentScale = ContentScale.Crop // 이미지를 박스를 꽉 채우도록 조정
            )

            Column(
                modifier = Modifier
                    .padding(8.dp)
                    .wrapContentHeight()
                    .align(Alignment.TopStart)
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Row(

                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        text = "${products[0].title}",
                        style = AchuTheme.typography.regular18.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = White,
                    )
                    Spacer(Modifier.weight(1.0f))

                    Image(
                        painter = painterResource(id = R.drawable.ic_favorite_line),
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .size(24.dp)
                            .shadow(2.dp)
                            .clickable {
                                onClick(products[0].id)
                            },
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(LightGray)
                    )

                }
                Text(
                    text = "${products[0].price}원",
                    style = AchuTheme.typography.semiBold16.copy(
                        shadow = Shadow(
                            color = Color.Black.copy(alpha = 0.5f),
                            offset = Offset(2f, 2f),
                            blurRadius = 4f
                        )
                    ),
                    color = FontPink,
                )
            }
        }





        Spacer(modifier = Modifier.width(8.dp)) // 이미지들 사이에 여백 추가

        // 두 번째 이미지와 세 번째 이미지가 포함된 Column
        Column(modifier = Modifier.weight(0.65f)) {
            // 두 번째 이미지가 포함된 Box
            Box(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth()
                    .height(150.dp)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .clickable {
                        onClick(products[1].id)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_miffy_doll),
                    contentDescription = "${products[1].title}",
                    modifier = Modifier
                        .fillMaxSize() // 박스를 꽉 채우도록 설정
                        .clip(RoundedCornerShape(8.dp)) // 이미지를 라운드 처리
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )

                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Row(

                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${products[1].title}",
                            style = AchuTheme.typography.regular16.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = White,
                        )
                        Spacer(Modifier.weight(1.0f))

                        Image(
                            painter = painterResource(id = R.drawable.ic_favorite_line),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(20.dp)
                                .shadow(2.dp)
                                .clickable {
                                    onClick(products[1].id)
                                },
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(LightGray)
                        )

                    }
                    Text(
                        text = "${products[1].price}원",
                        style = AchuTheme.typography.semiBold14PointBlue.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = FontPink,
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp)) // 이미지들 사이에 여백 추가

            // 세 번째 이미지가 포함된 Box
            Box(
                modifier = Modifier
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(8.dp)
                    )
                    .fillMaxWidth()
                    .height(120.dp)
                    .shadow(4.dp, RoundedCornerShape(8.dp))
                    .clickable {
                        onClick(products[2].id)
                    }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_miffy_doll),
                    contentDescription = "recommend3",
                    modifier = Modifier
                        .fillMaxSize() // 박스를 꽉 채우도록 설정
                        .clip(RoundedCornerShape(8.dp)) // 이미지를 라운드 처리
                        .align(Alignment.Center),
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .wrapContentHeight()
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .padding(12.dp),
                ) {
                    Row(

                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = "${products[2].title}",
                            style = AchuTheme.typography.regular16.copy(
                                shadow = Shadow(
                                    color = Color.Black.copy(alpha = 0.5f),
                                    offset = Offset(2f, 2f),
                                    blurRadius = 4f
                                )
                            ),
                            color = White,
                        )
                        Spacer(Modifier.weight(1.0f))

                        Image(
                            painter = painterResource(id = R.drawable.ic_favorite_line),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(20.dp)
                                .shadow(2.dp)
                                .clickable {
                                    onClick(products[2].id)
                                },
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(LightGray)
                        )

                    }
                    Text(
                        text = "${products[2].price}원",
                        style = AchuTheme.typography.semiBold14PointBlue.copy(
                            shadow = Shadow(
                                color = Color.Black.copy(alpha = 0.5f),
                                offset = Offset(2f, 2f),
                                blurRadius = 4f
                            )
                        ),
                        color = FontPink,
                    )
                }

            }
        }
    }
}

@Preview
@Composable
fun preview() {
    AchuTheme {
        RecommendGrid(
            products =
                mutableListOf(
                    Product(
                        chatCount = 11,
                        createdAt = "3일 전",
                        id = 1,
                        imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
                        likedByUser = true,
                        likedUsersCount = 18,
                        price = 5000,
                        title = "미피 인형"
                    ),
                    Product(

                        chatCount = 11,
                        createdAt = "3일 전",
                        id = 1,
                        imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
                        likedByUser = true,
                        likedUsersCount = 18,
                        price = 5000,
                        title = "미피 인형"
                    ),
                    Product(

                        chatCount = 11,
                        createdAt = "3일 전",
                        id = 1,
                        imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
                        likedByUser = true,
                        likedUsersCount = 18,
                        price = 5000,
                        title = "미피 인형"
                    )

                )
        )
    }

}