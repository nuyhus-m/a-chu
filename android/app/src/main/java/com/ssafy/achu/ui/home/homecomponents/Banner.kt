package com.ssafy.achu.ui.home.homecomponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.ssafy.achu.R
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun InfiniteBanner(
    onNavigateToBabyList: () -> Unit,
    onNavigateToRecommend: () -> Unit,
) {
    val imageList = listOf(
        R.drawable.img_banner1,
        R.drawable.img_banner2
    )

    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val flingBehavior = rememberSnapperFlingBehavior(
        lazyListState = listState,
        snapIndex = { _, startIndex, targetIndex ->
            // 자연스러운 snap을 위한 index 보정
            targetIndex.coerceIn(0, imageList.size - 1)
        }
    )

    var currentIndex by remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        while (true) {
            delay(3000)
            coroutineScope.launch {
                currentIndex = (currentIndex + 1) % imageList.size
                listState.animateScrollToItem(currentIndex)
            }
        }
    }

    LazyRow(
        state = listState,
        flingBehavior = flingBehavior,
        horizontalArrangement = Arrangement.spacedBy(0.dp),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(horizontal = 4.dp)
            .clip(RoundedCornerShape(8.dp)),
        userScrollEnabled = true
    ) {
        items(imageList.size) { index ->
            Box(
                modifier = Modifier
                    .fillParentMaxWidth()
                    .aspectRatio(16f / 9f)
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        when (index) {
                            0 -> onNavigateToBabyList()
                            1 -> onNavigateToRecommend()
                        }
                    }
            ) {
                Image(
                    painter = painterResource(id = imageList[index]),
                    contentDescription = "Banner $index",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}
