package com.ssafy.achu.ui.product.productdetail

import BasicRecommendItem
import android.net.Uri
import android.os.Build
import android.widget.Toast
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
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil3.compose.AsyncImage
import com.ssafy.achu.R
import com.ssafy.achu.core.LoadingImgScreen
import com.ssafy.achu.core.components.Divider
import com.ssafy.achu.core.components.TopBarWithMenu
import com.ssafy.achu.core.components.dialog.BasicDialog
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.FontGray
import com.ssafy.achu.core.theme.FontPink
import com.ssafy.achu.core.theme.LightGray
import com.ssafy.achu.core.theme.LightPink
import com.ssafy.achu.core.theme.White
import com.ssafy.achu.core.util.Constants.SOLD
import com.ssafy.achu.core.util.formatDate
import com.ssafy.achu.core.util.formatPrice
import com.ssafy.achu.data.model.product.CategoryResponse
import com.ssafy.achu.data.model.product.ProductResponse
import com.ssafy.achu.data.model.product.Seller
import com.ssafy.achu.ui.ActivityViewModel
import kotlinx.coroutines.flow.collectLatest

private const val TAG = "ProductDetailScreen"

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ProductDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: ProductDetailViewModel = viewModel(),
    activityViewModel: ActivityViewModel,
    isPreview: Boolean = false,
    onBackClick: () -> Unit,
    onNavigateToUpload: () -> Unit,
    onNavigateToChat: () -> Unit,
    onNavigateToRecommend: () -> Unit,
    onNavigateToMemoryUpload: (Int, String) -> Unit,
    onNavigateToProductList: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val activityUiState by activityViewModel.uiState.collectAsState()

    val isSeller = activityUiState.user?.nickname == activityUiState.product.seller.nickname
    val isSold = activityUiState.product.tradeStatus == SOLD

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.toastMessage.collectLatest { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.isLikeSuccess.collectLatest {
            if (it) {
                activityViewModel.getProductDetail(activityUiState.product.id)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.isUnLikeSuccess.collectLatest {
            if (it) {
                activityViewModel.getProductDetail(activityUiState.product.id)
            }
        }
    }

    LaunchedEffect(uiState.isDeleteSuccess) {
        if (uiState.isDeleteSuccess) {
            onBackClick()
        }
    }

    LaunchedEffect(Unit) {
        viewModel.navigateEvents.collectLatest {
            if (it) {
                onNavigateToMemoryUpload(
                    activityUiState.uploadProductRequest?.babyId ?: 0,
                    activityUiState.uploadProductRequest?.title ?: ""
                )
            } else {
                onNavigateToProductList()
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = White)
    ) {
        // 탑바
        TopBarWithMenu(
            title = if (isPreview) stringResource(R.string.upload_preview) else stringResource(R.string.product_detail),
            onBackClick = onBackClick,
            menuFirstText = stringResource(R.string.modify),
            menuSecondText = stringResource(R.string.delete),
            onMenuFirstItemClick = onNavigateToUpload,
            onMenuSecondItemClick = { viewModel.updateShowDeleteDialog(true) },
            isMenuVisible = isSeller && !isSold && !isPreview
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(scrollState)
        ) {
            // 이미지 페이저
            if (!isPreview) {
                ImagePager(
                    images = activityUiState.product.imgUrls
                )
            } else {
                ImageUriPager(
                    images = activityUiState.previewImgUris
                )
            }

            // 프로필
            ProfileInfo(
                seller = activityUiState.product.seller,
                isSold = isSold
            )

            // 물품 정보
            ProductInfo(
                title = activityUiState.product.title,
                description = activityUiState.product.description,
                category = activityUiState.product.category,
                date = if (!isPreview) formatDate(activityUiState.product.createdAt) else activityUiState.product.createdAt
            )

            // 추천 리스트
            if (!isPreview) {
                RecommendList(
                    onNavigateToRecommend = onNavigateToRecommend
                )
            }
        }

        BottomBar(
            likeCount = activityUiState.product.likedUsersCount,
            price = activityUiState.product.price,
            isSeller = isSeller,
            isSold = isSold,
            isPreview = isPreview,
            likedByUser = activityUiState.product.likedByUser,
            onLikeClick = { viewModel.likeProduct(activityUiState.product.id) },
            onUnLikeClick = { viewModel.unlikeProduct(activityUiState.product.id) },
            onButtonClick = {
                if (!isPreview) onNavigateToChat()
                else viewModel.updateShowUploadDialog(true)
            }
        )
    }

    if (uiState.showDeleteDialog) {
        BasicDialog(
            pinkText = activityUiState.product.title,
            textLine1 = "의",
            text = "판매를 중지하시겠습니까?",
            onDismiss = { viewModel.updateShowDeleteDialog(false) },
            onConfirm = { viewModel.deleteProduct(activityUiState.product.id) }
        )
    }

    if (uiState.showUploadDialog) {
        UploadDialog(
            productName = activityUiState.uploadProductRequest?.title ?: "",
            babyName = activityUiState.uploadBabyName,
            onUpload = {
                viewModel.uploadProduct(
                    uploadProductRequest = activityUiState.uploadProductRequest!!,
                    images = activityUiState.multiPartImages,
                    isWithMemory = false
                )
            },
            onUploadWithMemory = {
                viewModel.uploadProduct(
                    uploadProductRequest = activityUiState.uploadProductRequest!!,
                    images = activityUiState.multiPartImages,
                    isWithMemory = true
                )
            }
        )
    }
}

@Composable
private fun BottomBar(
    likeCount: Int,
    likedByUser: Boolean,
    price: Int,
    isSeller: Boolean,
    isSold: Boolean,
    isPreview: Boolean,
    onLikeClick: () -> Unit,
    onUnLikeClick: () -> Unit,
    onButtonClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .navigationBarsPadding(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = if (likedByUser) painterResource(id = R.drawable.ic_favorite)
            else painterResource(id = R.drawable.ic_favorite_line),
            contentDescription = stringResource(R.string.like),
            tint = if (likedByUser) FontPink else FontGray,
            modifier = Modifier
                .size(32.dp)
                .clickable(
                    onClick = if (likedByUser) onUnLikeClick else onLikeClick,
                    enabled = !isPreview
                ),
        )
        if (likeCount > 0) {
            Text(
                text = likeCount.toString(),
                style = AchuTheme.typography.regular18.copy(color = FontGray),
                modifier = Modifier.padding(start = 8.dp)
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .width(1.dp)
                .height(48.dp)
                .background(LightGray)
        )
        Text(
            text = formatPrice(price),
            style = AchuTheme.typography.semiBold20.copy(color = FontPink),
            modifier = Modifier.padding(start = 16.dp)
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = onButtonClick,
            enabled = (!isSeller && !isSold && !isPreview) || isPreview,
            modifier = Modifier
                .height(50.dp)
        ) {
            Text(
                text = if (isPreview) stringResource(R.string.register) else stringResource(R.string.go_chat),
                style = AchuTheme.typography.semiBold20White
            )
        }
    }
}

@Composable
private fun RecommendList(
    onNavigateToRecommend: () -> Unit
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = stringResource(R.string.recommend_title),
            style = AchuTheme.typography.semiBold18
        )
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowRight,
            contentDescription = stringResource(R.string.more),
            modifier = Modifier.clickable(onClick = onNavigateToRecommend)
        )
    }
    val productResponses = listOf(
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = false,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
        ProductResponse(
            chatCount = 11,
            createdAt = "3일 전",
            id = 1,
            imgUrl = "https://www.cheonyu.com/_DATA/product/70900/70982_1705645864.jpg",
            likedByUser = true,
            likedUsersCount = 18,
            price = 5000,
            title = "미피 인형"
        ),
    )
    Row(
        modifier = Modifier.padding(start = 24.dp)
    ) {
        RecommendList(
            items = productResponses
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun ProductInfo(
    title: String,
    description: String,
    category: CategoryResponse,
    date: String
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .defaultMinSize(minHeight = 220.dp)
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 24.dp)
    ) {
        Text(
            text = title,
            style = AchuTheme.typography.bold24
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(
                text = category.name,
                style = AchuTheme.typography.regular14.copy(
                    color = FontGray,
                    textDecoration = TextDecoration.Underline
                )
            )
            Text(
                text = stringResource(R.string.symbol),
                style = AchuTheme.typography.regular14.copy(color = FontGray)
            )
            Text(
                text = date,
                style = AchuTheme.typography.regular14.copy(color = FontGray)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            text = description,
            style = AchuTheme.typography.regular18
        )
    }
    Divider()
}

@Composable
private fun ProfileInfo(
    seller: Seller,
    isSold: Boolean,
) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(top = 24.dp, bottom = 16.dp, start = 24.dp, end = 24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box() {
            Image(
                painter = painterResource(R.drawable.img_profile_basic2),
                contentDescription = stringResource(R.string.profile),
                modifier = Modifier
                    .fillMaxWidth(0.16f)
                    .aspectRatio(1f)
                    .clip(CircleShape)
                    .background(color = LightPink),
                contentScale = ContentScale.Crop
            )
            AsyncImage(
                model = seller.imgUrl,
                contentDescription = stringResource(R.string.profile),
                modifier = Modifier
                    .fillMaxWidth(0.16f)
                    .aspectRatio(1f)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop
            )
        }

        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = seller.nickname,
                style = AchuTheme.typography.semiBold20
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = if (isSold) stringResource(R.string.sold)
                else stringResource(R.string.selling),
                style = AchuTheme.typography.semiBold14PointBlue
            )
        }
    }
    Divider()
}

@Composable
fun RecommendList(items: List<ProductResponse>) {
    LazyRow {
        items(items) { item ->
            BasicRecommendItem(
                isLiked = item.likedByUser,
                onClickItem = {},
                onClickHeart = {},
                productName = item.title,
                state = "판매중",
                price = "${item.price}원",
                img = ColorPainter(LightGray)
            )
            Spacer(modifier = Modifier.width(16.dp))
        }
    }
}

@Composable
fun ImagePager(images: List<String>) {

    // 페이저 상태
    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        // 이미지 페이저
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            AsyncImage(
                model = images[page],
                contentDescription = "이미지 ${page + 1}",
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
        }

        // 페이지 인디케이터
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                // 현재 페이지에 따라 점 색상 변경
                val color = if (pagerState.currentPage == index) Color.White else Color.Gray

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@Composable
fun ImageUriPager(images: List<Uri>) {

    // 페이저 상태
    val pagerState = rememberPagerState(pageCount = { images.size })

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        // 이미지 페이저
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            var isImageLoading by remember { mutableStateOf(true) }

            Box(modifier = Modifier.fillMaxSize()) {
                if (isImageLoading) {
                    LoadingImgScreen("이미지 로딩중...", Modifier.align(Alignment.Center), 16)
                }

                AsyncImage(
                    model = images[page],
                    contentDescription = "이미지 ${page + 1}",
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.White),
                    contentScale = ContentScale.Crop,
                    onSuccess = {
                        isImageLoading = false
                    },
                    onError = {
                        isImageLoading = false
                    }
                )
            }
        }

        // 페이지 인디케이터
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(images.size) { index ->
                // 현재 페이지에 따라 점 색상 변경
                val color = if (pagerState.currentPage == index) Color.White else Color.Gray

                Box(
                    modifier = Modifier
                        .padding(horizontal = 4.dp)
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(color)
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun PreviewProductDetailScreen() {
    AchuTheme {
//        ProductDetailScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTopBarWithMenu() {
    AchuTheme {
        TopBarWithMenu(
            title = "거래상세",
            onBackClick = {},
            menuFirstText = "수정",
            menuSecondText = "삭제",
            onMenuFirstItemClick = {},
            onMenuSecondItemClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewBottomBar() {
    AchuTheme {
        BottomBar(
            likeCount = 10,
            price = 10000,
            isSeller = true,
            isSold = false,
            likedByUser = true,
            onLikeClick = {},
            onButtonClick = {},
            isPreview = false,
            onUnLikeClick = {}
        )
    }
}

//@Preview(showBackground = true)
//@Composable
//fun PreviewImagePager() {
//    ImagePager()
//}
