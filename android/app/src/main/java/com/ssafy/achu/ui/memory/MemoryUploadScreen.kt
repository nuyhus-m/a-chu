package com.ssafy.achu.ui.memory

import android.content.Context
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
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
import com.ssafy.achu.core.components.CenterTopAppBar
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class)
@Composable
fun MemoryUploadScreen(
    memoryViewModel: MemoryEditViewModel = viewModel(),
    onNavigateToMemoryDetail: () -> Unit,
    memoryId: Int
) {
    val memoryUIState: MemoryEditUIState by memoryViewModel.uiState.collectAsState()
    val pagerState = rememberPagerState()
    val maxTitleLength = 15
    val maxContentLength = 100
    var images by remember(memoryUIState.selectedMemory.imgUrls) {
        mutableStateOf(memoryUIState.selectedMemory.imgUrls.map { Uri.parse(it) })
    }

    val context = LocalContext.current

    // 갤러리에서 여러 이미지를 선택하는 ActivityResultLauncher
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            if (uris.size + images.size <= 3) { // 최대 3장 선택
                images = images + uris
            } else {
                Toast.makeText(context, "최대 3장까지만 선택 가능합니다.", Toast.LENGTH_SHORT).show()
            }
        }
    )

    fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part? {
        val file = File(context.cacheDir, "upload_${System.currentTimeMillis()}.jpg")
        val inputStream = context.contentResolver.openInputStream(uri) ?: return null
        file.outputStream().use { output -> inputStream.copyTo(output) }

        val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", file.name, requestFile)
    }

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
                title = if (memoryUIState.selectedMemory.title == "")"추억 업로드" else "추억 수정",
                onBackClick = {}
            )
            if (images.size != 0) {
                // 이미지 슬라이드
                HorizontalPager(
                    count = images.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)

                ) { page ->
                    AsyncImage(
                        model = images[page], // Uri를 모델로 사용
                        contentDescription = "Memory Image",
                        modifier = Modifier.fillMaxSize(),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        IconButton(
                            onClick = {
                                images = images.filterIndexed { index, _ -> index != page }

                            },
                            modifier = Modifier
                                .padding(16.dp)
                                .background(PointPink, RoundedCornerShape(8.dp))
                                .size(28.dp)


                        ) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "삭제하기",
                                tint = Color.White,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }

                    // "사진 추가하기" 텍스트를 우측 하단에 배치하려면 Box 사용
                    if (images.size < 3 && images.isNotEmpty()) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize() // 화면 전체를 차지하게 함
                                .padding(16.dp)

                        ) {


                            Text(
                                text = "사진 추가하기",
                                style = AchuTheme.typography.semiBold14PointBlue.copy(
                                    shadow = Shadow(
                                        color = Color.Black.copy(alpha = 0.5f),
                                        offset = Offset(2f, 2f),
                                        blurRadius = 4f
                                    )
                                ),
                                color = White,
                                modifier = Modifier
                                    .clickable {
                                        launcher.launch("image/*") // 이미지 선택
                                    }

                                    .align(Alignment.BottomEnd) // Box 내에서 우측 하단에 배치
                            )


                        }
                    }
                }



                PageIndicator(
                    totalPages = images.size,
                    currentPage = pagerState.currentPage,
                )


            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .background(Color.LightGray)
                        .clickable {
                            launcher.launch("image/*") // 이 부분을 추가
                        },
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

                Spacer(modifier = Modifier.height(24.dp))
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp)
            ) {


                OutlinedTextField(
                    value = if (memoryUIState.selectedMemory.title == "") memoryUIState.memoryTitle else memoryUIState.selectedMemory!!.title,
                    onValueChange = { if (it.length <= maxTitleLength) memoryViewModel.memoryTitleUpdate(it)},
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
                            text = "${memoryUIState.memoryTitle.length}/$maxTitleLength",
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
                        value = if (memoryUIState.selectedMemory.content == "") memoryUIState.memoryContent else memoryUIState.selectedMemory!!.content,
                        onValueChange = { if (it.length <= maxContentLength) memoryViewModel.memoryContentUpdate(it)},
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
                    text = "${memoryUIState.memoryContent.length}/$maxContentLength",
                    color = PointPink,
                    style = AchuTheme.typography.regular14,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, end = 4.dp)
                        .align(alignment = Alignment.End)

                )



                Spacer(modifier = Modifier.height(24.dp))

                PointPinkBtn(
                    buttonText = if(memoryUIState.selectedMemory.title == "")"작성 완료" else "수정완료",
                    onClick = {
                        val multipartFiles = images.mapNotNull { uri -> uriToMultipart(context, uri) }
                        memoryViewModel.memoryImageUpdate(multipartFiles)
                        if (memoryUIState.selectedMemory.title == "") {
                            memoryViewModel.uploadMemory()
                            onNavigateToMemoryDetail()
                        } else {
                            memoryViewModel.changeMemory()
                            memoryViewModel.updateImage()
                            onNavigateToMemoryDetail()
                        }
                    }
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
        MemoryUploadScreen(
            onNavigateToMemoryDetail = {  },
            memoryId =  0
        )
    }
}

