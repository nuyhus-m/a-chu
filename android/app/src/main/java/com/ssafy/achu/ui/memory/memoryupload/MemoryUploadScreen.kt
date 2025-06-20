package com.ssafy.achu.ui.memory.memoryupload

import android.R.attr.maxLines
import android.R.attr.textStyle
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.relocation.BringIntoViewRequester
import androidx.compose.foundation.relocation.bringIntoViewRequester
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
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
import com.ssafy.achu.core.LoadingImgScreen
import com.ssafy.achu.core.LoadingScreen
import com.ssafy.achu.core.components.CenterTopAppBar
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.LightPink
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.getProductWithParticle
import com.ssafy.achu.core.util.isImageValid
import com.ssafy.achu.core.util.uriToMultipart
import com.ssafy.achu.ui.memory.memorydetail.PageIndicator
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

private const val TAG = "MemoryUploadScreen안주현"

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun MemoryUploadScreen(
    onNavigateToMemoryDetail: (memoryId: Int, babyId: Int) -> Unit,
    memoryId: Int,
    babyId: Int,
    productName: String
) {
    val context = LocalContext.current
    val memoryViewModel: MemoryEditViewModel = viewModel()
    val memoryUIState: MemoryEditUIState by memoryViewModel.uiState.collectAsState()
    val pagerState = rememberPagerState()
    val maxTitleLength = 15
    val maxContentLength = 100
    var isGallery by remember { mutableStateOf(false) }

    var images by remember(memoryUIState.selectedMemory.imgUrls) {
        mutableStateOf(memoryUIState.selectedMemory.imgUrls.map { Uri.parse(it) })
    }

    val contentFocusRequester = remember { FocusRequester() }



    var isLoading by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        memoryViewModel.isChanged.collectLatest { isChanged ->
            if (isChanged) {
                Toast.makeText(context, memoryUIState.toastString, Toast.LENGTH_SHORT).show()
                onNavigateToMemoryDetail(memoryUIState.selectedMemory.id, babyId)

            } else {
                Toast.makeText(context, memoryUIState.toastString, Toast.LENGTH_SHORT).show()
            }


        }
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetMultipleContents(),
        onResult = { uris ->
            isGallery = false
            if (uris.isEmpty()) {
                isLoading = false
                return@rememberLauncherForActivityResult
            }

            val validUris = uris.filter { uri ->
                isImageValid(context, uri)
            }

            if (validUris.isEmpty()) {
                isLoading = false
                return@rememberLauncherForActivityResult
            }

            if (validUris.size + images.size <= 3) {
                isLoading = true
                images = images + validUris

                val multipartFiles = images.mapNotNull { uri -> uriToMultipart(context, uri) }

                memoryViewModel.memoryImageUpdate(multipartFiles)
                memoryViewModel.isImageChanged(true)

                Log.d(TAG, "MemoryUploadScreen: $multipartFiles")
            } else {
                Toast.makeText(context, "최대 3장까지만 선택 가능합니다.", Toast.LENGTH_SHORT).show()
                isLoading = false
            }
        }
    )


    LaunchedEffect(images) {
        if (isLoading && images.isNotEmpty()) {
            delay(100)
            isLoading = false
        }
    }


    LaunchedEffect(key1 = Unit) {
        memoryViewModel.babyIdUpdate(babyId)
        memoryViewModel.getMemory(memoryId)
    }


    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher



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
                title = if (memoryUIState.selectedMemory.id == 0) "추억 업로드" else "추억 수정",
                onBackClick = {
                    backPressedDispatcher?.onBackPressed()

                }
            )

            if (isLoading && images.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightPink)
                        .height(350.dp),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingImgScreen("이미지 로딩중...", Modifier.fillMaxWidth(), 16, 250)
                }

                Spacer(modifier = Modifier.height(24.dp))
            } else if (images.size != 0) {
                HorizontalPager(
                    count = images.size,
                    state = pagerState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)

                ) { page ->
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(350.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        LoadingImgScreen("이미지 로딩중...", Modifier.width(80.dp), 16, 250)
                    }
                    AsyncImage(
                        model = images[page], // Uri를 모델로 사용
                        contentDescription = "Memory Image",
                        modifier = Modifier
                            .fillMaxSize()
                            .background(color = White),
                        alignment = Alignment.Center,
                        contentScale = ContentScale.Crop
                    )
                    if (memoryId == 0) {
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
                                            if(!isGallery){
                                                isGallery = true
                                                launcher.launch("image/*") // 이미지 선택
                                            }
                                        }

                                        .align(Alignment.BottomEnd) // Box 내에서 우측 하단에 배치
                                )


                            }
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
                            if(!isGallery){
                                isGallery = true
                                launcher.launch("image/*") // 이미지 선택
                            }
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
                            text = "(최소 1장 최대 3장)",
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
                    value = memoryUIState.memoryTitle,
                    onValueChange = {
                        if (it.length <= maxTitleLength) memoryViewModel.memoryTitleUpdate(
                            it
                        )
//                        else if(containsEmoji(it)){
//                            Toast.makeText(context, "이모지 포함 불가", Toast.LENGTH_SHORT).show()
//                        }
                    },
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
                            color = PointPink,
                            style = AchuTheme.typography.regular14,
                            modifier = Modifier.padding(end = 16.dp)
                        )
                    },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Next),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            contentFocusRequester.requestFocus()
                        }
                    )
                )

//                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "${memoryUIState.memoryContent.text.length}/$maxContentLength",
                    color = PointPink,
                    style = AchuTheme.typography.regular14,
                    textAlign = TextAlign.End,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 4.dp, end = 4.dp)
                        .align(alignment = Alignment.End)

                )



                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1.0f)
                ) {
                    OutlinedTextField(
                        value = memoryUIState.memoryContent,
                        onValueChange = { newText ->
                            val lineCount = newText.text.count { it == '\n' } + 1
                            if (lineCount <= 6 && newText.text.length <= maxContentLength) {
                                memoryViewModel.memoryContentUpdate(newText)
                            }
//                            else if(containsEmoji(newText.text)){
//                                Toast.makeText(context, "이모지 포함 불가", Toast.LENGTH_SHORT).show()
//                            }
                        },
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.TopStart)
                            .focusRequester(contentFocusRequester),
                        placeholder = {
                            Text(
                                if(memoryId <= 0)"${getProductWithParticle(productName)} 함께한 추억을 기록해보세요!\n(최대 6줄 입력가능)" else "",
                                color = FontGray
                            )
                        },
                        textStyle = TextStyle(color = Color.Black, fontSize = 16.sp),
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = PointPink,
                            unfocusedBorderColor = PointPink,
                            cursorColor = Color.Black
                        ),
                        maxLines = 5,
                        keyboardOptions = KeyboardOptions.Default.copy(),
                    )

                }
                Spacer(Modifier.height(24.dp))


                val coroutineScope = rememberCoroutineScope()
                var isClickable by remember { mutableStateOf(true) }

                PointPinkBtn(
                    buttonText = if (memoryUIState.selectedMemory.title == "") "작성 완료" else "수정완료",
                    onClick = {

                        if (!isClickable) return@PointPinkBtn

                        isClickable = false

                        coroutineScope.launch {
                            delay(2000)
                            isClickable = true
                        }

                        if (images.isEmpty()) {
                            Toast.makeText(context, "사진을 추가해주세요", Toast.LENGTH_SHORT).show()
                        } else if (memoryUIState.memoryTitle.trim() == "" || memoryUIState.memoryContent.text.trim() == "") {
                            Toast.makeText(context, "제목, 내용을 확인해 주세요", Toast.LENGTH_SHORT)
                                .show()

                        } else {
                            if (memoryId == 0) {
                                memoryViewModel.uploadMemory()
                            } else {
                                memoryViewModel.changeMemory()
                            }
                        }
                    }
                )

                Spacer(modifier = Modifier.height(40.dp))
            }
        }

        if (memoryUIState.loading) {
            LoadingScreen("추억 업로드 중...\n잠시만 기다려 주세요!")
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MemoryUploadScreenPreview() {
    AchuTheme {

        MemoryUploadScreen(
            onNavigateToMemoryDetail = { memoryId, babyId ->
            },
            memoryId = 0,
            babyId = 0,
            productName = "토끼인형"
        )
    }
}

