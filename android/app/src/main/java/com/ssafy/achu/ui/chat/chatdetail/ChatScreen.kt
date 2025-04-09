package com.ssafy.achu.ui.chat.chatdetail

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.components.dialog.BasicDialog
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightBlue
import com.ssafy.achu.core.theme.LightPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.Constants.TEXT
import com.ssafy.achu.core.util.formatChatRoomTime
import com.ssafy.achu.core.util.formatPrice
import com.ssafy.achu.data.database.SharedPreferencesUtil
import com.ssafy.achu.data.model.chat.Goods
import com.ssafy.achu.data.model.chat.Message
import com.ssafy.achu.data.model.chat.Partner
import com.ssafy.achu.ui.ActivityViewModel
import com.ssafy.achu.ui.chat.chatdetail.ChatViewModel.ChatStateHolder
import kotlinx.coroutines.flow.collectLatest
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel = viewModel(),
    activityViewModel: ActivityViewModel,
    roomId: Int,
    onBackClick: () -> Unit
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val activityUiState by activityViewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        if (roomId == -1) {
            viewModel.updateGoods(activityUiState.product)
            viewModel.updatePartner(activityUiState.product.seller)
            viewModel.checkChatRoomExistence()
        } else {
            viewModel.setMessages()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }


    DisposableEffect(Unit) {
        onDispose {
            SharedPreferencesUtil(context).deleteRoomId() // ✅ context 안전하게 사용
        }
    }
    val listState = rememberLazyListState()

    LaunchedEffect(uiState.messages.size) {
        if (uiState.messages.isNotEmpty()) {
            listState.animateScrollToItem(uiState.messages.size - 1)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomChatTopBar(
            partner = uiState.partner ?: Partner(
                id = 0,
                nickname = "",
                profileImageUrl = ""
            ),
            onBackClick = onBackClick,
            onLeaveClick = { /* 나가기 동작 */ }
        )
        uiState.goods?.let {
            ChatProduct(
                goods = it,
                isSeller = uiState.isSeller,
                isSold = uiState.isSold,
                onSoldClick = { viewModel.showSoldDialog(true) }
            )
        }

        // 채팅 메시지 목록
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            state = listState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(uiState.messages) { message ->
                when (message.type) {
                    TEXT -> ChatMessageItem(
                        message = message,
                        lastReadMessageId = uiState.lastReadMessageId,
                        userId = activityUiState.user!!.id,
                        partner = uiState.partner!!
                    )

                    else -> SystemMessage(message = message)
                }
            }
        }

        // 입력 필드
        ChatInputField(
            value = uiState.inputText,
            onValueChange = { viewModel.updateInputText(it) },
            onSendClick = {
                if (!uiState.hasChatRoom && uiState.isFirst) viewModel.createChatRoom()
                else viewModel.sendMessage()
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }

    if (uiState.isShowSoldDialog) {
        BasicDialog(
            text = "거래를 완료하시겠습니까?",
            onDismiss = { viewModel.showSoldDialog(false) },
            onConfirm = { viewModel.completeTrade() }
        )
    }
}


@Composable
fun ChatProduct(
    goods: Goods,
    isSeller: Boolean,
    isSold: Boolean,
    onSoldClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = goods.thumbnailImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
            )

            Column(
                modifier = Modifier
                    .weight(1.3f)
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = goods.title,
                    style = AchuTheme.typography.regular18
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (goods.price == 0L) stringResource(R.string.free)
                    else formatPrice(goods.price),
                    style = AchuTheme.typography.semiBold18.copy(color = if (goods.price == 0L) FontBlue else FontPink)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = if (isSold) stringResource(R.string.sold)
                    else stringResource(R.string.selling),
                    style = AchuTheme.typography.semiBold16.copy(color = PointPink)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
            if (isSeller) {
                SmallLineBtn(
                    buttonText = "거래 완료",
                    color = if (isSold) FontGray else PointPink,
                    onClick = onSoldClick,
                    enabled = !isSold
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
fun DateDivider(timestamp: Long) {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("yyyy년 MM월 dd일 EEEE", Locale.KOREA)
    val formattedDate = formatter.format(date)

    // 오늘 날짜인지 확인
    val todayFormatter = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA)
    val todayDate = todayFormatter.format(Date())
    val messageDate = todayFormatter.format(date)

    val displayText = if (messageDate == todayDate) {
        "오늘"
    } else {
        formattedDate
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = displayText,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = AchuTheme.typography.regular14.copy(color = FontGray)
        )
    }
}

@Composable
fun SystemMessage(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message.content,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            style = AchuTheme.typography.regular14.copy(color = FontGray)
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatMessageItem(message: Message, lastReadMessageId: Int, userId: Int, partner: Partner) {
    val isMine = message.senderId == userId
    val isUnread = message.id > lastReadMessageId

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isMine) Alignment.End else Alignment.Start
    ) {
        // 발신자 이름 (내가 보낸 메시지가 아닐 때만 표시)
        if (!isMine) {
            Text(
                text = partner.nickname,
                style = AchuTheme.typography.semiBold14PointBlue.copy(color = FontGray),
                modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = if (isMine) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 타임스탬프 (보낸 메시지면 왼쪽, 받은 메시지면 오른쪽)
            if (isMine) {
                MessageTimestamp(
                    isSent = true,
                    timestamp = formatChatRoomTime(message.timestamp),
                    isUnread = isUnread
                )
                Spacer(modifier = Modifier.width(4.dp))
            }

            // 메시지 말풍선
            Box(
                modifier = Modifier
                    .widthIn(max = 260.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 16.dp,
                            topEnd = 16.dp,
                            bottomStart = if (isMine) 16.dp else 0.dp,
                            bottomEnd = if (isMine) 0.dp else 16.dp
                        )
                    )
                    .background(
                        if (isMine) PointPink
                        else LightBlue
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = message.content,
                    color = if (isMine) Color.White else Color.Black,
                    style = AchuTheme.typography.regular16
                )
            }

            // 타임스탬프 (보낸 메시지면 오른쪽, 받은 메시지면 왼쪽)
            if (!isMine) {
                Spacer(modifier = Modifier.width(4.dp))
                MessageTimestamp(
                    isSent = false,
                    timestamp = formatChatRoomTime(message.timestamp),
                    isUnread = false
                )
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun MessageTimestamp(isSent: Boolean, timestamp: String, isUnread: Boolean) {
    Column(
        horizontalAlignment = if (isSent) Alignment.End else Alignment.Start
    ) {
        if (isUnread) {
            Text(
                text = "1",
                style = AchuTheme.typography.regular14.copy(color = FontGray),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text(
            text = timestamp,
            style = AchuTheme.typography.regular14.copy(color = FontGray),
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ChatInputField(
    value: String,
    onValueChange: (String) -> Unit,
    onSendClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
            .navigationBarsPadding()
            .background(color = White),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 텍스트 입력 필드
            Box(
                modifier = Modifier.weight(1f)
            ) {
                ChatTextField(
                    value = value,
                    onValueChange = onValueChange,
                    pointColor = PointPink
                )
            }

            Spacer(modifier = Modifier.width(8.dp))

            // 전송 버튼
            IconButton(
                onClick = { if (value.isNotEmpty()) onSendClick() },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(FontPink)
                    .size(48.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_send),
                    contentDescription = stringResource(R.string.send),
                    tint = Color.White
                )
            }
        }
    }
}

// 채팅 TextField
@Composable
fun ChatTextField(
    value: String,
    onValueChange: (String) -> Unit,
    pointColor: Color = PointBlue,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .fillMaxWidth(),
        textStyle = AchuTheme.typography.regular16,
        placeholder = {
            Text(
                text = stringResource(R.string.enter_message),
                style = AchuTheme.typography.regular16.copy(color = FontGray)
            )
        },
        shape = RoundedCornerShape(30.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = pointColor,
            unfocusedBorderColor = pointColor,
            cursorColor = Color.Black
        ),
//        trailingIcon = {
//            IconButton(onClick = { }) {
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_gallery),
//                    tint = FontGray,
//                    contentDescription = stringResource(R.string.add_img)
//                )
//            }
//        }
    )
}

@Composable
fun CustomChatTopBar(
    partner: Partner,
    onBackClick: () -> Unit,
    onLeaveClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(start = 8.dp, end = 8.dp, top = 68.dp, bottom = 16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 뒤로가기 버튼
        IconButton(onClick = onBackClick) {
            Icon(
                painter = painterResource(id = R.drawable.ic_back),
                contentDescription = stringResource(R.string.back),
                modifier = Modifier.size(28.dp)
            )
        }

        // 프로필 및 사용자 정보 섹션
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 프로필 이미지
            Box {
                Image(
                    painter = painterResource(id = R.drawable.img_profile_basic2),
                    contentDescription = stringResource(R.string.profile_img),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(LightPink),
                    contentScale = ContentScale.Crop
                )
                AsyncImage(
                    model = partner.profileImageUrl, // 여기에 실제 이미지 URL 입력
                    contentDescription = stringResource(R.string.profile_img),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            }

            Spacer(modifier = Modifier.width(8.dp))
            // 사용자 이름
            Text(
                text = partner.nickname,
                style = AchuTheme.typography.bold24
            )
        }

        // 나가기 버튼
        IconButton(
            onClick = onLeaveClick
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_leave),
                contentDescription = stringResource(R.string.leave),
                modifier = Modifier.size(28.dp)
            )
        }
    }
}

// 여기서부터 Preview 코드 시작
//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun ChatScreenPreview() {
//    AchuTheme {
//        ChatScreen(
//            onBackClick = { /* 뒤로가기 동작 */ }
//        )
//    }
//}

// 사용 예시 프리뷰
@Preview(showBackground = true)
@Composable
fun PreviewCustomChatTopBar() {
    AchuTheme {
        CustomChatTopBar(
            partner = Partner(
                id = 0,
                nickname = "홍길동",
                profileImageUrl = ""
            ),
            onBackClick = { /* 뒤로가기 동작 */ },
            onLeaveClick = { /* 나가기 동작 */ }
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun ChatProductPreview() {
//    AchuTheme {
//        ChatProduct()
//    }
//}

@Preview(showBackground = true)
@Composable
fun ChatInputFieldPreview() {
    AchuTheme {
        ChatInputField(
            value = "안녕하세요",
            onValueChange = {},
            onSendClick = {}
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DateDividerPreview() {
//    AchuTheme {
//        DateDivider(timestamp = System.currentTimeMillis())
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun SystemMessagePreview() {
//    AchuTheme {
//        SystemMessage(
//            message = ChatMessage(
//                id = "system_1",
//                content = "홍길동님이 나갔습니다.",
//                timestamp = System.currentTimeMillis(),
//                type = MessageType.SYSTEM
//            )
//        )
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun ChatMessagePreview_Sent() {
//    AchuTheme {
//        ChatMessageItem(
//            message = ChatMessage(
//                id = "msg_1",
//                content = "안녕하세요! 반갑습니다.",
//                timestamp = System.currentTimeMillis(),
//                type = MessageType.SENT
//            )
//        )
//    }
//}

//@Preview(showBackground = true)
//@Composable
//fun ChatMessagePreview_Received() {
//    AchuTheme {
//        ChatMessageItem(
//            message = ChatMessage(
//                id = "msg_2",
//                content = "네, 반갑습니다. 오늘 날씨가 정말 좋네요.",
//                timestamp = System.currentTimeMillis(),
//                type = MessageType.RECEIVED,
//                sender = "홍길동"
//            )
//        )
//    }
//}