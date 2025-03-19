package com.ssafy.achu.ui.mypage

import android.R.attr.radius
import android.R.attr.text
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTextField
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.ClearTextField
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PointBlueFlexibleBtn
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.components.PointPinkLineBtn
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BabyDetailScreen(type: String) {
    val type = type
    val titleText = if (type == "등록") "아이 정보 관리" else "두식이 정보"
    val profileBtnText = if (type == "등록") "프로필 사진 등록하기" else "프로필 사진 수정하기"
    val nicknameText = if (type == "등록") "닉네임" else "두식이"


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)

    ) {
        Column {
            BasicTopAppBar(
                title = titleText,
                onBackClick = {

                }
            )

            Column(
                modifier = Modifier
                    .fillMaxSize() // 전체 화면을 차지하도록 설정
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙 정렬

            ) {

                Box(
                    modifier = Modifier
                        .size(150.dp) // 크기 지정
                        .shadow(elevation = 8.dp, shape = CircleShape) // 그림자 적용
                        .clip(CircleShape), // 원형 이미지 적용
                    contentAlignment = Alignment.Center // 내부 컨텐츠 중앙 정렬
                ) {
                    if (type == "등록") {
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .background(color = Color.LightGray),
                            contentAlignment = Alignment.Center // 내부 컨텐츠 중앙 정렬
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.ic_add_a_photo),
                                contentDescription = "Write Icon",
                                modifier = Modifier
                                    .size(40.dp),
                                colorFilter = ColorFilter.tint(FontGray) // 색상을 PointPink로 변경
                            )
                        }
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.img_profile_test),
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                            contentScale = ContentScale.Crop
                        )
                    }
                }


                Spacer(modifier = Modifier.height(16.dp))
                SmallLineBtn(profileBtnText, PointPink, onClick = {})

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = nicknameText, style = AchuTheme.typography.bold24,
                        fontSize = 28.sp
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_write),
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .clickable {
                                //클릭시 다이얼로그 띄우기
                            },
                        colorFilter = ColorFilter.tint(PointPink) // 색을 빨간색으로 변경

                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                Text(
                    text = "생년월일",
                    style = AchuTheme.typography.semiBold18,
                    modifier = Modifier.align(Alignment.Start)

                )

                Spacer(modifier = Modifier.height(8.dp))

                if (type == "등록") {
                    BasicTextField(
                        value = "",
                        onValueChange = {},
                        placeholder = "자녀의 생년월일을 입력해주세요",
                        placeholderColor = FontGray,
                        borderColor = PointPink,
                    )
                } else {
                    ClearTextField(
                        value = "2019.05.04",
                        onValueChange = {},
                        pointColor = PointPink,
                        modifier = Modifier.fillMaxWidth()
                    )
                }


                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "성별",
                    style = AchuTheme.typography.semiBold18,
                    modifier = Modifier.align(Alignment.Start)

                )

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(), // Row가 가로 전체를 차지하도록 설정
                    horizontalArrangement = Arrangement.Start, // 왼쪽 정렬
                    verticalAlignment = Alignment.CenterVertically // 세로 가운데 정렬
                ) {
                    PointPinkLineBtn("남자") { }
                    Spacer(modifier = Modifier.width(8.dp))
                    PointPinkLineBtn("여자") { }
                }

                Spacer(modifier = Modifier.weight(1f))
                PointPinkBtn("등록하기", onClick = {})
                Spacer(modifier = Modifier.height(60.dp))


            }

        }
    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BabyDetailScreenPreview() {
    AchuTheme {
        BabyDetailScreen("등록")
    }

}