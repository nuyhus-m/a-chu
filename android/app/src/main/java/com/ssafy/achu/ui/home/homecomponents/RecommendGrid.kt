package com.ssafy.achu.ui.home.homecomponents

import android.R.attr.contentDescription
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter.State.Empty.painter
import com.ssafy.achu.R
import com.ssafy.achu.core.LoadingImg
import com.ssafy.achu.core.LoadingImgScreen
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightBlue
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatPrice
import com.ssafy.achu.data.model.product.ProductResponse

@Composable
fun RecommendGrid(
    productResponses: List<ProductResponse>,
    onClick: (productId: Int) -> Unit = {},
    onHeartClick: (productId: Int) -> Unit = {}
) {

    val heartClick = remember { mutableStateOf(false) }
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
                    onClick(productResponses[0].id)
                }
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize().background(LightBlue) // 박스를 꽉 채우도록 설정
                    .align(Alignment.Center) // 이미지를 중앙에 배치
                    .clip(RoundedCornerShape(8.dp))
            )

            LoadingImg("이미지 로딩중..", Modifier.fillMaxWidth())
            AsyncImage(
                model = productResponses[0].imgUrl,
                contentDescription = "$productResponses[0].title",
                modifier = Modifier
                    .fillMaxSize() // 박스를 꽉 채우도록 설정
                    .align(Alignment.Center) // 이미지를 중앙에 배치
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        onClick(productResponses[0].id)
                    },
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
                        text = productResponses[0].title,
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
                        painter = painterResource(id = if (!productResponses[0].likedByUser) R.drawable.ic_favorite_line else R.drawable.ic_favorite),
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                onHeartClick(productResponses[0].id)
                                productResponses[0].likedByUser = !productResponses[0].likedByUser
                            },
                        colorFilter = ColorFilter.tint(if(!productResponses[0].likedByUser)LightGray else FontPink)
                    )

                }
                Text(
                    text = formatPrice(productResponses[0].price),
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
                        onClick(productResponses[1].id)
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize().background(LightBlue) // 박스를 꽉 채우도록 설정
                        .align(Alignment.Center) // 이미지를 중앙에 배치
                        .clip(RoundedCornerShape(8.dp))
                )

                LoadingImg("이미지 로딩중..", Modifier.fillMaxWidth().padding(top = 24.dp), 12, 50)
                AsyncImage(
                    model = productResponses[1].imgUrl,
                    contentDescription = productResponses[1].title,
                    modifier = Modifier
                        .fillMaxSize() // 박스를 꽉 채우도록 설정
                        .clip(RoundedCornerShape(8.dp)) // 이미지를 라운드 처리
                        .align(Alignment.Center)
                        .clickable {
                            onClick(productResponses[1].id)
                        },
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
                            text = productResponses[1].title,
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
                            painter = painterResource(id = if (!productResponses[1].likedByUser) R.drawable.ic_favorite_line else R.drawable.ic_favorite),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    onHeartClick(productResponses[1].id)
                                },
                            colorFilter = ColorFilter.tint(if(!productResponses[1].likedByUser)LightGray else FontPink)
                        )

                    }
                    Text(
                        text = formatPrice(productResponses[1].price),
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
                        onClick(productResponses[2].id)
                    }
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize().background(LightBlue) // 박스를 꽉 채우도록 설정
                        .align(Alignment.Center) // 이미지를 중앙에 배치
                        .clip(RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ){

                    LoadingImg("이미지 로딩중..", Modifier.fillMaxWidth().padding(top = 16.dp), 12, 40)
                }
                AsyncImage(
                    model = productResponses[2].imgUrl,
                    contentDescription = "recommend3",
                    modifier = Modifier
                        .fillMaxSize() // 박스를 꽉 채우도록 설정
                        .clip(RoundedCornerShape(8.dp)) // 이미지를 라운드 처리
                        .align(Alignment.Center)
                        .clickable {
                            onClick(productResponses[1].id)
                        },
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
                            text = "${productResponses[2].title}",
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
                            painter = painterResource(id = if (!productResponses[2].likedByUser) R.drawable.ic_favorite_line else R.drawable.ic_favorite),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(20.dp)
                                .clickable {
                                    onHeartClick(productResponses[2].id)
                                },
                            colorFilter = ColorFilter.tint(if(!productResponses[2].likedByUser)LightGray else FontPink)
                        )

                    }
                    Text(
                        text = formatPrice(productResponses[2].price),
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
            productResponses =
                mutableListOf(
                    ProductResponse(
                        chatCount = 11,
                        createdAt = "3일 전",
                        id = 1,
                        imgUrl = "",
                        likedByUser = true,
                        likedUsersCount = 18,
                        price = 5000,
                        title = "미피 인형"
                    ),
                    ProductResponse(

                        chatCount = 11,
                        createdAt = "3일 전",
                        id = 1,
                        imgUrl = "",
                        likedByUser = false,
                        likedUsersCount = 18,
                        price = 5000,
                        title = "미피 인형"
                    ),
                    ProductResponse(

                        chatCount = 11,
                        createdAt = "3일 전",
                        id = 1,
                        imgUrl = "",
                        likedByUser = false,
                        likedUsersCount = 18,
                        price = 5000,
                        title = "미피 인형"
                    )

                )
        )
    }

}