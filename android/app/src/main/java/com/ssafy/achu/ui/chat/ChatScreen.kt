package com.ssafy.achu.ui.chat

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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.SmallLineBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightBlue
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink
import com.ssafy.achu.core.theme.White
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

// 메시지 타입 열거형
enum class MessageType {
    SENT, RECEIVED, SYSTEM, DATE
}

// 메시지 데이터 클래스
data class ChatMessage(
    val id: String,
    val content: String,
    val timestamp: Long,
    val type: MessageType,
    val sender: String = "" // 발신자 이름 (시스템 메시지에는 사용 안 함)
)

// 채팅 미리보기용 파라미터 제공자
class ChatMessagePreviewProvider : PreviewParameterProvider<ChatMessage> {
    override val values = sequenceOf(
        ChatMessage(
            id = "1",
            content = "안녕하세요! 좋은 하루 보내세요.",
            timestamp = System.currentTimeMillis(),
            type = MessageType.RECEIVED,
            sender = "홍길동"
        ),
        ChatMessage(
            id = "2",
            content = "네! 감사합니다. 좋은 하루 되세요.",
            timestamp = System.currentTimeMillis(),
            type = MessageType.SENT
        ),
        ChatMessage(
            id = "3",
            content = "홍길동님이 나갔습니다.",
            timestamp = System.currentTimeMillis(),
            type = MessageType.SYSTEM
        )
    )
}

@Composable
fun ChatScreen(onBackClick: () -> Unit = {}) {
    // 메시지 상태 관리
    var messageText by remember { mutableStateOf("") }

    // 샘플 데이터 준비 (실제로는 ViewModel에서 관리하는 것이 좋습니다)
    val calendar = Calendar.getInstance()
    val today = calendar.timeInMillis

    // 어제 날짜 설정
    calendar.add(Calendar.DAY_OF_YEAR, -1)
    val yesterday = calendar.timeInMillis

    var messages by remember {
        mutableStateOf(
            listOf(
                // 날짜 구분선 (어제)
                ChatMessage("1", "", yesterday, MessageType.DATE),

                // 어제 메시지들
                ChatMessage("2", "안녕하세요!", yesterday + 1000, MessageType.RECEIVED, "홍길동"),
                ChatMessage("3", "반갑습니다!", yesterday + 2000, MessageType.SENT),
                ChatMessage("4", "오늘 날씨가 좋네요.", yesterday + 3000, MessageType.RECEIVED, "홍길동"),
                ChatMessage("5", "네, 정말 좋은 날씨입니다.", yesterday + 4000, MessageType.SENT),

                // 시스템 메시지
                ChatMessage("6", "홍길동님이 나갔습니다.", yesterday + 5000, MessageType.SYSTEM),

                // 날짜 구분선 (오늘)
                ChatMessage("7", "", today, MessageType.DATE),

                // 오늘 메시지들
                ChatMessage("8", "다시 접속했습니다.", today + 1000, MessageType.RECEIVED, "홍길동"),
                ChatMessage("9", "어제 대화 이어서 할까요?", today + 2000, MessageType.RECEIVED, "홍길동"),
                ChatMessage("10", "좋아요!", today + 3000, MessageType.SENT)
            )
        )
    }

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // 메시지 추가 기능
    fun sendMessage() {
        if (messageText.isNotBlank()) {
            val currentTime = System.currentTimeMillis()

            // 날짜가 바뀌었는지 확인
            val lastMessage = messages.lastOrNull()
            val lastMessageDate = lastMessage?.let {
                val date = Date(it.timestamp)
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(date)
            }

            val currentDate =
                SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date(currentTime))

            // 날짜가 바뀌었으면 날짜 구분선 추가
            if (lastMessageDate != currentDate) {
                messages = messages + ChatMessage(
                    id = "date_${currentTime}",
                    content = "",
                    timestamp = currentTime,
                    type = MessageType.DATE
                )
            }

            // 새 메시지 추가
            val newMessage = ChatMessage(
                id = "msg_${currentTime}",
                content = messageText,
                timestamp = currentTime,
                type = MessageType.SENT
            )

            messages = messages + newMessage
            messageText = ""

            // 스크롤을 맨 아래로 이동
            coroutineScope.launch {
                listState.animateScrollToItem(messages.size - 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        CustomChatTopBar(
            onBackClick = onBackClick,
            onLeaveClick = { /* 나가기 동작 */ },
            userName = "덕윤맘"
        )
        ChatProduct()

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
            items(messages) { message ->
                when (message.type) {
                    MessageType.DATE -> DateDivider(timestamp = message.timestamp)
                    MessageType.SYSTEM -> SystemMessage(message = message)
                    else -> ChatMessageItem(message = message)
                }
            }
        }

        // 입력 필드
        ChatInputField(
            value = messageText,
            onValueChange = { messageText = it },
            onSendClick = { sendMessage() }
        )

        Spacer(modifier = Modifier.height(8.dp))
    }
}


@Composable
fun ChatProduct() {
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
                model = "imgUrl",
                contentDescription = null,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.img_miffy_doll)
            )

            Column(
                modifier = Modifier
                    .weight(1.3f)
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "미피 인형",
                    style = AchuTheme.typography.regular18
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "5,000원",
                    style = AchuTheme.typography.semiBold18.copy(color = FontPink)
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "판매중",
                    style = AchuTheme.typography.semiBold16.copy(color = PointPink)
                )
                Spacer(modifier = Modifier.weight(1f))
            }
            Spacer(modifier = Modifier.weight(1f))
            SmallLineBtn("거래 완료", PointPink) { }
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
fun SystemMessage(message: ChatMessage) {
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

@Composable
fun ChatMessageItem(message: ChatMessage) {
    val isSent = message.type == MessageType.SENT

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isSent) Alignment.End else Alignment.Start
    ) {
        // 발신자 이름 (내가 보낸 메시지가 아닐 때만 표시)
        if (!isSent) {
            Text(
                text = message.sender,
                style = AchuTheme.typography.semiBold14PointBlue.copy(color = FontGray),
                modifier = Modifier.padding(start = 12.dp, bottom = 4.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = if (isSent) Arrangement.End else Arrangement.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            // 타임스탬프 (보낸 메시지면 왼쪽, 받은 메시지면 오른쪽)
            if (isSent) {
                MessageTimestamp(timestamp = message.timestamp)
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
                            bottomStart = if (isSent) 16.dp else 0.dp,
                            bottomEnd = if (isSent) 0.dp else 16.dp
                        )
                    )
                    .background(
                        if (isSent) PointPink
                        else LightBlue
                    )
                    .padding(12.dp)
            ) {
                Text(
                    text = message.content,
                    color = if (isSent) Color.White else Color.Black,
                    style = AchuTheme.typography.regular16
                )
            }

            // 타임스탬프 (보낸 메시지면 오른쪽, 받은 메시지면 왼쪽)
            if (!isSent) {
                Spacer(modifier = Modifier.width(4.dp))
                MessageTimestamp(timestamp = message.timestamp)
            }
        }
    }

    Spacer(modifier = Modifier.height(8.dp))
}

@Composable
fun MessageTimestamp(timestamp: Long) {
    val date = Date(timestamp)
    val formatter = SimpleDateFormat("a h:mm", Locale.KOREA)
    val formattedTime = formatter.format(date)

    Text(
        text = formattedTime,
        style = AchuTheme.typography.regular14.copy(color = FontGray),
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
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
                onClick = onSendClick,
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
        trailingIcon = {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_gallery),
                    tint = FontGray,
                    contentDescription = stringResource(R.string.add_img)
                )
            }
        }
    )
}

// 시스템 메시지 추가 함수 (ViewModel에서 사용)
fun addSystemMessage(messages: List<ChatMessage>, content: String): List<ChatMessage> {
    val currentTime = System.currentTimeMillis()

    val systemMessage = ChatMessage(
        id = "system_${currentTime}",
        content = content,
        timestamp = currentTime,
        type = MessageType.SYSTEM
    )

    return messages + systemMessage
}

@Composable
fun CustomChatTopBar(
    onBackClick: () -> Unit,
    onLeaveClick: () -> Unit,
    userName: String
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
            Image(
                painter = painterResource(R.drawable.img_profile_test),
                contentDescription = stringResource(R.string.profile_img),
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(8.dp))
            // 사용자 이름
            Text(
                text = userName,
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
@Preview(showBackground = true)
@Composable
fun ChatScreenPreview() {
    AchuTheme {
        ChatScreen()
    }
}

// 사용 예시 프리뷰
@Preview(showBackground = true)
@Composable
fun PreviewCustomChatTopBar() {
    AchuTheme {
        CustomChatTopBar(
            onBackClick = { /* 뒤로가기 동작 */ },
            onLeaveClick = { /* 나가기 동작 */ },
            userName = "홍길동"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatProductPreview() {
    AchuTheme {
        ChatProduct()
    }
}

@Preview(showBackground = true)
@Composable
fun DateDividerPreview() {
    AchuTheme {
        DateDivider(timestamp = System.currentTimeMillis())
    }
}

@Preview(showBackground = true)
@Composable
fun SystemMessagePreview() {
    AchuTheme {
        SystemMessage(
            message = ChatMessage(
                id = "system_1",
                content = "홍길동님이 나갔습니다.",
                timestamp = System.currentTimeMillis(),
                type = MessageType.SYSTEM
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatMessagePreview_Sent() {
    AchuTheme {
        ChatMessageItem(
            message = ChatMessage(
                id = "msg_1",
                content = "안녕하세요! 반갑습니다.",
                timestamp = System.currentTimeMillis(),
                type = MessageType.SENT
            )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ChatMessagePreview_Received() {
    AchuTheme {
        ChatMessageItem(
            message = ChatMessage(
                id = "msg_2",
                content = "네, 반갑습니다. 오늘 날씨가 정말 좋네요.",
                timestamp = System.currentTimeMillis(),
                type = MessageType.RECEIVED,
                sender = "홍길동"
            )
        )
    }
}

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

@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun FullChatPreview() {
    AchuTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            ChatScreen()
        }
    }
}