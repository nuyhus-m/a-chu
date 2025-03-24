package com.ssafy.achu.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.BasicTopAppBar
import com.ssafy.achu.core.components.Divider
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White

@Composable
fun ChatListScreen(modifier: Modifier = Modifier) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        BasicTopAppBar(
            title = stringResource(R.string.chat_list),
            onBackClick = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        val chats = listOf(
            Chat(
                imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
                title = "덕윤맘",
                productName = "미피 인형",
                lastMessage = "사진 추가로 볼 수 있을까요?",
                time = "오후 7:59",
                unreadCount = 1
            ),
            Chat(
                imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
                title = "덕윤맘",
                productName = "미피 인형",
                lastMessage = "사진 추가로 볼 수 있을까요?",
                time = "오후 7:59",
                unreadCount = 1
            ),
        )
        ChatList(chats)
    }
}

@Composable
fun ChatItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = "https://www.cheonyu.com/_DATA/product/70900/70983_1705645848.jpg",
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
                        text = "덕윤맘",
                        style = AchuTheme.typography.semiBold18
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "미피 인형",
                        style = AchuTheme.typography.semiBold16.copy(color = PointBlue),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "사진 추가로 볼 수 있을까요?",
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
                    text = "오후 7:59",
                    style = AchuTheme.typography.regular14.copy(color = FontGray)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "1",
                    style = AchuTheme.typography.regular16.copy(color = White),
                    modifier = Modifier
                        .background(color = FontPink, shape = CircleShape)
                        .padding(horizontal = 10.dp, vertical = 4.dp)
                )
            }
        }
        Divider()
    }
}

@Composable
fun ChatList(items: List<Chat>) {
    LazyColumn {
        items(items) { item ->
            ChatItem()
        }
    }
}

// 임시 데이터 클래스
data class Chat(
    val imgUrl: String,
    val title: String,
    val productName: String,
    val lastMessage: String,
    val time: String,
    val unreadCount: Int,
)

@Preview
@Composable
fun ChatListScreenPreview() {
    AchuTheme {
        ChatListScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun ChatItemPreview() {
    AchuTheme {
        ChatItem()
    }
}