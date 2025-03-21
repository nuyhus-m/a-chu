package com.ssafy.achu.ui.mypage

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.ssafy.achu.R
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BabyListScreen(onNavigateToBabyDetail: () -> Unit) {


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column {
            BasicTopAppBar(
                title = "아이 정보 관리",
                onBackClick = {

                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize() // 전체 화면을 차지하도록 설정
                    .padding(horizontal = 24.dp)
            ) {
                // LazyColumn에 BabyListItem 추가
                LazyColumn {
                    items(babyList.size) { index ->
                        BabyListItem(babyInfo = babyList[index], onClick = {
                            onNavigateToBabyDetail()
                        })
                        Spacer(modifier = Modifier.height(8.dp))
                    }
                }

                // Spacer를 사용하여 버튼을 하단으로 밀기
                Spacer(modifier = Modifier.weight(1f)) // 이 Spacer가 나머지 공간을 차지하도록 함

                // 아이 정보 추가 버튼
                PointPinkBtn("아이 정보 추가 하기", onClick = {
                    onNavigateToBabyDetail()
                })
                Spacer(modifier = Modifier.height(60.dp))
            }

        }
    }


}


val babyList = listOf(
    BabyInfo2(
        profileImg = R.drawable.img_baby_profile,
        babyNickname = "두식이",
        birth = "첫째(2019.05.04)",
        gender = "남"
    ),
    BabyInfo2(
        profileImg = R.drawable.img_baby_profile,
        babyNickname = "소율이",
        birth = "둘째(2020.07.14)",
        gender = "여"
    ),
    BabyInfo2(
        profileImg = R.drawable.img_baby_profile,
        babyNickname = "민호",
        birth = "출산예정",
        gender = "미정"
    )
)

@Composable
fun BabyListItem(babyInfo: BabyInfo2, onClick: () -> Unit) {
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
                            .size(66.dp) // 크기 지정 (부모 Box보다 2dp 더 크게)
                            .clip(CircleShape) // 원형 이미지 적용
                            .border(1.dp, birthTextColor, CircleShape) // 성별에 맞는 색상으로 원형 띠 적용
                    ) {
                        Image(
                            painter = painterResource(id = babyInfo.profileImg!!),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(60.dp) // 이미지 크기 50.dp
                                .clip(CircleShape)
                                .align(Alignment.Center), // 원형 이미지
                            contentScale = ContentScale.Crop // 이미지가 Box에 맞게 잘리도록
                        )
                    }

                    Spacer(modifier = Modifier.size(16.dp))

                    Column(
                        modifier = Modifier.fillMaxHeight(),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "${babyInfo.babyNickname}",
                            style = AchuTheme.typography.semiBold16

                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "${babyInfo.birth}",
                            style = AchuTheme.typography.semiBold14PointBlue.copy(color = birthTextColor)
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
            onNavigateToBabyDetail = {}
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


data class BabyInfo2(
    val profileImg: Int?,
    val babyNickname: String,
    val gender: String,
    val birth: String,
)