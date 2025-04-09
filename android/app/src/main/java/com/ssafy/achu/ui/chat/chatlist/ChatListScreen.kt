package com.ssafy.achu.ui.chat.chatlist

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.Divider
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatChatRoomTime
import com.ssafy.achu.data.model.chat.ChatRoomResponse
import com.ssafy.achu.ui.ActivityViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatListScreen(
    modifier: Modifier = Modifier,
    viewModel: ChatListViewModel = viewModel(),
    activityViewModel: ActivityViewModel,
    onNavigateToChat: (Int) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    DisposableEffect(Unit) {
        activityViewModel.uiState.value.user?.let {
            viewModel.connectToStompServer(it.id)
        }
        viewModel.getChatRooms()

        onDispose {
            viewModel.cancelStomp()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        // 탑바
        Box(
            modifier = Modifier.padding(start = 24.dp, top = 48.dp, bottom = 24.dp)
        ) {
            Text(
                text = stringResource(id = R.string.chat_list),
                style = AchuTheme.typography.semiBold24
            )
        }

        if (uiState.chatRooms.isEmpty()) {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(color = White),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Image(
                    painter = painterResource(id = R.drawable.img_smiling_face),
                    contentDescription = "Profile",
                    modifier = Modifier
                        .size(60.dp)
                )

                Spacer(Modifier.height(24.dp))
                Text(
                    text = "마음에 드는 상품이 있나요?\n지금 바로 채팅해보세요!",
                    style = AchuTheme.typography.semiBold18,
                    color = FontGray,
                    textAlign = TextAlign.Center,
                    lineHeight = 30.sp
                )
            }
        } else {
            // 채팅 목록
            ChatList(
                items = uiState.chatRooms,
                onNavigateToChat
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatList(
    items: List<ChatRoomResponse>,
    onNavigateToChat: (Int) -> Unit
) {
    LazyColumn {
        items(items) { item ->
            ChatItem(
                chatRoom = item,
                onNavigateToChat = {
                    onNavigateToChat(item.id)
                }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ChatItem(
    chatRoom: ChatRoomResponse,
    onNavigateToChat: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .clickable(onClick = onNavigateToChat)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = chatRoom.goods.thumbnailImageUrl,
                contentDescription = stringResource(R.string.product_img),
                modifier = Modifier
                    .fillMaxWidth(0.2f)
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.7f)
                    .padding(start = 16.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        text = chatRoom.partner.nickname,
                        style = AchuTheme.typography.semiBold18
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = chatRoom.goods.title,
                        style = AchuTheme.typography.semiBold16.copy(color = PointBlue),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = chatRoom.lastMessage.content,
                    style = AchuTheme.typography.regular16.copy(color = FontGray),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = formatChatRoomTime(chatRoom.lastMessage.timestamp),
                    style = AchuTheme.typography.regular14.copy(color = FontGray)
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (chatRoom.unreadCount > 0) {
                    Text(
                        text = chatRoom.unreadCount.toString(),
                        style = AchuTheme.typography.regular16.copy(color = White),
                        modifier = Modifier
                            .background(color = FontPink, shape = CircleShape)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    )
                }
            }
        }
        Divider()
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview
//@Composable
//fun ChatListScreenPreview() {
//    AchuTheme {
//        ChatListScreen(
//            activityViewModel = viewModel(),
//            onNavigateToChat = {}
//        )
//    }
//}

@Preview(showBackground = true)
@Composable
fun ChatItemPreview() {
    AchuTheme {
//        ChatItem()
    }
}