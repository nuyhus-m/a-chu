import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.LoadingImg
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatPrice
import com.ssafy.achu.data.model.product.ProductResponse

@Composable
fun BasicRecommendItem(
    product: ProductResponse,
    onClickItem: (Int) -> Unit, // 아이템 전체 클릭 시 동작
    onLikeClick: (Int) -> Unit,
    onUnLikeClick: (Int) -> Unit,
) {

    var isLiked: Boolean by remember { mutableStateOf(product.likedByUser) }

    Box(
        modifier = Modifier
            .wrapContentSize()
            .padding(start = 2.dp)
            .shadow(elevation = 4.dp, shape = RoundedCornerShape(16.dp)) // 필요한 경우 추가적인 shadow
    ) {

        Box(
            modifier = Modifier
                .height(220.dp)
                .width(160.dp)
                .background(White, shape = RoundedCornerShape(16.dp))
                .padding(8.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { onClickItem(product.id) }
                ) // 아이템 전체 클릭 시 동작
        ) {

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.Start, // 수평 중앙 정렬
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f) // 정사각형
                        .clip(RoundedCornerShape(8.dp))

                ) {

                    LoadingImg("이미지로딩중", Modifier.fillMaxWidth(), 12, 40)
                    AsyncImage(
                        model = product.imgUrl,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f) // 정사각형
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )


                    // 🔹 거래 완료 오버레이 추가
                    //if (product.Stat == "거래완료") {
                    //   Box(
                    //      modifier = Modifier
                    //          .matchParentSize()
                    //          .background(Color.Black.copy(alpha = 0.4f)) // 반투명 배경
                    //          .clip(RoundedCornerShape(8.dp)), // 모서리 둥글게
                    //       contentAlignment = Alignment.Center
                    //   ) {
                    //        Text(
                    //             text = "거래완료",
                    //             color = White,
                    //              style = AchuTheme.typography.semiBold16Pink
                    //           )
                    // //       }
                    //    }
                }



                Text(
                    text = product.title,
                    style = AchuTheme.typography.regular18,
                    modifier = Modifier.padding(top = 8.dp, start = 4.dp),
                    maxLines = 1, // 한 줄만 표시
                    overflow = TextOverflow.Ellipsis // 넘치는 부분은 "..."으로 표시
                )


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(28.dp)
                        .padding(start = 4.dp),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.Start
                ) {
                    // 가격 텍스트
                    Text(
                        text = formatPrice(product.price),
                        style = AchuTheme.typography.semiBold16Pink,
                        modifier = Modifier
                            .padding(top = 8.dp) // 오른쪽 여백 추가
                            .weight(0.8f),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,// 가격이 길어지면 ...으로 표시,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Start
                    )

                    // 하트 클릭 처리 (고정된 크기 유지)
                    Image(
                        painter = painterResource(id = if (isLiked) R.drawable.ic_favorite else R.drawable.ic_favorite_line),
                        contentDescription = null,
                        colorFilter = ColorFilter.tint(if (isLiked) FontPink else FontGray),
                        modifier = Modifier
                            .width(24.dp)
                            .height(24.dp)
                            .weight(0.2f)
//                            .clickable(
//                                interactionSource = remember { MutableInteractionSource() },
//                                indication = null,
//                                onClick = {
//                                    if (isLiked) {
//                                        onUnLikeClick(product.id)
//                                        isLiked = false
//                                    } else {
//                                        onLikeClick(product.id)
//                                        isLiked = true
//                                    }
//                                },
//
//                                )
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
        Row(Modifier.padding(4.dp)) {

        }
    }
}
