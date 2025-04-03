package com.ssafy.achu.ui.product.productlist

import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.components.Divider
import com.ssafy.achu.core.components.PointBlueLineBtn
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontBlue
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.formatPrice
import com.ssafy.achu.core.util.formatRelativeTime
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "ProductListScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductListScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductListViewModel = viewModel(),
    activityViewModel: ActivityViewModel,
    onNavigateToUploadProduct: () -> Unit,
    onNavigateToProductDetail: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()
    val context = LocalContext.current

    LaunchedEffect(uiState.selectedCategoryId, uiState.query) {
        listState.scrollToItem(0)
        viewModel.updateCurrentOffset(0)
        viewModel.updateIsLastPage(false)
        viewModel.loadProductList()
    }

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest {
            Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        activityViewModel.getProductSuccess.collectLatest {
            if (it) {
                onNavigateToProductDetail()
            } else {
                Toast.makeText(
                    context,
                    context.getString(R.string.fail_get_product), Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        Column {
            Spacer(modifier = Modifier.height(24.dp))

            // 검색창
            Box(
                modifier = Modifier
                    .padding(horizontal = 24.dp)
            ) {
                SearchBar(
                    value = uiState.query,
                    onValueChange = {
                        viewModel.updateQuery(it)
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // 카테고리 버튼 리스트
            CategoryButtonList(
                items = uiState.categories,
                onButtonClick = { id ->
                    viewModel.updateSelectedCategoryId(id)
                },
                initialSelectedIndex = uiState.selectedCategoryId
            )

            Spacer(modifier = Modifier.height(16.dp))

            // 물품 리스트
            ProductList(
                items = uiState.products,
                onLoadMore = { viewModel.loadProductList() },
                listState = listState,
                onItemClick = { id -> activityViewModel.getProductDetail(id) },
                onLikeClick = { id, index -> viewModel.likeProduct(id, index) },
                onUnLikeClick = { id, index -> viewModel.unlikeProduct(id, index) }
            )
        }

        // FAB 버튼
        FloatingActionButton(
            onClick = onNavigateToUploadProduct,
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp),
            containerColor = PointBlue,
            shape = RoundedCornerShape(100)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = stringResource(R.string.move_to_upload_screen)
            )
        }
    }
}

@Composable
private fun SearchBar(
    value: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        trailingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon)
            )
        },
        placeholder = {
            Text(
                text = stringResource(R.string.searchbar_placeholder),
                style = AchuTheme.typography.regular16.copy(color = Color(0xFFC4C4C4))
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color(0xFFF3F3F3), shape = RoundedCornerShape(16.dp)),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = Color.Transparent,
            unfocusedBorderColor = Color.Transparent,
            cursorColor = Color.Black
        ),
        singleLine = true
    )
}

@Composable
fun CategoryButtonList(
    items: List<CategoryResponse>,
    onButtonClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    initialSelectedIndex: Int
) {
    var selectedIndex by remember { mutableIntStateOf(initialSelectedIndex) }
    val listState = rememberLazyListState()

    LaunchedEffect(Unit) {
        listState.scrollToItem(initialSelectedIndex)
    }

    LazyRow(
        state = listState,
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(items.withIndex().toList()) { (index, item) ->
            val isSelected = index == selectedIndex

            PointBlueLineBtn(buttonText = item.name, isSelected = isSelected) {
                selectedIndex = index
                onButtonClick(item.id)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductList(
    items: List<ProductResponse>,
    listState: LazyListState,
    onLoadMore: () -> Unit,
    onItemClick: (Int) -> Unit,
    onLikeClick: (Int, Int) -> Unit,
    onUnLikeClick: (Int, Int) -> Unit
) {

    val lastIndex = listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index ?: -1
    val shouldLoadMore = lastIndex > 0 && (lastIndex >= items.size - 5) && items.isNotEmpty()

    LaunchedEffect(key1 = lastIndex) {
        if (shouldLoadMore) {
            onLoadMore()
        }
    }

    LazyColumn(
        state = listState
    ) {
        itemsIndexed(items) { index, item ->
            ProductItem(
                productResponse = item,
                onItemClick = { onItemClick(item.id) },
                onLikeClick = { onLikeClick(item.id, index) },
                onUnLikeClick = { onUnLikeClick(item.id, index) }
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductItem(
    productResponse: ProductResponse,
    onItemClick: () -> Unit,
    onLikeClick: () -> Unit,
    onUnLikeClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .clickable { onItemClick() }
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            AsyncImage(
                model = productResponse.imgUrl,
                contentDescription = null,
                modifier = Modifier
                    .weight(0.4f)
                    .clip(RoundedCornerShape(16.dp))
                    .aspectRatio(1f),
                contentScale = ContentScale.Crop,
                error = painterResource(R.drawable.img_miffy_doll)
            )

            Column(
                modifier = Modifier
                    .weight(0.6f)
                    .padding(start = 16.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = productResponse.title,
                    style = AchuTheme.typography.regular18,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = formatRelativeTime(productResponse.createdAt),
                    style = AchuTheme.typography.regular16.copy(color = FontGray),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                if (productResponse.price != 0) {
                    Text(
                        text = formatPrice(productResponse.price),
                        style = AchuTheme.typography.semiBold18.copy(color = FontPink),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                } else {
                    Text(
                        text = stringResource(R.string.free),
                        style = AchuTheme.typography.semiBold18.copy(color = FontBlue),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Spacer(modifier = Modifier.weight(1f))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chat_product),
                        contentDescription = null,
                        tint = FontGray
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = productResponse.chatCount.toString(),
                        style = AchuTheme.typography.regular16.copy(color = FontGray)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter =
                            if (productResponse.likedByUser) painterResource(id = R.drawable.ic_favorite)
                            else painterResource(id = R.drawable.ic_favorite_line),
                        contentDescription = null,
                        tint = if (productResponse.likedByUser) FontPink else FontGray,
                        modifier = Modifier.clickable(onClick = {
                            if (productResponse.likedByUser) {
                                onUnLikeClick()
                            } else {
                                onLikeClick()
                            }
                        })
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = productResponse.likedUsersCount.toString(),
                        style = AchuTheme.typography.regular16.copy(color = FontGray)
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Divider()
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun ProductListScreenPreview() {
//    AchuTheme {
//        ProductListScreen(
//            onNavigateToUploadProduct = {},
//            onNavigateToProductDetail = {},
//        )
//    }
//}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun ProductItemPreview() {
    AchuTheme {
        ProductItem(
            productResponse = ProductResponse(
                chatCount = 11,
                createdAt = "3일 전",
                id = 1,
                imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
                likedByUser = false,
                likedUsersCount = 18,
                price = 5000,
                title = "미피 인형"
            ),
            onItemClick = {},
            onLikeClick = {},
            onUnLikeClick = {}
        )
    }
}