package com.ssafy.achu.ui.mypage.userinfo

import PhoneNumberTextField
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.ssafy.achu.core.ApplicationClass.Companion.sharedPreferencesUtil
import com.ssafy.achu.core.LoadingImg
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PointBlueFlexibleBtn
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.components.dialog.BasicDialog
import com.ssafy.achu.core.components.dialog.PhoneVerificationDialog
import com.ssafy.achu.core.components.textfield.BasicTextField
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.LightPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatPhoneNumber
import com.ssafy.achu.core.util.isImageValid
import com.ssafy.achu.core.util.uriToMultipart
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.AuthActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest

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
    var isGallery by remember { mutableStateOf(false) }


    LaunchedEffect(Unit) {
        userInfoViewModel.isChanged.collectLatest { isChanged ->
            if (isChanged) {
                viewModel.getUserinfo()
                userInfoViewModel.updateShowNickNameUpdateDialog(false)
                userInfoViewModel.updateNickname("")
                delay(1000)
                userInfoViewModel.updateIsProfileLoading(false)
                Toast.makeText(context, userInfoUiState.toastMessage, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, userInfoUiState.toastMessage, Toast.LENGTH_SHORT).show()
                userInfoViewModel.updateIsProfileLoading(false)
            }
        }
    }


    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            isGallery = false
            uri?.let {
                if (isImageValid(context, it)) {
                    val multipartFile = uriToMultipart(context, it, "profileImage")
                    if (multipartFile != null) {
                        userInfoViewModel.changeProfile(multipartFile)
                    }
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
                        .clip(CircleShape)
                        .background(LightPink) // 원형 이미지 적용
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_profile_basic2),
                        contentDescription = "Profile",
                        modifier = Modifier
                            .fillMaxSize()
                            .clickable {
                                if(!isGallery){
                                    isGallery = true
                                    launcher.launch("image/*") // 이미지 선택
                                }
                            }, // Box 크기에 맞추기
                        contentScale = ContentScale.Crop
                    )

                    if (!uiState.user!!.profileImageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = uiState.user!!.profileImageUrl,
                            contentDescription = "Profile",
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {
                                    if(!isGallery){
                                        isGallery = true
                                        launcher.launch("image/*") // 이미지 선택
                                    }
                                }, // Box 크기에 맞추기
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (userInfoUiState.isProfileLoading) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(White)
                                .clickable {
                                    if(!isGallery){
                                        isGallery = true
                                        launcher.launch("image/*") // 이미지 선택
                                    }
                                }, // Box 크기에 맞추기
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingImg(
                                "프로필\n업로드중...", modifier = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
                SmallLineBtn("프로필 수정하기", PointBlue, onClick = {
                    if(!isGallery){
                        isGallery = true
                        launcher.launch("image/*") // 이미지 선택
                    }
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
                        value = userInfoUiState.phoneNumber,
                        placeholder = formatPhoneNumber(uiState.user!!.phoneNumber),
                        onValueChange = { userInfoViewModel.updatePhoneNumber(it) },
                        pointColor = PointBlue,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    PointBlueFlexibleBtn("인증", onClick = {

                        if (userInfoUiState.phoneNumber.length >= 13) {
                            Log.d(TAG, "UserInfoScreen: ${userInfoUiState.phoneNumber.length}")
                            Toast.makeText(context, "전화번호를 확인해주세요", Toast.LENGTH_SHORT).show()
                        } else if (userInfoUiState.phoneNumber.replace(
                                "-",
                                ""
                            ) == uiState.user!!.phoneNumber || userInfoUiState.phoneNumber == ""
                        ) {
                            Toast.makeText(context, "전화번호 변경 후 인증 요청해주세요", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            userInfoViewModel.sendPhoneAuth()

                        }
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
                        text = "회원탈퇴",
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
                userInfoViewModel.updateNickname("")
            },
            onConfirm = {
                userInfoViewModel.confirmNickname(userInfoUiState.newNickname)
            },
            onValueChange = {
                if (it.length > 6) {
                    Toast.makeText(context, "6자 이내로 입력해주세요", Toast.LENGTH_SHORT).show()
                } else {
                    userInfoViewModel.updateNickname(it)
                }
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
            onDismiss = {
                userInfoViewModel.updateLogoutDialog(false)
            },
            onConfirm = {
                viewModel.deleteFcmToken()
                navigateToAuthActivity(context)
                sharedPreferencesUtil.clearTokensInfo()
            }
        )
    }

    if (userInfoUiState.deleteUserDialog) {
        BasicDialog(
            img = painterResource(id = R.drawable.img_crying_face),
            "A - Chu",
            "와 함께한",
            text = "모든 추억이 삭제됩니다.\n정말 탈퇴하시겠습니까?",
            onDismiss = { userInfoViewModel.updateDeleteUserDialog(false) },
            onConfirm = {
                sharedPreferencesUtil.clearTokensInfo()
                navigateToAuthActivity(context)
                viewModel.deleteFcmToken()
            }
        )
    }

    if (userInfoUiState.verifyPhoneNumberDialog) {
        PhoneVerificationDialog(
            value = userInfoUiState.verifyNumber,
            onValueChange = { userInfoViewModel.updateVerifyNumber(it) },
            phoneNumber = formatPhoneNumber(userInfoUiState.phoneNumber),
            onDismiss = { userInfoViewModel.updatePhoneNumberDialog(false) },
            onConfirm = {
                userInfoViewModel.updatePhoneNumberDialog(false)
                userInfoViewModel.checkPhoneAuth()
            },
            PointBlue
        )

    }
}

fun navigateToAuthActivity(context: Context) {
    val options = ActivityOptions.makeCustomAnimation(context, 0, 0)
    Intent(context, AuthActivity::class.java).apply {
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }.also { intent ->
        context.startActivity(intent, options.toBundle())
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