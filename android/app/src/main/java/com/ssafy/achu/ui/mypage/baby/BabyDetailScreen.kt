package com.ssafy.achu.ui.mypage.baby

import android.app.DatePickerDialog
import android.content.Context
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.Toast
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
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.PointPinkBtn
import com.ssafy.achu.core.components.PointPinkLineBtn
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.components.textfield.ClearTextField
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
    var pointColor:Color = PointPink


    if (babyId != 0) {
        babyViewModel.getBaby(babyId)
    }


    val type = if (babyUiState.selectedBaby == null) "등록" else "수정"
    var selectedGender by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(babyUiState.selectedBaby) {
        selectedGender = if (type == "등록") null else babyUiState.selectedBaby?.gender
    }
    val titleText = if (type == "등록") "아이 정보 관리" else "${babyUiState.selectedBaby!!.nickname} 정보"
    val profileBtnText = if (type == "등록") "프로필 사진 등록하기" else "프로필 사진 수정하기"
    val nicknameText =
        if (type == "등록" && babyUiState.babyNickname == "") "닉네임"
        else if (babyUiState.babyNickname != "") babyUiState.babyNickname
        else babyUiState.selectedBaby!!.nickname

    var imgUrl by remember { mutableStateOf("") }
    if (type != "등록") {
        imgUrl = babyUiState.selectedBaby?.imgUrl ?: ""
    }

    var multipartFile: MultipartBody.Part? = null

    var showNickNameUpdateDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current
    var dateList: List<Int>

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

        datePickerDialog.show()
    }

    LaunchedEffect(Unit) {
        babyViewModel.isChanged.collectLatest { isChanged ->
            viewModel.getBabyList()
            Toast.makeText(context, babyUiState.toastString, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        babyViewModel.isSelectedBabyChanged.collectLatest { isChanged ->
            if (uiState.selectedBaby != null){
                if (uiState.selectedBaby!!.id == babyUiState.selectedBaby!!.id) {
                    viewModel.updateSelectedBaby(babyUiState.selectedBaby!!)
                }
            }

        }
    }


    fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part? {
        val contentResolver = context.contentResolver
        val inputStream: InputStream? = contentResolver.openInputStream(uri)

        inputStream?.let { input ->
            val file = File(context.cacheDir, "upload_image.jpg") // 임시 파일 생성
            val outputStream = FileOutputStream(file)
            input.copyTo(outputStream)
            outputStream.close()
            input.close()

            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            return MultipartBody.Part.createFormData("profileImage", file.name, requestFile)
        }
        return null
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) {
                imgUrl = uri.toString()
                multipartFile = uriToMultipart(context, uri)
                if (multipartFile != null) {
                    if (type != "등록") {
                        babyViewModel.updateBabyProfile(multipartFile!!)
                    }

                    babyViewModel.updateBabyPhoto(multipartFile!!)
                } else {
                    Toast.makeText(context, "이미지 변환 실패", Toast.LENGTH_SHORT).show()
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
                        .shadow(elevation = 4.dp, shape = CircleShape) // 그림자 적용
                        .clip(CircleShape), // 원형 이미지 적용
                    contentAlignment = Alignment.Center // 내부 컨텐츠 중앙 정렬
                ) {
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
                                .border(1.5.dp, PointPink, CircleShape) // 성별에 맞는 색상으로 원형 띠 적용
                                .background(color = Color.LightGray),
                            contentAlignment = Alignment.Center // 내부 컨텐츠 중앙 정렬
                        ) {


                            // URL이 비어 있으면 기본 이미지 리소스를 사용하고, 그렇지 않으면 네트워크 이미지를 로드합니다.
                            if (imgUrl.isNullOrEmpty()) {
                                // 기본 이미지를 painter로 설정
                                Image(
                                    painter = painterResource(id = R.drawable.img_baby_profile),
                                    contentDescription = "Profile",
                                    modifier = Modifier
                                        .size(142.dp)
                                        .clip(CircleShape), // Box 크기에 맞추기
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
                    launcher.launch("image/*")
                })

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
                            Log.d(TAG, "BabyDetailScreen: 클릭은된다")
                            showDatePicker("")
                        }
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
                            Log.d(TAG, "BabyDetailScreen: 클릭하냐고")
                            showDatePicker(babyUiState.selectedBaby!!.birth)
                        }
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
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    PointPinkLineBtn(
                        buttonText = "남자",
                        isSelected = selectedGender == "MALE"
                    ) {
                        selectedGender = if (selectedGender == "FEMALE") null else "MALE"
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
                        selectedGender = if (selectedGender == "MALE") null else "FEMALE"
                        babyViewModel.updateBabyGender("FEMALE")
                        if (type != "등록") {
                            babyViewModel.changeBabyGender()
                        }
                    }
                }

                Spacer(modifier = Modifier.weight(1f))
                if (type == "등록") {
                    PointPinkBtn("등록하기", onClick = {
                        babyViewModel.registerBaby()
                    })
                } else {
                    PointPinkBtn("아이삭제", onClick = {
                        babyViewModel.deleteBaby(babyUiState.selectedBaby!!.id)

                    })
                }
                Spacer(modifier = Modifier.height(40.dp))


            }

        }
    }

    if (showNickNameUpdateDialog) {
        if (type == "등록") {
            BabyNicknameDialog(
                onDismiss = { showNickNameUpdateDialog = false },
                onConfirm = {
                    if (babyUiState.isCorrectNickname) {
                        showNickNameUpdateDialog = false
                    }
                },
                PointPink,
                type = "등록",
                viewModel = babyViewModel
            )
        } else {
            BabyNicknameDialog(
                onDismiss = { showNickNameUpdateDialog = false },
                onConfirm = {
                    babyViewModel.changeBabyNickname()
                    showNickNameUpdateDialog = false
                },
                PointPink,
                type = "수정",
                viewModel = babyViewModel
            )
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun BabyDetailScreenPreview() {
    AchuTheme {
        BabyDetailScreen(viewModel = viewModel(),
            babyId = 0)
    }

}