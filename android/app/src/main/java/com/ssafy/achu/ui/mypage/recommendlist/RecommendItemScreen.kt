package com.ssafy.achu.ui.mypage.recommendlist

import BasicRecommendItem
import RecommendViewModel
import android.R.attr.x
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.LoadingImg
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.BabyBlue
import com.ssafy.achu.core.theme.BabyYellow
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun RecommendItemScreen(
    activityViewModel: ActivityViewModel = viewModel(),
    onNavigateToProductDetail: () -> Unit
) {
    val uiState = activityViewModel.uiState.collectAsState()
    val context = LocalContext.current
    val recommendViewModel: RecommendViewModel = viewModel()

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

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = White)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BasicTopAppBar(
                title = "추천 상품",
                onBackClick = { backPressedDispatcher?.onBackPressed() }
            )

            Column(modifier = Modifier.padding(horizontal = 24.dp)) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    itemsIndexed(uiState.value.babyList) { index, babyInfo ->

                        // 추천 리스트 요청
                        LaunchedEffect(babyInfo.id) {
                            if (recommendViewModel.recommendMap.value[babyInfo.id] == null) {
                                recommendViewModel.getRecommendList(babyInfo.id)
                            }
                        }

                        val recommendMap by recommendViewModel.recommendMap.collectAsState()

                        val recommendList = recommendMap[babyInfo.id] ?: emptyList()
                        BabyListItem(
                            babyInfo = babyInfo,
                            list = recommendList,
                            onClick = { activityViewModel.getProductDetail(it)},
                            viewModel = recommendViewModel
                        )
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun BabyListItem(
    babyInfo: BabyResponse,
    list: List<ProductResponse>,
    onClick: (Int) -> Unit,
    viewModel: RecommendViewModel
) {
    val birthTextColor = when (babyInfo.gender) {
        "MALE" -> {
            PointBlue
        }

        "FEMALE" -> {
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
                    Image(
                        painter = painterResource(id = R.drawable.img_baby_profile),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(50.dp) // 이미지 크기 50.dp
                            .clip(CircleShape) // 원형 이미지
                            .align(Alignment.Center), // 중앙에 배치
                        contentScale = ContentScale.Crop
                    )

                    // URL이 비어 있으면 기본 이미지 리소스를 사용하고, 그렇지 않으면 네트워크 이미지를 로드합니다.
                    if (imageUrl.isEmpty()) {
                        // 기본 이미지를 painter로 설정
                        Image(
                            painter = painterResource(id = if (babyInfo.gender == "MALE") R.drawable.img_profile_baby_boy else R.drawable.img_profile_baby_girl),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(50.dp) // 이미지 크기 50.dp
                                .clip(CircleShape) // 원형 이미지
                                .align(Alignment.Center)
                                .background(if (babyInfo.gender == "MALE") BabyBlue else BabyYellow), // 중앙에 배치
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
                    text = babyInfo.nickname,
                    style = AchuTheme.typography.semiBold18
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = babyInfo.birth,
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

            if (list.isEmpty()) {
                Box(modifier = Modifier.fillMaxWidth().height(220.dp)){
                    LoadingImg("추천상품 로드중...", Modifier.fillMaxWidth(), 16)
                }
            } else {
                LazyRow(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    itemsIndexed(list) { index, item -> // 리스트와 함께 사용해야 함
                        BasicRecommendItem(
                            product = item,
                            onClickItem = {onClick(it) }, // 클릭 시 ViewModel에서 Detail 요청
                            onLikeClick = { productId ->
                                viewModel.likeItem(productId, babyInfo.id)
                            },
                            onUnLikeClick = { productId ->
                                viewModel.unlikeItem(productId)
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp)) // 아이템 간 간격
                    }
                }
            }
        }
    }
}


