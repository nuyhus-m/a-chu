package com.ssafy.achu.ui.mypage.baby

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ssafy.achu.R
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.baby.BabyResponse
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.mypage.recommendlist.BabyListItem

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BabyListScreen(
    onNavigateToBabyDetail: (Int) -> Unit,
    viewModel: ActivityViewModel
) {

    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            BasicTopAppBar(
                title = "아이 정보 관리",
                onBackClick = {
                    // Add logic for back button click if needed
                }
            )

            // 아이 리스트가 있을 때와 없을 때 구분
            if (uiState.babyList.isNotEmpty()) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 24.dp, end = 24.dp, bottom = 114.dp)
                ) {
                    // LazyColumn에 BabyListItem 추가
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f) // LazyColumn이 차지할 공간
                    ) {
                        items(uiState.babyList.size) { index ->
                            BabyListItem(babyInfo = uiState.babyList[index], onClick = {
                                onNavigateToBabyDetail(uiState.babyList[index].id)
                            })
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            } else {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.img_smiling_face),
                        contentDescription = "smile",
                        modifier = Modifier
                            .size(100.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(24.dp))
                    Text(
                        text = "등록된 아이가 없습니다.\n아이별 추천, 추억기록을 위해 \n 아이를 등록해보세요!",
                        style = AchuTheme.typography.semiBold18,
                        color = FontGray,
                        lineHeight = 30.sp,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(bottom = 60.dp),
                        textAlign = TextAlign.Center
                    )
                }
            }
        }

        // 아이 정보 추가 버튼을 화면 하단에 고정
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter) // 화면 하단에 고정
                .padding(horizontal = 24.dp)
                .padding(bottom = 40.dp) // 버튼과 화면 하단 사이의 여백 추가
        ) {
            PointPinkBtn("아이 정보 추가 하기", onClick = {
                onNavigateToBabyDetail(0)
            })
        }
    }
}


@Composable
fun BabyListItem(babyInfo: BabyResponse, onClick: () -> Unit) {
    val birthTextColor = when (babyInfo.gender) {
        "MALE" -> {
            PointBlue
        }

        "FEMALE" -> {
            PointPink
        }

        else -> {
            FontGray
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable { onClick() } // Row에 클릭 이벤트 추가
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(2.dp)


        ) {


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(4.dp, RoundedCornerShape(16.dp))
                    .background(color = White, shape = RoundedCornerShape(16.dp))
                    .height(98.dp)
                    .padding(16.dp)

            ) {

                Row {


                    Box(
                        modifier = Modifier
                            .size(66.dp) // 크기 지정
                            .clip(CircleShape) // 원형 이미지
                            .border(1.dp, birthTextColor, CircleShape) // 성별에 맞는 색상으로 원형 띠 적용
                    ) {
                        val imageUrl = babyInfo.imgUrl

                        // URL이 비어 있으면 기본 이미지 리소스를 사용하고, 그렇지 않으면 네트워크 이미지를 로드합니다.
                        if (imageUrl.isNullOrEmpty()) {
                            // 기본 이미지를 painter로 설정
                            Image(
                                painter = painterResource(id = R.drawable.img_baby_profile),
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
                                model = babyInfo.imgUrl,
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

                    Spacer(modifier = Modifier.size(16.dp))

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${babyInfo.nickname}",
                            style = AchuTheme.typography.semiBold18

                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${babyInfo.birth}",
                            style = AchuTheme.typography.semiBold16.copy(color = birthTextColor)
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(), // Row를 부모 너비에 맞춤
                        verticalAlignment = Alignment.CenterVertically, // 수직 중앙 정렬
                        horizontalArrangement = Arrangement.End // 수평 끝 정렬
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_arrow_forward),
                            contentDescription = "Arrow",
                            modifier = Modifier
                                .size(20.dp), // 크기 설정
                            colorFilter = ColorFilter.tint(Color(0xFFBEBEBE))
                        )
                    }

                }

            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BabyListScreenPreview() {

    AchuTheme {
        BabyListScreen(
            onNavigateToBabyDetail = {},
            viewModel = ActivityViewModel()
        )
//        BabyListItem(BabyInfo2(
//                profileImg = R.drawable.img_baby_profile,
//                babyNickname = "두식이",
//                birth = "첫째(2019.05.04)",
//                gender = "남",
//
//        ))
    }
}

