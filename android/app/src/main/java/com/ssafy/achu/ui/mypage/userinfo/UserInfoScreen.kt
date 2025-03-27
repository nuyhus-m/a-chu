package com.ssafy.achu.ui.mypage.userinfo

import PhoneNumberTextField
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.ssafy.achu.core.components.BasicDialog
import com.ssafy.achu.core.components.textfield.BasicTextField
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.PointBlueButton
import com.ssafy.achu.core.components.PointBlueFlexibleBtn
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlack
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.auth.UserInfoResponse

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun UserInfoScreen(
    viewModel: UserInfoViewModel = viewModel(),
) {

    var showNickNameUpdateDialog by remember { mutableStateOf(false) }
    var showPasswordUpdateDialog by remember { mutableStateOf(false) }
    var logoutDialog by remember { mutableStateOf(false) }
    var deleteUserDialog by remember { mutableStateOf(false) }
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current


    val user = UserInfoResponse(
        imageUrl = "https://loremflickr.com/300/300/mom",
        nickname = "재영맘",
        userId = "achutest1",
        phoneNumber = "010-1234-4568",
    )
    var img by remember { mutableStateOf(user


        .imageUrl) }
    

    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { uri ->
            if (uri != null) { // 이미지가 선택되었을 때만 처리
               img = uri.toString()
                Toast.makeText(context, "프로필 수정완료", Toast.LENGTH_SHORT).show()
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
                title = "내 정보 수정",
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
                        .shadow(elevation = 8.dp, shape = CircleShape) // 그림자 적용
                        .clip(CircleShape) // 원형 이미지 적용
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.img_profile_test),
                        contentDescription = "Profile",
                        modifier = Modifier.fillMaxSize(), // Box 크기에 맞추기
                        contentScale = ContentScale.Crop
                    )

                    if (!user.imageUrl.isNullOrEmpty()) {
                        AsyncImage(
                            model = img,
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
                        text = user.nickname, style = AchuTheme.typography.bold24,
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
                                showNickNameUpdateDialog = true
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
                    value = user.userId,
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
                        value = uiState.phoneNumber,
                        placeholder = user.phoneNumber,
                        onValueChange = {viewModel.updatePhoneNumber(it)},
                        pointColor = PointBlue,
                        modifier = Modifier.weight(1f),
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    PointBlueFlexibleBtn("인증", onClick = {

                    })
                }

                Spacer(modifier = Modifier.height(24.dp))

                PointBlueButton("비밀번호 수정", onClick = {
                    showPasswordUpdateDialog = true
                })

                Spacer(modifier = Modifier.height(48.dp))

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
                            logoutDialog = true
                        }
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        text = "화원탈퇴",
                        style = AchuTheme.typography.semiBold14PointBlue.copy(
                            textDecoration = TextDecoration.Underline
                        ),
                        modifier = Modifier.clickable {
                            deleteUserDialog = true
                        }

                    )
                }


            }
        }
    }

    if (showNickNameUpdateDialog) {
        NicknameUpdateDialog(
            onDismiss = { showNickNameUpdateDialog = false },
            onConfirm = { showNickNameUpdateDialog = false },
            PointBlue
        )
    }
    if (showPasswordUpdateDialog) {
        PasswordUpdateDialog(
            onDismiss = {

                showPasswordUpdateDialog = false
            },
            onConfirm = { showPasswordUpdateDialog = false }
        )
    }

    if (logoutDialog) {
        BasicDialog(
            text = "로그아웃 하시겠습니까?",
            onDismiss = { logoutDialog = false },
            onConfirm = { logoutDialog = false }
        )
    }

    if (deleteUserDialog) {
        BasicDialog(
            img = painterResource(id = R.drawable.img_crying_face),
            "A - Chu",
            "와 함께한",
            text = "모든 추억이 삭제됩니다.\n정말 탈퇴하시겠습니까?",
            onDismiss = { deleteUserDialog = false },
            onConfirm = { deleteUserDialog = false }
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun UserInfoScreenPreview() {
    AchuTheme {
        UserInfoScreen()
    }
}