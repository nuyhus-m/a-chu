import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.home.homecomponents.BabyDropdown
import com.ssafy.achu.ui.home.homecomponents.RecommendGrid
import com.ssafy.achu.ui.mypage.recommendlist.LikeItem2
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToLikeList: () -> Unit,
    onNavigateToRecommend: () -> Unit,
    onNavigateToBabyList: () -> Unit,
    onNavigateToProductList: () -> Unit,
    viewModel: ActivityViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {

    val uiState by viewModel.uiState.collectAsState()



    val likeItemList = remember {
        mutableListOf(
            LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "토끼 인형", "3,000원"),
            LikeItem2(R.drawable.img_miffy_doll, true, "거래완료", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, true, "판매중", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, false, "판매중", "곰인형", "2,500원"),
            LikeItem2(R.drawable.img_miffy_doll, false, "거래완료", "곰인형", "2,500원")
        )
    }

    var babyList = listOf(
        BabyResponse(
            imgUrl = "https://loremflickr.com/300/300/baby",
            nickname = "두식이",
            id = 1,
            birth = "첫째(2019.05.04)",
            gender = "남"
        ),
        BabyResponse(
            imgUrl = "",
            nickname = "삼식이",
            id = 2,
            birth = "둘째(2020.07.14)",
            gender = "여"
        ),
    )


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
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
            .verticalScroll(scrollState)
    ) {

        Spacer(Modifier.height(24.dp))
        if (uiState.user != null) {
            BabyDropdown(
                babyList = babyList,
                selectedBaby = selectedBaby,
                onBabySelected = { selectedBaby = it },
                user = uiState.user!!
            )
        } else {
            // 로딩 상태 또는 에러 메시지 표시
            Text(text = "사용자 정보를 불러오는 중...")
        }

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
                .wrapContentHeight().padding(horizontal = 4.dp)
                .clip(RoundedCornerShape(8.dp))
        ) {
            LazyRow(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(0.dp),
                userScrollEnabled = true
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
                            ) {
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
                    ) {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_miscellaneous,
                "잡화",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    ) {
                        onNavigateToProductList()
                    })

            CategoryItem(
                R.drawable.ic_category_toys,
                "장난감",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    ) {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_maternity,
                "출산",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    ) {
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
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    ) {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_parenting,
                "육아",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    ) {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_indoor,
                "실내",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    ) {
                        onNavigateToProductList()
                    })
            CategoryItem(
                R.drawable.ic_category_others,
                "기타",
                modifier = Modifier
                    .weight(1.0f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() }, // 리플 제거
                        indication = null
                    ) {
                        onNavigateToProductList()
                    })
        }

        Spacer(modifier = Modifier.height(24.dp))
        Column(Modifier.padding(horizontal = 24.dp)) {

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "추천상품  ",
                    style = AchuTheme.typography.semiBold20,
                )

                Text(
                    text = "${selectedBaby.nickname}",
                    style = AchuTheme.typography.semiBold18,
                    color = if(selectedBaby.gender == "남")PointBlue else PointPink
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
            RecommendGrid(
                listOf(
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
                ), onClick = {},//아이템 클릭시
                onHeartClick = {

                }//하트 클릭시
            )

            Spacer(Modifier.height(24.dp))

            Text(
                text = "찜한 상품",
                style = AchuTheme.typography.semiBold20
            )

            Spacer(Modifier.height(24.dp))

            LazyRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                itemsIndexed(likeItemList) { index, likeItem -> // 인덱스와 아이템을 동시에 전달
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

}

@Composable
fun CategoryItem(
    img: Int,
    categoryTitle: String,
    modifier: Modifier
) {
    Column(
        modifier = modifier,
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

@Preview
@Composable
fun HomeScreenPreview() {
    AchuTheme {
        HomeScreen(
            onNavigateToLikeList = { },
            onNavigateToRecommend = { },
            onNavigateToBabyList = { },
            onNavigateToProductList = { }
        )
    }
}
