package com.ssafy.achu.ui.mypage.baby

import android.R.attr.text
import android.R.attr.type
import android.app.DatePickerDialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.LoadingImg
import com.ssafy.achu.core.LoadingScreen
import com.ssafy.achu.core.components.BasicDeleteTopAppBar
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.components.PointPinkLineBtn
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.components.dialog.BasicDialog
import com.ssafy.achu.core.components.textfield.ClearTextField
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.BabyBlue
import com.ssafy.achu.core.theme.BabyYellow
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.compressImage
import com.ssafy.achu.core.util.uriToMultipart
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.util.Calendar

private const val TAG = "BabyDetailScreen_안주현"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun BabyDetailScreen(
    babyViewModel: BabyViewModel = viewModel(),
    viewModel: ActivityViewModel,
    babyId: Int
) {

    val babyUiState by babyViewModel.babyUiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val pointColor: Color = PointPink
    var showDeleteDialog by remember { mutableStateOf(false) }
    var isClickable by remember { mutableStateOf(true) }
    val context = LocalContext.current
    var isCalendar by remember { mutableStateOf(false) }



    LaunchedEffect(Unit) {
        delay(500) // 500ms (원하시는 시간으로 조정)
        isClickable = true


    }

    LaunchedEffect(babyViewModel.isChangedToast) {
        babyViewModel.isChangedToast.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }

    }

    if (babyId > 0) {
        babyViewModel.getBaby(babyId)
    }


    val type = if (babyUiState.selectedBaby == null) "등록" else "수정"
    var selectedGender by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(babyUiState.selectedBaby) {
        selectedGender = if (type == "등록") null else babyUiState.selectedBaby?.gender
    }
    var isGallery by remember { mutableStateOf(false) }
    val titleText = if (type == "등록") "아이 정보 관리" else "${babyUiState.selectedBaby!!.nickname} 정보"
    val profileBtnText = if (type == "등록") "프로필 사진 등록하기" else "프로필 사진 수정하기"
    val nicknameText =
        if (type == "등록" && babyUiState.babyNickname == "") babyUiState.resisterNickname
        else if (babyUiState.babyNickname != "") babyUiState.babyNickname
        else babyUiState.selectedBaby!!.nickname

    var imgUrl by remember { mutableStateOf("") }
    if (type != "등록") {
        imgUrl = babyUiState.selectedBaby?.imgUrl ?: ""
    }


    var showNickNameUpdateDialog by remember { mutableStateOf(false) }
    var dateList: List<Int>
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher


    fun showDatePicker(defaultDate: String) {
        Log.d(TAG, "showDatePicker: 실행합니다")

        val calendar = Calendar.getInstance()

        if (defaultDate.isNotBlank()) {
            try {
                val dateParts = defaultDate.split("-")
                val year = dateParts[0].toInt()
                val month = dateParts[1].toInt() - 1  // Calendar.MONTH는 0부터 시작
                val day = dateParts[2].toInt()

                calendar.set(year, month, day)  // 지정된 날짜 설정
            } catch (e: Exception) {
                Log.e(TAG, "날짜 변환 오류: $e")
            }


        }

        val datePickerDialog = DatePickerDialog(
            context,
            { _, selectedYear, selectedMonth, selectedDay ->
                dateList = listOf(selectedYear, selectedMonth + 1, selectedDay)  // 월 보정
                babyViewModel.updateBabyBirth(dateList)
                if (babyUiState.selectedBaby != null) {
                    babyViewModel.changeBabyBirth()
                }
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        val minSelectableDate = Calendar.getInstance().apply {
            add(Calendar.YEAR, -13)
            add(Calendar.DAY_OF_YEAR, 1) // 당일 생일은 제외하고 다음날부터 가능하게
        }
        datePickerDialog.datePicker.minDate = minSelectableDate.timeInMillis

        val maxSelectableDate = Calendar.getInstance().apply {
            add(Calendar.YEAR, 1) // 현재로부터 1년 뒤까지 선택 가능
        }
        datePickerDialog.datePicker.maxDate = maxSelectableDate.timeInMillis

        datePickerDialog.setOnDismissListener {
            isClickable = false
        }


        datePickerDialog.show()

    }


    LaunchedEffect(Unit) {
        babyViewModel.isChanged.collectLatest { isChanged ->
            if (isChanged == "실패") {
                Toast.makeText(context, babyUiState.toastString, Toast.LENGTH_SHORT).show()
                babyViewModel.clearIsChanged()
            } else {
                viewModel.getBabyList()
                if (isChanged == "등록성공" && babyId == -1) {
                    Log.d(TAG, "BabyDetailScreen:여기로와라")
                    delay(500)
                    Toast.makeText(context, babyUiState.toastString, Toast.LENGTH_SHORT).show()


                    backPressedDispatcher?.onBackPressed()
                } else if (isChanged == "삭제") {
                    backPressedDispatcher?.onBackPressed()
                    babyViewModel.clearIsChanged()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        babyViewModel.isSelectedBabyChanged.collectLatest { isChanged ->
            if (uiState.selectedBaby != null) {
                if (uiState.selectedBaby!!.id == babyUiState.selectedBaby!!.id) {
                    viewModel.updateSelectedBaby(babyUiState.selectedBaby!!)
                }
            }

        }
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            isGallery = false
            uri?.let {
                val multipartFile = uriToMultipart(context, it, "profileImage")
                if (multipartFile != null) {
                    if (type == "등록") {
                        imgUrl = it.toString()
                        babyViewModel.updateBabyPhoto(multipartFile)
                    } else {
                        babyViewModel.updateBabyProfile(multipartFile)
                    }
                }
            }
        }
    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .navigationBarsPadding()

    ) {
        Column {

            if (babyId == -1) {
                Box(modifier = Modifier.padding(horizontal = 24.dp)) {
                    BasicTopAppBar(
                        title = "아이등록",
                        onBackClick = {
                            backPressedDispatcher?.onBackPressed()
                        },
                        false
                    )
                }
            } else if (type == "등록") {
                BasicTopAppBar(
                    title = titleText,
                    onBackClick = {
                        backPressedDispatcher?.onBackPressed()
                    }
                )
            } else {
                BasicDeleteTopAppBar(
                    title = titleText,
                    onBackClick = {
                        backPressedDispatcher?.onBackPressed()
                    },
                    onDeleteClick = {
                        if (uiState.babyList.size > 1) {
                            showDeleteDialog = true
                        } else {
                            Toast.makeText(
                                context,
                                "이용을 위해 한명 이상의 아이 정보가 필요합니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                )
            }


            Column(
                modifier = Modifier
                    .fillMaxSize() // 전체 화면을 차지하도록 설정
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally // 가로 중앙 정렬

            ) {

                Box(
                    modifier = Modifier
                        .size(150.dp) // 크기 지정
                        .shadow(elevation = 2.dp, shape = CircleShape)
                        .background(color = White) // 그림자 적용
                        .clip(CircleShape)
                        .clickable {
                            if (!isGallery) {
                                isGallery = true
                                launcher.launch("image/*") // 이미지 선택
                            }
                        }, // 원형 이미지 적용
                    contentAlignment = Alignment.Center // 내부 컨텐츠 중앙 정렬
                ) {

                    LoadingImg("프로필\n업로드중..", Modifier.fillMaxSize())

                    if (type == "등록" && imgUrl.isEmpty()) {
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
                        Box(
                            modifier = Modifier
                                .size(150.dp)
                                .border(1.5.dp, PointPink, CircleShape),
                            contentAlignment = Alignment.Center // 내부 컨텐츠 중앙 정렬
                        ) {


                            // URL이 비어 있으면 기본 이미지 리소스를 사용하고, 그렇지 않으면 네트워크 이미지를 로드합니다.
                            if (imgUrl.isEmpty()) {
                                // 기본 이미지를 painter로 설정
                                Image(
                                    painter = painterResource(
                                        id =
                                            if (babyUiState.selectedBaby!!.gender == "MALE") R.drawable.img_profile_baby_boy
                                            else R.drawable.img_profile_baby_girl
                                    ),
                                    contentDescription = "Profile",
                                    modifier = Modifier
                                        .size(142.dp)
                                        .clip(CircleShape)
                                        .background(
                                            if (babyUiState.selectedBaby!!.gender == "MALE") BabyBlue
                                            else BabyYellow
                                        ), // Box 크기에 맞추기
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                // URL을 통해 이미지를 로드
                                AsyncImage(
                                    model = imgUrl,
                                    contentDescription = "Profile",
                                    modifier = Modifier
                                        .size(142.dp)
                                        .clip(CircleShape), // Box 크기에 맞추기
                                    contentScale = ContentScale.Crop,
                                    error = painterResource(R.drawable.img_baby_profile)
                                )

                            }


                        }
                    }

                }


                Spacer(modifier = Modifier.height(8.dp))

                SmallLineBtn(profileBtnText, pointColor, onClick = {
                    if (!isGallery) {
                        isGallery = true
                        launcher.launch("image/*") // 이미지 선택
                    }
                })

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = if (nicknameText == "") "닉네임" else nicknameText,
                        style = AchuTheme.typography.bold24,
                        fontSize = 28.sp
                    )

                    Image(
                        painter = painterResource(id = R.drawable.ic_write),
                        contentDescription = "Arrow",
                        modifier = Modifier
                            .height(20.dp)
                            .width(20.dp)
                            .clickable {
                                showNickNameUpdateDialog = true
                            },
                        colorFilter = ColorFilter.tint(pointColor) // 색을 빨간색으로 변경

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
                    ClearTextField(
                        value = babyUiState.babyBirth.joinToString("-") {
                            String.format(
                                "%02d",
                                it
                            )
                        },
                        onValueChange = {},
                        pointColor = pointColor,
                        modifier = Modifier
                            .fillMaxWidth(),
                        icon = R.drawable.ic_calendar,
                        onIconClick = {
                            if (!isCalendar) {
                                isClickable = true
                                showDatePicker("")
                            }
                        },
                        redOnly = true
                    )
                } else {
                    ClearTextField(
                        value = babyUiState.selectedBaby!!.birth,
                        onValueChange = {},
                        pointColor = PointPink,
                        modifier = Modifier
                            .fillMaxWidth(),
                        icon = R.drawable.ic_calendar,
                        onIconClick = {
                            if (!isCalendar) {
                                isClickable = true
                                showDatePicker(babyUiState.selectedBaby!!.birth)
                            }
                        },
                        redOnly = true
                    )
                }


                Spacer(modifier = Modifier.height(24.dp))


                Text(
                    text = "성별",
                    style = AchuTheme.typography.semiBold18,
                    modifier = Modifier.align(Alignment.Start)

                )

                Spacer(modifier = Modifier.height(8.dp))


                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Start,
                ) {

                    PointPinkLineBtn(
                        buttonText = "남자",
                        isSelected = selectedGender == "MALE"
                    ) {
                        selectedGender = "MALE"
                        babyViewModel.updateBabyGender("MALE")
                        if (type != "등록") {
                            babyViewModel.changeBabyGender()
                        }
                    }

                    Spacer(modifier = Modifier.width(8.dp))

                    PointPinkLineBtn(
                        buttonText = "여자",
                        isSelected = selectedGender == "FEMALE"
                    ) {
                        selectedGender = "FEMALE"
                        babyViewModel.updateBabyGender("FEMALE")
                        if (type != "등록") {
                            babyViewModel.changeBabyGender()
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                if (type == "등록" && babyUiState.isButtonAble) {
                    PointPinkBtn("등록하기", onClick = {
                        if (uiState.babyList.size >= 15) {
                            Toast.makeText(context, "아이는 최대 15명까지 등록 가능합니다.", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            if (nicknameText == "" || selectedGender == null || babyUiState.babyBirth.isEmpty()) {
                                Toast.makeText(context, "모든 정보를 입력해주세요", Toast.LENGTH_SHORT).show()
                            } else {
                                babyViewModel.registerBaby()
                            }
                        }
                    }, enable = babyUiState.isButtonAble)
                }
                Spacer(modifier = Modifier.height(40.dp))


            }

        }
    }

    if (showNickNameUpdateDialog) {
        if (type == "등록") {

            BabyNicknameDialog(
                onDismiss = {
                    showNickNameUpdateDialog = false
                    babyViewModel.updateBabyNickname(babyNicknameInput = "")
                },
                onConfirm = {
                    if (babyViewModel.confirmNickname()) {
                        showNickNameUpdateDialog = false
                        babyViewModel.updateResisterNickname(babyUiState.babyNickname)
                        babyViewModel.updateBabyNickname(babyNicknameInput = "")
                    }
                },
                onValueChange = {
                    if (it.length > 6) {
                        Toast.makeText(context, "닉네임은 6글자 이하만 가능합니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        babyViewModel.updateBabyNickname(babyNicknameInput = it)
                    }
                },
                PointPink,
                type = "등록",
                viewModel = babyViewModel,
            )
        } else {
            BabyNicknameDialog(
                onDismiss = {
                    showNickNameUpdateDialog = false
                    babyViewModel.updateBabyNickname(babyNicknameInput = "")
                },
                onConfirm = {
                    babyViewModel.changeBabyNickname()
                    showNickNameUpdateDialog = false
                    babyViewModel.updateBabyNickname(babyNicknameInput = "")
                },
                onValueChange = {
                    if (it.length > 6) {
                        Toast.makeText(context, "닉네임은 6글자 이하만 가능합니다.", Toast.LENGTH_SHORT).show()
                    } else {
                        babyViewModel.updateBabyNickname(babyNicknameInput = it)
                    }
                },
                PointPink,
                type = "수정",
                viewModel = babyViewModel
            )
        }
    }

    if (showDeleteDialog) {
        BasicDialog(
            img = painterResource(id = R.drawable.img_crying_face),
            text = "해당 아이를 삭제하시겠습니까?",
            onConfirm = {
                babyViewModel.deleteBaby(babyUiState.selectedBaby!!.id)
                showDeleteDialog = false
            },
            onDismiss = {
                showDeleteDialog = false
            }
        )
    }

    if (!babyUiState.isButtonAble) {
        LoadingScreen("아이등록중!\n잠시만 기다려주세요!")
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BabyDetailScreenPreview() {
    AchuTheme {
        BabyDetailScreen(
            viewModel = viewModel(),
            babyId = -1
        )
    }

}