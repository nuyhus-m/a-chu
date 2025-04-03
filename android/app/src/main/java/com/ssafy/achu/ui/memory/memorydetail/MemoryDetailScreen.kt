package com.ssafy.achu.ui.memory.memorydetail

import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ssafy.achu.R
import com.ssafy.achu.core.components.dialog.BasicDialog
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.LightPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import kotlinx.coroutines.flow.collectLatest


private const val TAG = "MemoryDetailScreen 안주현"
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MemoryDetailScreen(
    onNavigateToMemoryUpload: (memoryID:Int, babyId:Int) -> Unit,
    memoryViewModel: MemoryDetailViewModel = viewModel(),
    memoryId: Int,
    babyId: Int,
) {
    val memoryUIState: MemoryDetailUIState by memoryViewModel.uiState.collectAsState()
    val context = LocalContext.current

    val pagerState = rememberPagerState()

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
    LaunchedEffect(Unit) {
        memoryViewModel.isChanged.collectLatest { isChanged ->
            memoryViewModel.getMemory(memoryId)
            Toast.makeText(context, memoryUIState.toastString, Toast.LENGTH_SHORT).show()
            backPressedDispatcher?.onBackPressed()
        }
    }

    memoryViewModel.getMemory(memoryId)


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .navigationBarsPadding()
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 56.dp, start = 24.dp, end = 24.dp, bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_arrow_back_),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(30.dp)
                        .alignByBaseline()
                        .clickable {
                            backPressedDispatcher?.onBackPressed()
                        },
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontBlack)
                )

                Spacer(modifier = Modifier.weight(1.0F))
                Image(
                    painter = painterResource(id = R.drawable.ic_edit_square),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(32.dp)
                        .alignByBaseline()
                        .clickable {
                            onNavigateToMemoryUpload(
                                memoryUIState.selectedMemory.id,
                                babyId
                            )

                        },
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(PointBlue)
                )

                Spacer(modifier = Modifier.width(8.dp))

                Image(
                    painter = painterResource(id = R.drawable.ic_delete),
                    contentDescription = "Arrow",
                    modifier = Modifier
                        .size(32.dp)
                        .alignByBaseline()
                        .clickable {
                            memoryViewModel.showDeleteDialog(true)
                        },
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(PointBlue)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            // 이미지 슬라이드
            HorizontalPager(
                count = memoryUIState.selectedMemory.imgUrls.size,
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
            ) { page ->

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = LightPink), // 기본 배경 설정
                    contentAlignment = Alignment.Center // 가운데 정렬
                ) {
                    AsyncImage(
                        model = memoryUIState.selectedMemory.imgUrls[page],
                        contentDescription = "Memory Image",
                        modifier = Modifier.fillMaxSize(),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop,
                        placeholder = ColorPainter(LightPink) // 로딩 중일 때 배경 유지
                    )
                }
            }

            PageIndicator(
                totalPages = memoryUIState.selectedMemory.imgUrls.size,
                currentPage = pagerState.currentPage
            )



            Text(
                text = memoryUIState.selectedMemory.title,
                style = AchuTheme.typography.semiBold20
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text =  memoryUIState.selectedMemory.createdAt.substringBefore("T"),
                style = AchuTheme.typography.semiBold14PointBlue,
                color = FontGray

            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = memoryUIState.selectedMemory.content,
                style = AchuTheme.typography.regular18,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp),
                textAlign = TextAlign.Center,
                lineHeight = 28.sp,

                )


        }
    }

if (memoryUIState.showDeleteDialog){
    BasicDialog(
        img = painterResource(id = R.drawable.img_crying_face),
        "A - Chu",
        "의",
        text = "추억을 삭제하시겠습니까?",
        onDismiss = {
            memoryViewModel.showDeleteDialog(false)
        },
        onConfirm = {
            memoryViewModel.deleteMemory()
            memoryViewModel.showDeleteDialog(false)
        }
    )
}
}

@Composable
fun PageIndicator(totalPages: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .height(24.dp)
    ) {
        // 각 점을 인디케이터로 표현
        repeat(totalPages) { index ->
            Spacer(modifier = Modifier.width(4.dp))
            // 선택된 페이지일 때와 아닐 때 점 색을 다르게 설정
            val color = if (index == currentPage) PointBlue else LightGray
            Box(
                modifier = Modifier
                    .size(8.dp)
                    .background(color = color, shape = CircleShape)
            )
        }
    }
}





@Preview
@Composable
fun MemoryDetailScreenPreview() {
    AchuTheme { MemoryDetailScreen(
        onNavigateToMemoryUpload = {
                memoryId: Int, babyId: Int ->
        },
        memoryId = 0,
        babyId = 0,

    )
    }
}
