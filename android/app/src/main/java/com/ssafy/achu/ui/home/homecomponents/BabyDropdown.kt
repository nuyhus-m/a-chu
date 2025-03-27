package com.ssafy.achu.ui.home.homecomponents

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.baby.BabyResponse
import kotlin.collections.forEach

@Composable
fun BabyDropdown(
    babyList: List<BabyResponse>,
    selectedBaby: BabyResponse,
    onBabySelected: (BabyResponse) -> Unit
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

            val imageUrl = selectedBaby.imgUrl
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
                    model = selectedBaby.imgUrl,
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
            Row(
                modifier = Modifier
                    .wrapContentWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "재영맘의 ",
                    style = AchuTheme.typography.semiBold18
                )

                Text(
                    text = selectedBaby.nickname,
                    style = AchuTheme.typography.semiBold20,
                    color = NicknameTextColor
                )

                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                    contentDescription = "Arrow",
                    modifier = Modifier.size(24.dp),
                    colorFilter = ColorFilter.tint(FontBlack)
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
                                text = baby.nickname,
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
