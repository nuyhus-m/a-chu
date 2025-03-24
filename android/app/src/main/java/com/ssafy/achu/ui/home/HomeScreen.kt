import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.ui.mypage.BabyInfo
import com.ssafy.achu.ui.mypage.LikeItem2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToLikeList: () -> Unit,
    onNavigateToRecommend: () -> Unit,
    onNavigateToBabyList: () -> Unit,
    onNavigateToProductList: () -> Unit
) {

    val likeItemList = remember {
        mutableListOf(
            LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "토끼 인형", "3,000원"),
            LikeItem2(R.drawable.img_miffy_doll, true, "거래완료", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, true, "판매중", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, false, "거래완료", "곰인형", "2,500원")
        )
    }

    val babyList = remember {
        mutableListOf(
            BabyInfo(
                profileImg = R.drawable.img_baby_profile,
                babyNickname = "두식이",
                birth = "첫째(2019.05.04)",
                gender = "남",
                recommendList = likeItemList
            ),
            BabyInfo(
                profileImg = R.drawable.img_baby_profile,
                babyNickname = "삼식이",
                birth = "둘째(2020.05.04)",
                gender = "여",
                recommendList = likeItemList
            ),
            BabyInfo(
                profileImg = R.drawable.img_baby_profile,
                babyNickname = "튼튼이",
                birth = "출산예정",
                gender = "아직",
                recommendList = likeItemList
            )
        )
    }

    var selectedBaby by remember { mutableStateOf(babyList[0]) }

    val imageList = listOf(
        R.drawable.img_banner1,
        R.drawable.img_banner2
    )

    val coroutineScope = rememberCoroutineScope()
    val listState = rememberLazyListState(initialFirstVisibleItemIndex = imageList.size)
    var currentIndex by remember { mutableStateOf(imageList.size) }
    val scrollState = rememberScrollState() // 스크롤 상태 저장



    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .padding( start = 24.dp, end = 24.dp)
            .verticalScroll(scrollState)
    ) {

        Spacer(Modifier.height(24.dp))
        BabyDropdown(
            babyList = babyList,
            selectedBaby = selectedBaby,
            onBabySelected = { selectedBaby = it }
        )

        Spacer(Modifier.height(16.dp))

        LaunchedEffect(Unit) {
            while (true) {
                delay(3000) // 3초마다 이동
                coroutineScope.launch {
                    val nextIndex = currentIndex + 1
                    listState.animateScrollToItem(nextIndex)
                    currentIndex = nextIndex

                    // 마지막 항목에 도달하면 인덱스 재설정
                    if (currentIndex >= imageList.size * 2) {
                        currentIndex = imageList.size
                        listState.scrollToItem(currentIndex) // 순간적으로 초기화하여 자연스럽게 반복
                    }
                }
            }
        }

        // LazyRow 부분을 Box로 감싸서 수정
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .clip(RoundedCornerShape(8.dp))
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                userScrollEnabled = false // 사용자 스크롤 비활성화
            ) {
                items(imageList.size * 2) { index -> // 2배로 늘려 무한 반복 느낌 구현
                    val actualIndex = index % imageList.size // 실제 이미지 인덱스

                    Box(
                        modifier = Modifier
                            .fillParentMaxWidth() // 부모 너비에 맞춤
                            .wrapContentHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                                indication = null
                            )  {
                                when (actualIndex) {
                                    0 -> onNavigateToBabyList()
                                    1 -> onNavigateToRecommend()
                                }
                            }
                    ) {
                        Image(
                            painter = painterResource(id = imageList[actualIndex]),
                            contentDescription = "Banner $actualIndex",
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(
                                    ratio = 16f / 9f, // 이미지 비율 설정 (예: 16:9)
                                    matchHeightConstraintsFirst = false
                                ),
                            contentScale = ContentScale.Crop // FillWidth 대신 Crop 사용
                        )
                    }
                }
            }

        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryItem(
                R.drawable.ic_category_clothing,
                "의류",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()

                    })
            CategoryItem(
                R.drawable.ic_category_miscellaneous,
                "잡화",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable (
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()
                    })

            CategoryItem(
                R.drawable.ic_category_toys,
                "장난감",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable (
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_maternity,
                "출산",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable (
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()
                    })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            CategoryItem(
                R.drawable.ic_category_furniture,
                "가구",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable (
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_parenting,
                "육아",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable (
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_indoor,
                "실내",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable (
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_others,
                "기타",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable (
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    )  {
                        onNavigateToProductList()
                    })
        }

        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.Bottom
        ) {
            Text(
                text = "추천상품  ",
                style = AchuTheme.typography.semiBold18,
            )

            Text(
                text = "${selectedBaby.babyNickname}",
                style = AchuTheme.typography.semiBold16Pink,
                color = PointBlue
            )
            Spacer(modifier = Modifier.weight(1.0f))
            Text(
                text = "더보기",
                style = AchuTheme.typography.semiBold14PointBlue.copy(
                    textDecoration = TextDecoration.Underline

                ),
                color = FontGray,
                modifier = Modifier.clickable {
                    onNavigateToRecommend()
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

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
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_miffy_doll),
                    contentDescription = "recommend1",
                    modifier = Modifier
                        .fillMaxSize() // 박스를 꽉 채우도록 설정
                        .align(Alignment.Center) // 이미지를 중앙에 배치
                        .clip(RoundedCornerShape(8.dp)) // 이미지를 라운드 처리

                    ,
                    contentScale = ContentScale.Crop // 이미지를 박스를 꽉 채우도록 조정
                )
                Row(
                    modifier = Modifier
                        .padding(8.dp)
                        .height(42.dp)
                        .align(Alignment.TopStart)
                        .fillMaxWidth()
                        .background(
                            color = Color.White.copy(alpha = 0.7f),
                            RoundedCornerShape(4.dp)
                        ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Spacer(Modifier.width(8.dp))
                    Column {
                        Text(
                            text = "추천 상품 2",
                            style = AchuTheme.typography.semiBold14PointBlue,
                            color = FontBlack,
                        )
                        Text(
                            text = "5,000원",
                            style = AchuTheme.typography.semiBold12PointBlue,
                            color = FontPink,
                        )
                    }


                    Spacer(Modifier.weight(1.0f))

                    Image(
                        painter = painterResource(id = R.drawable.ic_favorite_line),
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .size(16.dp)
                            .clickable {

                            },
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontGray)
                    )

                    Spacer(Modifier.width(8.dp))
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
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_miffy_doll),
                        contentDescription = "recommend2",
                        modifier = Modifier
                            .fillMaxSize() // 박스를 꽉 채우도록 설정
                            .clip(RoundedCornerShape(8.dp)) // 이미지를 라운드 처리
                            .align(Alignment.Center),
                        contentScale = ContentScale.Crop
                    )

                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(28.dp)
                            .align(Alignment.TopStart)
                            .fillMaxWidth()
                            .background(
                                color = Color.White.copy(alpha = 0.7f),
                                RoundedCornerShape(4.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "추천 상품 2",
                                style = AchuTheme.typography.semiBold12,
                                fontSize = 10.sp,
                                color = FontBlack,
                            )
                            Text(
                                text = "5,000원",
                                style = AchuTheme.typography.semiBold12,
                                fontSize = 8.sp,
                                color = FontPink,
                            )
                        }


                        Spacer(Modifier.weight(1.0f))

                        Image(
                            painter = painterResource(id = R.drawable.ic_favorite_line),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(12.dp)
                                .clickable {

                                },
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontGray)
                        )

                        Spacer(Modifier.width(8.dp))
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
                    Row(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(28.dp)
                            .align(Alignment.TopStart)
                            .fillMaxWidth()
                            .background(
                                color = Color.White.copy(alpha = 0.7f),
                                RoundedCornerShape(4.dp)
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Spacer(Modifier.width(8.dp))
                        Column {
                            Text(
                                text = "추천 상품 3",
                                style = AchuTheme.typography.semiBold12,
                                fontSize = 10.sp,
                                color = FontBlack,
                            )
                            Text(
                                text = "5,000원",
                                style = AchuTheme.typography.semiBold12,
                                fontSize = 8.sp,
                                color = FontPink,
                            )
                        }


                        Spacer(Modifier.weight(1.0f))

                        Image(
                            painter = painterResource(id = R.drawable.ic_favorite_line),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(12.dp)
                                .clickable {

                                },
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontGray)
                        )

                        Spacer(Modifier.width(8.dp))
                    }

                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Text(
            text = "찜한 상품",
            style = AchuTheme.typography.semiBold18
        )

        Spacer(Modifier.height(24.dp))

        LazyRow(
            modifier = Modifier.fillMaxWidth()
        ) {
            itemsIndexed(babyList[0].recommendList) { index, likeItem -> // 인덱스와 아이템을 동시에 전달
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

        Spacer(Modifier.height(24.dp))


    }

}


@Composable
fun CategoryItem(
    img: Int,
    categoryTitle: String,
    modifier: Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = img),
            contentDescription = "Heart",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = categoryTitle,
            style = AchuTheme.typography.semiBold16
        )
    }
}

@Composable
fun BabyDropdown(
    babyList: MutableList<BabyInfo>,
    selectedBaby: BabyInfo,
    onBabySelected: (BabyInfo) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    val birthTextColor = when (selectedBaby.gender) {
        "남" -> PointBlue
        "여" -> PointPink
        else -> FontGray
    }

    val NicknameTextColor = when (selectedBaby.gender) {
        "남" -> FontBlue
        "여" -> PointPink
        else -> FontGray
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = true }
            .padding(2.dp)
            .height(66.dp)
    ) {
        Box(
            modifier = Modifier
                .size(66.dp)
                .clip(CircleShape)
                .border(1.dp, birthTextColor, CircleShape)
        ) {
            Image(
                painter = painterResource(id = selectedBaby.profileImg!!),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .align(Alignment.Center),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.size(16.dp))

        Column(
            modifier = Modifier.fillMaxHeight(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "재영맘의 ",
                    style = AchuTheme.typography.semiBold16
                )

                Text(
                    text = selectedBaby.babyNickname,
                    style = AchuTheme.typography.semiBold18,
                    color = NicknameTextColor
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                    contentDescription = "Arrow",
                    modifier = Modifier.size(24.dp),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontBlack)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier
                    .wrapContentWidth()
                    .background(color = White)
            ) {
                babyList.forEach { baby ->
                    DropdownMenuItem(
                        text = {
                            Text(
                                text = baby.babyNickname,
                                style = AchuTheme.typography.semiBold16
                            )
                        },
                        onClick = {
                            onBabySelected(baby)
                            expanded = false
                        }
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = selectedBaby.birth,
                style = AchuTheme.typography.semiBold16.copy(color = birthTextColor)
            )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    AchuTheme {
        HomeScreen(
            onNavigateToLikeList = { },
            onNavigateToRecommend = { },
            onNavigateToBabyList = { },
            onNavigateToProductList = {  })
    }
}
