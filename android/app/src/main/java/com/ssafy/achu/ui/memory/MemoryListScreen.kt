package com.ssafy.achu.ui.memory

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
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.LoadingImgScreen
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.BabyBlue
import com.ssafy.achu.core.theme.BabyYellow
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.LightPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatBirthDate
import com.ssafy.achu.core.util.formatDate
import com.ssafy.achu.core.util.getNameWithParticle
import com.ssafy.achu.data.model.memory.MemoryResponse
import com.ssafy.achu.ui.ActivityUIState
import com.ssafy.achu.ui.ActivityViewModel
import kotlin.String

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MemoryListScreen(
    modifier: Modifier = Modifier,
    onNavigateToMemoryDetail: (memoryID: Int, babyId: Int) -> Unit,
    viewModel: ActivityViewModel,
    memoryViewModel: MemoryViewModel = viewModel()
) {

    val uiState: ActivityUIState by viewModel.uiState.collectAsState()
    val memoryUIState: MemoryUIState by memoryViewModel.uiState.collectAsState()

    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(uiState.selectedBaby?.nickname) }

    LaunchedEffect(Unit) {
        viewModel.getUnreadCount()
    }

    LaunchedEffect(uiState.selectedBaby?.id) {
        uiState.selectedBaby?.id?.let { babyId ->
            memoryViewModel.getMemoryList(babyId)
        }
    }

    if (uiState.babyList.isEmpty()) {
        Column(
            Modifier
                .fillMaxSize()
                .background(color = White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Image(
                painter = painterResource(id = R.drawable.img_crying_face),
                contentDescription = "Profile",
                modifier = Modifier
                    .size(90.dp)
            )

            Spacer(Modifier.height(24.dp))
            Text(
                text = "등록된 아이가 없습니다.\n우리 아이 정보를 등록하고\n추억을 기록하세요! ",
                style = AchuTheme.typography.semiBold18,
                color = FontGray,
                textAlign = TextAlign.Center,
                lineHeight = 30.sp
            )

        }
    } else {

        Box(
            modifier = modifier
                .fillMaxSize()
                .background(color = White),
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
                        .background(White)
                        .border(
                            1.5.dp,
                            if (uiState.selectedBaby!!.gender == "MALE") PointBlue else PointPink,
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = if (uiState.selectedBaby!!.gender == "MALE") R.drawable.img_profile_baby_boy else R.drawable.img_profile_baby_girl),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .size(82.dp)
                            .clip(CircleShape)
                            .background(if (uiState.selectedBaby!!.gender == "MALE") BabyBlue else BabyYellow),
                        contentScale = ContentScale.Crop
                    )
                    if (!uiState.selectedBaby?.imgUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = uiState.selectedBaby?.imgUrl,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(82.dp)
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }
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
                            text = getNameWithParticle(selectedItem.toString()),
                            style = AchuTheme.typography.semiBold20,
                            modifier = Modifier.alignByBaseline()  // 베이스라인 정렬

                        )

                        Text(
                            text = " 추억",
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
                        modifier = Modifier
                            .wrapContentWidth()
                            .background(color = White)
                    ) {
                        uiState.babyList.forEach { baby ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = baby.nickname,
                                        style = AchuTheme.typography.semiBold16
                                    )
                                },
                                onClick = {
                                    selectedItem = baby.nickname
                                    viewModel.updateSelectedBaby(
                                        baby
                                    )
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = formatBirthDate(uiState.selectedBaby!!.birth),
                    style = AchuTheme.typography.semiBold16,
                    color = if (uiState.selectedBaby!!.gender == "MALE") PointBlue else PointPink
                )
                if (memoryUIState.memoryList.isEmpty()) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(bottom = 40.dp),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_smiling_face),
                            contentDescription = "Profile",
                            modifier = Modifier
                                .size(60.dp)
                                .clickable {
                                    onNavigateToMemoryDetail(0, uiState.selectedBaby!!.id)
                                }
                        )

                        Spacer(Modifier.height(24.dp))
                        Text(
                            text = "${getNameWithParticle(selectedItem.toString())} 추억이 없습니다.\nA-Chu에서 거래하고 추억을 기록하세요! ",
                            style = AchuTheme.typography.semiBold18,
                            color = FontGray,
                            textAlign = TextAlign.Center,
                            lineHeight = 30.sp
                        )
                    }
                } else {

                    LazyColumn(
                        modifier = Modifier.padding(top = 24.dp)
                    ) {
                        items(memoryUIState.memoryList.size) { index ->
                            MemoryListItem(
                                img = memoryUIState.memoryList[index].imgUrl,
                                title = memoryUIState.memoryList[index].title,
                                date = formatDate(memoryUIState.memoryList[index].createdAt),
                                onClick = {
                                    onNavigateToMemoryDetail(
                                        memoryUIState.memoryList[index].id,
                                        memoryUIState.babyId
                                    )
                                }
                            )
                            Spacer(modifier = Modifier.height(16.dp)) // 아이템 간 간격 추가
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun MemoryListItem(img: String, title: String, date: String, onClick: () -> Unit) {
    var isImageLoaded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable(onClick = onClick)
            .shadow(4.dp, RoundedCornerShape(24.dp))
            .background(color = LightPink, shape = RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {
        // 로딩 중일 때만 로딩 화면 표시
        if (!isImageLoaded) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = LightPink)
            ){
                LoadingImgScreen(
                    "추억을 불러오는 중..",
                    modifier = Modifier.fillMaxWidth(), 16, 300)
            }
        }

        AsyncImage(
            model = img,
            contentDescription = "Memory Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(24.dp)),
            onSuccess = {
                isImageLoaded = true
            },
            onLoading = {
                isImageLoaded = false
            },
            onError = {
                isImageLoaded = true // 에러 시에도 로딩 끄고 에러 이미지 띄우게
            }
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
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
                color = Color.White,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 8.dp)

            )
            Text(
                text = date.substringBefore("T"),
                style = AchuTheme.typography.semiBold18White.copy(
                    shadow = Shadow(
                        color = Color.Black.copy(alpha = 0.5f),
                        offset = Offset(2f, 2f),
                        blurRadius = 4f
                    )
                ),
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MemoryListScreenPreview() {

    AchuTheme {
        MemoryListScreen(
            onNavigateToMemoryDetail = { memoryId, babyId ->
            }, viewModel = viewModel(),
            memoryViewModel = viewModel()
        )
//        MemoryListItem(
//            img = R.drawable.img_baby_profile,
//            title = "정말 귀여운 원피스",
//            date = "2023.05.04",
//            onClick = {}
//        )
    }
}

