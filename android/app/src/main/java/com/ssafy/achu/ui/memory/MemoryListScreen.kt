package com.ssafy.achu.ui.memory

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
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@Composable
fun MemoryListScreen(modifier: Modifier = Modifier, onNavigateToMemoryDetail: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf("두식이") }
    val items = listOf("두식이", "삼식이", "튼튼이")

    val memoryList = listOf(
        MemoryItemDto(R.drawable.img_test_baby_doll, "첫 돌 기념", "2022.03.14"),
        MemoryItemDto(R.drawable.img_test_baby_summer, "여름 휴가", "2022.07.20"),
        MemoryItemDto(R.drawable.img_test_sopung, "소풍 간 날", "2023.06.30"),
        MemoryItemDto(R.drawable.img_baby_profile, "놀이터에서", "2023.04.15"),

    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
          ,
    ) {
        Column(
            modifier = Modifier
                .padding(top = 40.dp)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(90.dp)
                    .shadow(elevation = 4.dp, shape = CircleShape)
                    .border(1.5.dp, PointBlue, CircleShape)
                    .background(color = Color.LightGray),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.img_baby_profile),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(80.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentSize(Alignment.Center)
            ) {

                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .clickable { expanded = true }
                        .padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically  // 세로 중앙 정렬

                ) {
                    Spacer(modifier = Modifier.padding(start = 32.dp))
                    Text(
                        text = selectedItem,
                        style = AchuTheme.typography.semiBold20,
                        modifier = Modifier.alignByBaseline()  // 베이스라인 정렬

                    )

                    Text(
                        text = "의 추억",
                        style = AchuTheme.typography.semiBold16,
                        modifier = Modifier.alignByBaseline()  // 베이스라인 정렬

                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_arrow_drop_down),
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .size(28.dp)
                            .alignByBaseline(),
                        colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontBlack)
                    )
                }


                // 드롭다운 메뉴는 Box 안에서 정의
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.wrapContentWidth().background(color = White)
                ) {
                    items.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item,
                                style = AchuTheme.typography.semiBold16)
                                   },
                            onClick = {
                                selectedItem = item
                                expanded = false
                                //클릭되면 바꿔라이
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "2019.05.04일생",
                style = AchuTheme.typography.semiBold16,
                color = PointBlue
            )

            LazyColumn(
                modifier = Modifier.padding(top = 24.dp)
            ) {
                items(memoryList.size) { index ->
                    MemoryListItem(
                        img = memoryList[index].imgRes,
                        title = memoryList[index].title,
                        date = memoryList[index].date,
                        onClick = {onNavigateToMemoryDetail()}
                    )
                    Spacer(modifier = Modifier.height(16.dp)) // 아이템 간 간격 추가
                }
            }
        }
    }
}

@Composable
fun MemoryListItem(img: Int, title: String, date: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick,)
            .shadow(4.dp, RoundedCornerShape(24.dp)) // 그림자 추가
            .background(color = Color.LightGray, shape = RoundedCornerShape(24.dp))
    ) {
        // 이미지가 박스에 꽉 차도록 설정
        Image(
            painter = painterResource(id = img),
            contentDescription = "Memory Image",
            contentScale = ContentScale.Crop, // 이미지가 꽉 차도록 설정
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp)) // 박스 모양에 맞게 클립
        )

        // 텍스트를 이미지 위에 오버레이
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 40.dp),
            verticalArrangement = Arrangement.Bottom, // 아래쪽 정렬
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = AchuTheme.typography.bold24.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f
                    )
                ),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)

            )
            Text(
                text = date,
                style = AchuTheme.typography.semiBold18White.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f), offset = Offset(2f, 2f), blurRadius = 4f
                    )
                ),
            )
        }
    }
}


data class MemoryItemDto(
    val imgRes: Int, // 이미지 리소스 ID
    val title: String, // 추억 제목
    val date: String // 날짜
)

@Preview
@Composable
fun MemoryListScreenPreview() {

    AchuTheme {
        MemoryListScreen {}
//        MemoryListItem(
//            img = R.drawable.img_baby_profile,
//            title = "정말 귀여운 원피스",
//            date = "2023.05.04",
//            onClick = {}
//        )
    }
}

