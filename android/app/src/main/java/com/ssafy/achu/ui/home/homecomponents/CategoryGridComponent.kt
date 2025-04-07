package com.ssafy.achu.ui.home.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.data.model.product.CategoryResponse

@Composable
fun FixedGrid(
    categoryList: List<CategoryResponse>,
    onNavigateToProductList: (String) -> Unit
) {
    // 리스트가 비어 있으면 placeholder 8개 생성 (2행 고정)
    val filledList = if (categoryList.isEmpty()) {
        List(8) {
            CategoryResponse(id = 0, name = "", imgUrl = "ic_category_others")
        }
    } else {
        categoryList
    }

    val chunkedList = filledList.chunked(4) // 4개씩 끊어서 행 단위로 만듦

    Column(
        modifier = Modifier
            .padding(bottom = 24.dp, start = 16.dp, end = 16.dp)
            .wrapContentHeight(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        chunkedList.forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                rowItems.forEach { category ->
                    val isPlaceholder = category.name.isEmpty()

                    CategoryItem(
                        img = category.imgUrl,
                        categoryTitle = category.name,
                        modifier = Modifier
                            .weight(1f)
                            .then(
                                if (!isPlaceholder) {
                                    Modifier.clickable(
                                        interactionSource = remember { MutableInteractionSource() },
                                        indication = null
                                    ) {
                                        onNavigateToProductList(category.id.toString())
                                    }
                                } else {
                                    Modifier // 클릭 불가
                                }
                            )
                    )
                }

                // 4개보다 적으면 빈 칸 채우기
                repeat(4 - rowItems.size) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun CategoryItem(
    img: String,
    categoryTitle: String,
    modifier: Modifier
) {
    val context = LocalContext.current
    val resId = context.resources.getIdentifier(img, "drawable", context.packageName)
    val isPlaceholder = categoryTitle.isEmpty()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "Category Image",
            modifier = Modifier.graphicsLayer {
                alpha = if (isPlaceholder) 0f else 1f
            }
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = categoryTitle,
            style = AchuTheme.typography.semiBold16,
            color = if (isPlaceholder) Color.Transparent else Black
        )
    }
}
