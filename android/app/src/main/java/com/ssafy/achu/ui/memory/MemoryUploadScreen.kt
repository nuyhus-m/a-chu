package com.ssafy.achu.ui.memory

import android.R.attr.textStyle
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.ssafy.achu.R
import com.ssafy.achu.core.components.CenterTopAppBar
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MemoryUploadScreen() {
    val images = emptyList<Int>()
    val pagerState = rememberPagerState()
    var titleText by remember { mutableStateOf("") }
    var contentText by remember { mutableStateOf("") }
    val maxTitleLength = 15
    val maxContentLength = 100

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .navigationBarsPadding()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            CenterTopAppBar(
                title = "추억 업로드",
                onBackClick = {}
            )
            if (images.size != 0) {
                // 이미지 슬라이드
                HorizontalPager(
                    count = images.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                ) { page ->
                    Image(
                        painter = painterResource(id = images[page]),
                        contentDescription = "Memory Image",
                        modifier = Modifier.fillMaxSize(),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )
                }


                PageIndicator(
                    totalPages = images.size,
                    currentPage = pagerState.currentPage
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .background(Color.LightGray),
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_add_a_photo),
                            contentDescription = "Memory Image",
                            modifier = Modifier
                                .height(32.dp)
                                .width(32.dp),
                            alignment = Alignment.Center,
                            colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(FontGray)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "이미지를 추가해 보세요",
                            style = AchuTheme.typography.semiBold14PointBlue,
                            color = FontGray,
                            textAlign = TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(4.dp))

                        Text(
                            text = "(최대 3장)",
                            style = AchuTheme.typography.semiBold14PointBlue,
                            color = FontGray,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {

                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = titleText,
                    onValueChange = { if (it.length <= maxTitleLength) titleText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("타이틀을 입력해주세요", color = FontGray) },
                    textStyle = AchuTheme.typography.regular16,
                    shape = RoundedCornerShape(16.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = PointPink,
                        unfocusedBorderColor = PointPink,
                        cursorColor = Color.Black
                    ),
                    trailingIcon = {
                        Text(
                            text = "${titleText.length}/$maxTitleLength",
                            color = FontGray,
                            style = AchuTheme.typography.regular14,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    }
                )


                Spacer(modifier = Modifier.height(16.dp))


                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                ) {
                    OutlinedTextField(
                        value = contentText,
                        onValueChange = { if (it.length <= maxContentLength) contentText = it },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopStart), // 텍스트 필드 정렬
                        placeholder = { Text("과 함께한 추억을 기록해보세요!", color = FontGray) },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PointPink,
                            unfocusedBorderColor = PointPink,
                            cursorColor = Color.Black
                        ),
                    )

                }

                Text(
                    text = "${contentText.length}/$maxContentLength",
                    color = PointPink,
                    style = AchuTheme.typography.regular14,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth().padding(top =  4.dp, end = 4.dp)
                        .align(alignment = Alignment.End)

                )



                Spacer(modifier = Modifier.height(24.dp))

                PointPinkBtn(
                    buttonText = "작성 완료",
                    onClick = {}
                )
                Spacer(modifier = Modifier.height(40.dp))

            }

        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MemoryUploadScreenPreview() {
    AchuTheme {
        MemoryUploadScreen()
    }
}