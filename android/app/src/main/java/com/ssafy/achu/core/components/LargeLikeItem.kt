import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
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
    onClickItem: () -> Unit, // 아이템 전체 클릭 시 동작
    onClickHeart: () -> Unit, // 하트 클릭 시 동작
    productName: String,
    price: String,
    img: Painter? = null,
) {

    Box(
        modifier = Modifier
            .wrapContentSize()
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) // 필요한 경우 추가적인 shadow

    ) {


        Box(
            modifier = Modifier
                .height(230.dp)
                .width(170.dp)
                .background(White, shape = RoundedCornerShape(16.dp))
                .padding(10.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = onClickItem
                ) // 아이템 전체 클릭 시 동작
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start, // 수평 중앙 정렬
            ) {
                img?.let {
                    Image(
                        painter = img,
                        contentDescription = null,
                        modifier = Modifier
                            .width(150.dp)
                            .height(150.dp)
                            .clip(RoundedCornerShape(8.dp))
                    )
                }

                Text(
                    text = productName,
                    style = AchuTheme.typography.regular18,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .padding(start = 4.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = price,
                        style = AchuTheme.typography.semiBold16Pink,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    // 하트 클릭 처리
                    Image(
                        painter = painterResource(id = if (isLiked) R.drawable.ic_favorite else R.drawable.favorite_line),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (isLiked) FontPink else FontGray),
                        modifier = Modifier
                            .width(20.dp)
                            .height(20.dp)
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                                onClick = onClickHeart
                            ) // 하트 클릭 시 동작
                            .padding(end = 4.dp)
                    )
                }
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
                onClickItem = {
                    // 아이템 전체 클릭 시 동작
                    println("아이템 클릭됨")
                },
                onClickHeart = {
                    println("하트 클릭됨")
                },
                productName = "유아식기",
                price = "5,000원",
                img = ColorPainter(LightGray)
            )

            Spacer(Modifier.width(16.dp))

            LargeLikeItem(
                isLiked = false,
                onClickItem = {
                    // 아이템 전체 클릭 시 동작
                    println("아이템 클릭됨")
                },
                onClickHeart = {
                    println("하트 클릭됨")
                },
                productName = "유아식기",
                price = "5,000원",
                img = ColorPainter(LightGray)
            )

        }

    }
}
