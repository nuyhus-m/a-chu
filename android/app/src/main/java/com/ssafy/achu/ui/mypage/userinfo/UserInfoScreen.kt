package com.ssafy.achu.ui.mypage.userinfo

import PhoneNumberTextField
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PointBlueFlexibleBtn
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.components.dialog.BasicDialog
import com.ssafy.achu.core.components.dialog.PhoneVerificationDialog
import com.ssafy.achu.core.components.textfield.BasicTextField
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatPhoneNumber
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.ByteArrayOutputStream

private const val TAG = "UserInfoScreen 안주현"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserInfoScreen(
    viewModel: ActivityViewModel,
    userInfoViewModel: UserInfoViewModel = viewModel(),
) {

    val userInfoUiState by userInfoViewModel.uiState.collectAsState()
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        userInfoViewModel.isChanged.collectLatest { isChanged ->
            if (isChanged) {
                viewModel.getUserinfo()
                Toast.makeText(context, userInfoUiState.toastMessage, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, userInfoUiState.toastMessage, Toast.LENGTH_SHORT).show()

            }
        }
    }





    fun compressImage(uri: Uri, context: Context): ByteArray? {
        val contentResolver = context.contentResolver

        // 파일의 InputStream을 열고 Bitmap으로 변환
        val inputStream = contentResolver.openInputStream(uri) ?: return null
        val bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream.close()

        // 이미지 압축: 품질을 80%로 설정 (0 ~ 100)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 30, byteArrayOutputStream)

        // 압축된 이미지의 바이트 배열 반환
        return byteArrayOutputStream.toByteArray()
    }

    fun uriToMultipart(context: Context, uri: Uri): MultipartBody.Part? {
        val contentResolver = context.contentResolver

        // 실제 파일의 MIME 타입 가져오기
        val mimeType = contentResolver.getType(uri) ?: return null

        // MIME 타입에 맞는 확장자 추출
        val extension = MimeTypeMap.getSingleton().getExtensionFromMimeType(mimeType) ?: "jpg"

        // 이미지 압축
        val byteArray = compressImage(uri, context) ?: return null

        if (byteArray.isEmpty()) {
            Log.e(TAG, "⚠️ 변환된 바이트 배열이 비어있음! 이미지 손실 가능성 있음!")
            return null
        }

        Log.d(TAG, "✅ 변환된 바이트 배열 크기: ${byteArray.size}, MIME: $mimeType, 확장자: $extension")

        // 파일명을 실제 MIME 타입에 맞게 설정
        val fileName = "upload_${System.currentTimeMillis()}.$extension"

        // 올바른 MIME 타입 적용
        val requestBody = byteArray.toRequestBody(mimeType.toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("profileImage", fileName, requestBody)
    }

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            uri?.let {
                val multipartFile = uriToMultipart(context, it)
                if (multipartFile != null) {
                    userInfoViewModel.changeProfile(multipartFile)
                }
            }
        }
    )

    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
            .navigationBarsPadding()

    ) {

        Column {
            BasicTopAppBar(
                title = "내 정보 수정",
                onBackClick = {
                    backPressedDispatcher?.onBackPressed()
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
                        .shadow(elevation = 8.dp, shape = CircleShape) // 그림자 적용
                        .clip(CircleShape) // 원형 이미지 적용
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_profile_test),
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                        contentScale = ContentScale.Crop
                    )

                    if (!uiState.user!!.profileImageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = uiState.user!!.profileImageUrl,
                            contentDescription = "Profile",
                            modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                            contentScale = ContentScale.Crop
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                SmallLineBtn("프로필 수정하기", PointBlue, onClick = {
                    launcher.launch("image/*")
                })

                Spacer(modifier = Modifier.height(16.dp))

                Row(verticalAlignment = Alignment.Bottom) {
                    Spacer(modifier = Modifier.width(20.dp))
                    Text(
                        text = uiState.user!!.nickname, style = AchuTheme.typography.bold24,
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
                                userInfoViewModel.updateShowNickNameUpdateDialog(true)
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
                    value = uiState.user!!.username,
                    onValueChange = {},
                    placeholder = "아이디",
                    placeholderColor = FontBlack,
                    borderColor = PointBlue,
                    readOnly = true
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "전화번호",
                    style = AchuTheme.typography.semiBold18,
                    modifier = Modifier.align(Alignment.Start)

                )

                Spacer(modifier = Modifier.height(8.dp))

                Row {
                    PhoneNumberTextField(
                        value = formatPhoneNumber(userInfoUiState.phoneNumber),
                        placeholder = formatPhoneNumber(uiState.user!!.phoneNumber),
                        onValueChange = { userInfoViewModel.updatePhoneNumber(it) },
                        pointColor = PointBlue,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    PointBlueFlexibleBtn("인증", onClick = {
                        userInfoViewModel.sendPhoneAuth()

                    })
                }

                Spacer(modifier = Modifier.height(24.dp))

                PointBlueButton("비밀번호 수정", onClick = {
                    userInfoViewModel.updateShowPasswordUpdateDialog(true)
                })

                Spacer(modifier = Modifier.height(48.dp))

                //이거 로그아웃은 프론트 에서 그냥 토큰 날리면 되지않나?
                //회원탈퇴는 아직 안나옴
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
                            userInfoViewModel.updateLogoutDialog(true)
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "화원탈퇴",
                        style = AchuTheme.typography.semiBold14PointBlue.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable {
                            userInfoViewModel.updateDeleteUserDialog(true)
                        }

                    )
                }


            }
        }
    }

    if (userInfoUiState.showNickNameUpdateDialog) {
        NicknameUpdateDialog(
            onDismiss = {
                userInfoViewModel.updateShowNickNameUpdateDialog(false)
            },
            onConfirm = {
                userInfoViewModel.changeNickname()
                userInfoViewModel.updateShowNickNameUpdateDialog(false)
                Toast.makeText(context, "닉네임 수정완료", Toast.LENGTH_SHORT).show()
                viewModel.getUserinfo()
            },
            PointBlue, viewModel = userInfoViewModel
        )
    }
    if (userInfoUiState.showPasswordUpdateDialog) {
        PasswordUpdateDialog(
            onDismiss = { userInfoViewModel.updateShowPasswordUpdateDialog(false) },
            onConfirm = {
                userInfoViewModel.updateShowPasswordUpdateDialog(false)
                Toast.makeText(context, "비밀번호 수정완료", Toast.LENGTH_SHORT).show()
            }, viewModel = userInfoViewModel
        )
    }

    if (userInfoUiState.logoutDialog) {
        BasicDialog(
            text = "로그아웃 하시겠습니까?",
            onDismiss = { userInfoViewModel.updateLogoutDialog(false) },
            onConfirm = { userInfoViewModel.updateLogoutDialog(false) }
        )
    }

    if (userInfoUiState.deleteUserDialog) {
        BasicDialog(
            img = painterResource(id = R.drawable.img_crying_face),
            "A - Chu",
            "와 함께한",
            text = "모든 추억이 삭제됩니다.\n정말 탈퇴하시겠습니까?",
            onDismiss = { userInfoViewModel.updateDeleteUserDialog(false) },
            onConfirm = { userInfoViewModel.updateDeleteUserDialog(false) }
        )
    }

    if (userInfoUiState.verifyPhoneNumberDialog) {
        PhoneVerificationDialog(
            value = userInfoUiState.verifyNumber,
            onValueChange = { userInfoViewModel.updateVerifyNumber(it) },
            phoneNumber = formatPhoneNumber(userInfoUiState.phoneNumber),
            onDismiss = { userInfoViewModel.updateLogoutDialog(false) },
            onConfirm = { userInfoViewModel.updateLogoutDialog(false)
                        userInfoViewModel.checkPhoneAuth()},
            PointBlue
        )

    }
}




@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun UserInfoScreenPreview() {
    AchuTheme {
        UserInfoScreen(
            viewModel = viewModel()
        )
    }
}