package com.ssafy.achu.ui.home.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.data.model.product.CategoryResponse

@Composable
fun FixedGrid(
    categoryList: List<CategoryResponse>,
    onNavigateToProductList: (String) -> Unit
) {
    val chunkedList = categoryList.chunked(4) // 4개씩 끊어서 행 단위로 만듦

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
                    CategoryItem(
                        img = category.imgUrl,
                        categoryTitle = category.name,
                        modifier = Modifier
                            .weight(1f)
                            .clickable { onNavigateToProductList(category.id.toString()) }
                    )
                }

                // 만약 4개보다 적으면 빈 공간 추가
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
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = resId),
            contentDescription = "Heart",
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = categoryTitle,
            style = AchuTheme.typography.semiBold16
        )
    }
}
