import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.magnifier
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.White

@Composable
fun LargeLikeItem(
    isLiked: Boolean,
    state: String,
    onClickItem: () -> Unit,
    onClickHeart: () -> Unit,
    productName: String,
    price: String,
    img: Painter? = null
) {
    val screenWidth = LocalConfiguration.current.screenWidthDp.dp // 전체 화면 너비
    val itemWidth = screenWidth * 0.42f

    Box(
        modifier = Modifier
            .wrapContentSize()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .width(itemWidth) // 화면 비율 적용
                .wrapContentHeight()
                .background(White, shape = RoundedCornerShape(16.dp))
                .padding(8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickItem
                )
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start,
            ) {
                Box(
                    modifier = Modifier
                        .width(itemWidth)
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                ) {
                    img?.let {
                        Image(
                            painter = img,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f) // 정사각형
                                .clip(RoundedCornerShape(8.dp)),
                            contentScale = ContentScale.Crop
                        )
                    }

                    if (state == "거래완료") {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio(1f) // 정사각형
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color.Black.copy(alpha = 0.5f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = "거래완료",
                                color = White,
                                style = AchuTheme.typography.semiBold16Pink
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = productName,
                    style = AchuTheme.typography.regular18,
                    modifier = Modifier.padding(start = 4.dp),
                    maxLines = 1, // 한 줄만 표시
                    overflow = TextOverflow.Ellipsis // 넘치는 부분은 "..."으로 표시
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(24.dp)
                        .padding(start = 4.dp),
                    verticalAlignment = Alignment.Bottom
                ) {


                    Text(
                        text = price,
                        style = AchuTheme.typography.semiBold16Pink,
                        maxLines = 1, // 한 줄만 표시
                        overflow = TextOverflow.Ellipsis, // 넘치는 부분은 "..."으로 표시
                        modifier = Modifier.weight(0.8f)

                    )

                    Image(
                        painter = painterResource(id = if (isLiked) R.drawable.ic_favorite else R.drawable.ic_favorite_line),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (isLiked) FontPink else FontGray),
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp).weight(0.2f)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onClickHeart
                            )
                            .padding(end = 4.dp)
                    )
                }
                Spacer(Modifier.height(4.dp))
            }
        }
    }
}

@Preview
@Composable
fun preItem2() {
    AchuTheme {
        Row(modifier = Modifier.padding(24.dp)) {
            LargeLikeItem(
                isLiked = true,
                state = "판매중",
                onClickItem = {
                    // 아이템 전체 클릭 시 동작
                    println("아이템 클릭됨")
                },
                onClickHeart = {
                    println("하트 클릭됨")
                },
                productName = "유아식기",
                price = "500,000,000원",
                img = ColorPainter(LightGray)
            )

            Spacer(Modifier.width(16.dp))

            LargeLikeItem(
                isLiked = false,
                state = "판매중",
                onClickItem = {
                    // 아이템 전체 클릭 시 동작
                    println("아이템 클릭됨")
                },
                onClickHeart = {
                    println("하트 클릭됨")
                },
                productName = "유아식기",
                price = "5,000",
                img = ColorPainter(LightGray)
            )

        }

    }
}
