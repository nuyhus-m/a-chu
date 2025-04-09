import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.LightPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.mypage.userinfo.UserInfoViewModel
import kotlin.String

@Composable
fun MyPageScreen(
    onNavigateToTradeList: () -> Unit,
    modifier: Modifier = Modifier,
    onNavigateToLikeList: () -> Unit,
    onNavigateToRecommend: () -> Unit,
    onNavigateToUserInfo: () -> Unit,
    onNavigateToBabyList: () -> Unit,
    viewModel: ActivityViewModel = viewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()


    val user = uiState.user

    LaunchedEffect(Unit) {
        viewModel.getUnreadCount()
    }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        // 배경 이미지 추가
        Image(
            painter = painterResource(id = R.drawable.img_background_mypage),
            contentDescription = "Background",
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds  // 화면에 꽉 차도록 설정
        )

        // 텍스트 표시 (위에 위치)
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 24.dp,
                    end = 24.dp,
                    top = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
                ),
        ) {

            Column {
                Spacer(modifier = Modifier.height(60.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // 프로필 이미지 + 그림자 효과를 위한 Box 사용
                    Box(
                        modifier = Modifier
                            .size(100.dp) // 크기 지정
                            .shadow(elevation = 4.dp, shape = CircleShape) // 그림자 적용
                            .clip(CircleShape)
                            .background(color = LightPink)// 원형 이미지 적용
                    ) {
                        Image(
                            painter = painterResource(R.drawable.img_profile_basic2),//로딩 이미지
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                            contentScale = ContentScale.Crop
                        )

                        if (user?.profileImageUrl.isNullOrEmpty()) {
                            Image(
                                painter = painterResource(R.drawable.img_profile_basic2),//디폴트 이미지
                                contentDescription = "Profile",
                                modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            AsyncImage(
                                model = user?.profileImageUrl,
                                contentDescription = "Profile",
                                modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                                contentScale = ContentScale.Crop
                            )
                        }
                    }

                    Row(
                        verticalAlignment = Alignment.Bottom // 하단 정렬
                    ) {
                        if (user == null) {
                            Text(
                                text = "사용자 정보 로딩중...",
                                style = AchuTheme.typography.bold24.copy(
                                    fontSize = 24.sp,
                                    lineHeight = 30.sp,
                                    shadow = Shadow(
                                        color = Color.Gray, // 그림자 색상
                                        offset = Offset(2f, 2f), // 그림자 위치 (x, y)
                                        blurRadius = 8f // 그림자 흐림 정도
                                    )
                                ),
                                color = White,
                                modifier = Modifier
                                    .padding(start = 24.dp)
                                    .alignByBaseline()
                            )
                        }
                        user?.nickname?.let {
                            Text(
                                text = it,
                                style = AchuTheme.typography.bold24.copy(
                                    fontSize = 32.sp,
                                    lineHeight = 30.sp,
                                    shadow = Shadow(
                                        color = Color.Gray, // 그림자 색상
                                        offset = Offset(2f, 2f), // 그림자 위치 (x, y)
                                        blurRadius = 8f // 그림자 흐림 정도
                                    )
                                ),
                                color = White,
                                modifier = Modifier
                                    .padding(start = 24.dp)
                                    .alignByBaseline()
                            )

                            Text(
                                text = "님",
                                style = AchuTheme.typography.bold24.copy(
                                    fontSize = 24.sp,
                                    lineHeight = 30.sp,
                                    shadow = Shadow(
                                        color = Color.Gray, // 그림자 색상
                                        offset = Offset(2f, 2f), // 그림자 위치 (x, y)
                                        blurRadius = 4f // 그림자 흐림 정도
                                    )
                                ),
                                color = White,
                                modifier = Modifier.alignByBaseline()
                            )
                        }


                    }

                }

                Spacer(modifier = Modifier.height(100.dp))

                MyPageItem(
                    img = R.drawable.ic_mypage_baby,
                    title = "우리 아이 정보 관리",
                    onClick = { onNavigateToBabyList() }
                )

                Spacer(modifier = Modifier.height(8.dp))

                MyPageItem(
                    img = R.drawable.ic_mypage_tradelist,
                    title = "거래내역 확인",
                    content = "판매, 구매 기록을 확인해 보세요",
                    onClick = {
                        onNavigateToTradeList()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                MyPageItem(
                    img = R.drawable.ic_mypage_like,
                    title = "찜한상품보기",
                    onClick = {
                        onNavigateToLikeList()
                    }
                )

                Spacer(modifier = Modifier.height(8.dp))

                MyPageItem(
                    img = R.drawable.ic_mypage_recomend,
                    title = "추천상품보기",
                    content = "우리아이에게 딱 맞는 상품보기",
                    onClick = { onNavigateToRecommend() }
                )

                Spacer(modifier = Modifier.height(8.dp))

                MyPageItem(
                    img = R.drawable.ic_maypage_myinfo,
                    title = "내 정보 수정",
                    onClick = { onNavigateToUserInfo() }
                )


            }

        }
    }
}


@Composable
fun MyPageItem(img: Int, title: String, content: String? = null, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clip(shape = RoundedCornerShape(16.dp))
            .padding(1.dp)
            .clickable(onClick = onClick)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) // 그림자 적용
            .background(color = White) // 배경색은 shadow 뒤에 설정
    ) {
        Row(
            modifier = Modifier.padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = img),
                contentDescription = "Profile",
                modifier = Modifier
                    .height(60.dp)
                    .width(60.dp), // Box 크기에 맞추기
            )

            Column(
                modifier = Modifier
                    .weight(0.8f)
                    .padding(start = 16.dp)
            ) {

                Text(
                    text = title,
                    style = AchuTheme.typography.medium18,
                )
                if (content != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = content,
                        style = AchuTheme.typography.regular14,
                    )
                }
            }

            Image(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = "Arrow",
                modifier = Modifier
                    .height(20.dp)
                    .width(20.dp)
                    .weight(0.15f),
                colorFilter = ColorFilter.tint(Color(0xFFBEBEBE)) // 색을 빨간색으로 변경
            )

        }
    }
}


@Preview
@Composable
fun MyPageScreenPreview() {
    AchuTheme {  // 테마를 감싸기
        MyPageScreen(
            onNavigateToTradeList = { /* 거래내역 클릭 시 동작 */ },
            onNavigateToLikeList = { /* 찜한 상품 클릭 시 동작 */ },
            onNavigateToRecommend = { /* 추천상품 클릭 시 동작 */ },
            onNavigateToUserInfo = { },
            onNavigateToBabyList = {},
            viewModel = ActivityViewModel()
        )
    }
}


//@Preview
//@Composable
//fun MyPageItemPreview() {
//    AchuTheme {  // 테마를 감싸기
//        MyPageItem(
//            img = R.drawable.ic_mypage_baby,
//            "우리아이정보 등록하기",
//            "제품추천, 추억기록을 위한 아이정보 입력하기",
//            onClick = { /* 클릭 시 동작 */ }
//        )
//    }
//}

