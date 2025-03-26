package com.ssafy.achu.ui.mypage

import BasicLikeItem
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.baby.BabyResponse

@Composable
fun RecommendItemScreen() {
    val likeItemList = remember {
        mutableListOf(
            LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "토끼 인형", "3,000원"),
            LikeItem2(R.drawable.img_miffy_doll, true, "거래완료", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, true, "판매중", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, false, "거래완료", "곰인형", "2,500원")
        )
    }

    val babyList = listOf(
        BabyResponse(
            imgUrl = "https://loremflickr.com/300/300/baby",
            nickname = "두식이",
            id = 1,
            birth = "2019-05-04",
            gender = "남"
        ),
        BabyResponse(
            imgUrl = "",
            nickname = "삼식이",
            id = 2,
            birth = "2020-07-14",
            gender = "여"
        ),
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Box() {
                BasicTopAppBar(
                    title = "추천 상품",
                    onBackClick = {

                    }
                )
            }

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {


                LazyColumn(
                    modifier = Modifier.fillMaxSize() // LazyColumn 크기 설정
                ) {
                    itemsIndexed(babyList) { index, babyInfo ->
                        BabyListItem(babyInfo, likeItemList) // BabyListItem을 LazyColumn 내에 추가
                    }
                }


                Spacer(modifier = Modifier.height(24.dp))


            }
        }
    }
}


@Composable
fun BabyListItem(babyInfo: BabyResponse, list : List<LikeItem2>) {
    val birthTextColor = when (babyInfo.gender) {
        "남" -> {
            PointBlue
        }

        "여" -> {
            // 여성의 경우 텍스트 색상 (예: 분홍색)
            PointPink
        }

        else -> {
            FontGray
        }
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(color = White)
    ) {
        Column(modifier = Modifier.padding(start = 2.dp, bottom = 24.dp)) {

            Row(
                verticalAlignment = Alignment.CenterVertically // 세로 중앙 정렬
            ) {
                Box(
                    modifier = Modifier
                        .size(56.dp) // 크기 지정
                        .shadow(elevation = 8.dp, shape = CircleShape) // 그림자 적용
                        .clip(CircleShape) // 원형 이미지 적용
                ) {
                    Box(
                        modifier = Modifier
                            .size(56.dp) // 크기 지정 (부모 Box보다 2dp 더 크게)
                            .clip(CircleShape) // 원형 이미지 적용
                            .border(1.dp, birthTextColor, CircleShape) // 성별에 맞는 색상으로 원형 띠 적용
                            .align(Alignment.Center) // 중앙에 배치
                    )

                    val imageUrl = babyInfo.imgUrl

                    // URL이 비어 있으면 기본 이미지 리소스를 사용하고, 그렇지 않으면 네트워크 이미지를 로드합니다.
                    if (imageUrl.isNullOrEmpty()) {
                        // 기본 이미지를 painter로 설정
                        Image(
                            painter = painterResource(id = R.drawable.img_baby_profile),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(50.dp) // 이미지 크기 50.dp
                                .clip(CircleShape) // 원형 이미지
                                .align(Alignment.Center), // 중앙에 배치
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // URL을 통해 이미지를 로드
                        AsyncImage(
                            model = babyInfo.imgUrl,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(50.dp) // 이미지 크기 50.dp
                                .clip(CircleShape) // 원형 이미지
                                .align(Alignment.Center), // 중앙에 배치
                            error = painterResource(R.drawable.img_baby_profile)
                        )

                    }

                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${babyInfo.nickname}",
                    style = AchuTheme.typography.semiBold18
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "${babyInfo.birth}",
                    style = AchuTheme.typography.semiBold14PointBlue.copy(color = birthTextColor) // 성별에 맞춰 색상 변경
                )
            }
            Spacer(modifier = Modifier.height(16.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(1.dp)
                    .background(color = LightGray, shape = RoundedCornerShape(5.dp))
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(list) { index, likeItem -> // 인덱스와 아이템을 동시에 전달
                    BasicLikeItem(
                        isLiked = likeItem.like,
                        onClickItem = { }, // 아이템 전체 클릭 시 동작
                        onClickHeart = { }, // 하트 클릭 시 동작
                        productName = likeItem.productName,
                        state = likeItem.sate,
                        price = likeItem.price,
                        img = likeItem.img?.let { painterResource(id = likeItem.img) },
                    ) // 각 아이템을 컴포넌트로 렌더링
                    Spacer(modifier = Modifier.width(8.dp)) // 아이템 간 간격 추가
                }
            }

        }
    }
}


@Preview
@Composable
fun RecommendItemScreenPreview() {
    AchuTheme {
        RecommendItemScreen()
//        BabyListItem(
//            BabyInfo(
//                profileImg = R.drawable.img_baby_profile,
//                babyNickname = "두식이",
//                birth = "첫째(2019.05.04)",
//                gender = "남",
//                recommendList = mutableListOf(
//                    LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "토끼 인형", "3,000원"),
//                    LikeItem2(R.drawable.img_miffy_doll, true, "거래완료", "곰인형", "2,500원"),
//                    LikeItem2(R.drawable.img_miffy_doll, true, "판매중", "곰인형", "2,500원"),
//                    LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "곰인형", "2,500원"),
//                    LikeItem2(R.drawable.img_miffy_doll, false, "거래완료", "곰인형", "2,500원")
//                )
//            )
//        )
    }
}

data class LikeItem2(
    val img: Int?, // 이미지 리소스 ID
    val like: Boolean,
    val sate: String,// 제품 상태 (판매중/구매중 등)
    val productName: String, // 제품명
    val price: String // 가격
)
