package com.ssafy.achu.ui.mypage

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTextField
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.ClearTextField
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PointBlueFlexibleBtn
import com.ssafy.achu.core.components.PointBlueLineBtn
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserInfoScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙 정렬
        ) {
            BasicTopAppBar(
                title = "내 정보 수정",
                onBackClick = {

                }
            )

            Box(
                modifier = Modifier
                    .size(150.dp) // 크기 지정
                    .shadow(elevation = 8.dp, shape = CircleShape) // 그림자 적용
                    .clip(CircleShape) // 원형 이미지 적용
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_profile_test),
                    contentDescription = "Profile",
                    modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            SmallLineBtn("프로필 수정하기", PointBlue, onClick = {})

            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.Bottom) {
                Spacer(modifier = Modifier.width(20.dp))
                Text(
                    text = "재영맘", style = AchuTheme.typography.bold24,
                    fontSize = 28.sp
                )
                Text(text = "님", style = AchuTheme.typography.bold24)

                Image(
                    painter = painterResource(id = R.drawable.ic_write),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .height(20.dp)
                        .width(20.dp)
                        .clickable {
                            //클릭시 다이얼로그 띄우기
                        },
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "아이디",
                style = AchuTheme.typography.semiBold18,
                modifier = Modifier.align(Alignment.Start)

            )

            Spacer(modifier = Modifier.height(8.dp))

            BasicTextField(
                value = "aa846811",
                onValueChange = {},
                placeholder = "아이디",
                placeholderColor = FontBlack,
                borderColor = PointBlue,
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "전화번호",
                style = AchuTheme.typography.semiBold18,
                modifier = Modifier.align(Alignment.Start)

            )

            Spacer(modifier = Modifier.height(8.dp))

            Row {
                ClearTextField(
                    value = "010-3443-8468",
                    onValueChange = {},
                    pointColor = PointBlue,
                    modifier = Modifier.weight(1f)
                )
                Spacer(modifier = Modifier.width(8.dp))

                PointBlueFlexibleBtn("인증", onClick = {})
            }

            Spacer(modifier = Modifier.height(32.dp))

            PointBlueButton("비밀번호 수정", onClick = {})
            Spacer(modifier = Modifier.height(48.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = "로그아웃",
                    style = AchuTheme.typography.semiBold14PointBlue.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        // 클릭 시 동작
                    }
                )

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "화원탈퇴",
                    style = AchuTheme.typography.semiBold14PointBlue.copy(
                        textDecoration = TextDecoration.Underline
                    ),
                    modifier = Modifier.clickable {
                        // 클릭 시 동작
                    }

                )
            }


        }
    }



}




@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun UserInfoScreenPreview() {
    AchuTheme {
        UserInfoScreen()
    }
}