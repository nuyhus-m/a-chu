import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.home.HomeViewModel
import com.ssafy.achu.ui.home.homecomponents.BabyDropdown
import com.ssafy.achu.ui.home.homecomponents.RecommendGrid
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToLikeList: () -> Unit,
    onNavigateToRecommend: () -> Unit,
    onNavigateToBabyList: () -> Unit,
    onNavigateToProductList: (Int) -> Unit,
    viewModel: ActivityViewModel = viewModel(),
    homeViewModel: HomeViewModel = viewModel(),
    onNavigateToProductDetail: () -> Unit,
) {

    val context = LocalContext.current
    LaunchedEffect(Unit) {
        homeViewModel.getLikeItemList()
        homeViewModel.getCategoryList()
    }

    LaunchedEffect(Unit) {
        viewModel.errorMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.getProductSuccess.collectLatest {
            if (it) {
                onNavigateToProductDetail()
            }
        }
    }

    val likeItemList by homeViewModel.likeItemList.collectAsState()
    val categoryList by homeViewModel.categoryList.collectAsState()

    val uiState by viewModel.uiState.collectAsState()



    viewModel.getBabyList()


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
        if (uiState.user != null && uiState.babyList.size != 0) {
            LaunchedEffect(uiState.babyList) {
                if (uiState.selectedBaby == null) {
                    viewModel.updateSelectedBaby(uiState.babyList[0])
                }
            }
            uiState.selectedBaby?.let {
                BabyDropdown(
                    babyList = uiState.babyList,
                    selectedBaby = it,
                    onBabySelected = { baby -> viewModel.updateSelectedBaby(baby) },
                    user = uiState.user!!
                )
            }
        } else if (uiState.user == null) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "사용자 정보를 불러오는 중...",
                    style = AchuTheme.typography.semiBold16,
                    color = PointBlue,
                    modifier = Modifier.height(66.dp)
                )
            }
        } else if (uiState.babyList.isEmpty()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(66.dp)
                    .padding(horizontal = 24.dp),
                verticalAlignment = Alignment.CenterVertically

            ) {
                Box(
                    modifier = Modifier
                        .size(66.dp)
                        .clip(CircleShape)
                        .border(1.dp, PointBlue, CircleShape)
                ) {

                    val imageUrl = uiState.user!!.profileImageUrl
                    if (imageUrl.isEmpty()) {
                        Image(
                            painter = painterResource(id = R.drawable.img_profile_test),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // URL을 통해 이미지를 로드
                        AsyncImage(
                            model = imageUrl,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .align(Alignment.Center),
                            contentScale = ContentScale.Crop,
                            error = painterResource(R.drawable.img_baby_profile)
                        )

                    }
                }
                Spacer(Modifier.width(16.dp))
                Column(
                    Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${uiState.user!!.nickname}님 안녕하세요!",
                        style = AchuTheme.typography.semiBold18,
                        color = FontBlack,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "등록된 아이가 없습니다.",
                        style = AchuTheme.typography.semiBold16,
                        color = PointBlue,
                    )
                }

            }
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
                .wrapContentHeight()
                .padding(horizontal = 4.dp)
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

        LazyVerticalGrid(
            columns = GridCells.Fixed(4), // 4개의 열로 고정
            modifier = Modifier
                .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
                .height(180.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(categoryList) { category ->
                CategoryItem(
                    img = category.imgUrl,
                    categoryTitle = category.name,
                    modifier = Modifier
                        .clickable {
                            onNavigateToProductList(category.id)
                        }
                )
            }
        }


        Column(Modifier.padding(horizontal = 24.dp)) {

            Row(
                verticalAlignment = Alignment.Bottom
            ) {
                Text(
                    text = "추천상품  ",
                    style = AchuTheme.typography.semiBold20,
                )
                if (uiState.selectedBaby != null) {
                    Text(
                        text = uiState.selectedBaby!!.nickname,
                        style = AchuTheme.typography.semiBold18,
                        color = if (uiState.selectedBaby!!.gender == "MALE") PointBlue else PointPink
                    )
                }
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

            if (likeItemList.isNullOrEmpty()) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.img_crying_face),
                        contentDescription = "Crying Face",
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(30.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "찜한 상품이 없습니다.",
                        style = AchuTheme.typography.semiBold18,
                        lineHeight = 40.sp,
                        color = FontGray,
                        textAlign = TextAlign.Center
                    )
                }
            } else {

                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    // 스크롤 상태를 기억하기 위한 rememberLazyListState 추가
                    state = rememberLazyListState()
                ) {
                    itemsIndexed(likeItemList) { index, likeItem -> // 인덱스와 아이템을 동시에 전달
                        BasicLikeItem(
                            onClickItem = {
                                viewModel.getProductDetail(likeItem.id)
                            },
                            likeCLicked = {
                                homeViewModel.likeItem(likeItem.id)
                            },
                            unlikeClicked = {
                                homeViewModel.unlikeItem(likeItem.id)
                            },
                            productName = likeItem.title,
                            state = likeItem.tradeStatus,
                            price = likeItem.price,
                            img = likeItem.imgUrl,
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // 아이템 간 간격 추가

                        // 리스트의 끝에 도달하면 추가 데이터 로드
                        if (index == likeItemList.size - 2) {
                            LaunchedEffect(index) {
                                homeViewModel.loadMoreItems()
                            }
                        }
                    }

                    // 로딩 중일 때 표시할 로딩 인디케이터
                    item {
                        if (homeViewModel.isLoading.collectAsState().value) {
                            CircularProgressIndicator(
                                modifier = Modifier.padding(horizontal = 8.dp)
                            )
                        }
                    }
                }

                Spacer(Modifier.height(24.dp))

            }

        }
    }
}

@Composable
fun CategoryItem(
    img: String,
    categoryTitle: String,
    modifier: Modifier
) {

    val context = LocalContext.current
    val resId = context.resources.getIdentifier(img, "drawable", context.packageName)
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = resId),
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
            onNavigateToProductList = { },
            onNavigateToProductDetail = {
            }
        )
    }
}
