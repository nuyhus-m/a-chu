package com.ssafy.achu.ui.memory.memoryupload

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.NewPink
import com.ssafy.achu.core.theme.PointPink

@Composable
fun LoadingScreen(text: String) {


    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.ani_dots_color))


    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever // 무한 반복
    )

    // ✅ Lottie 애니메이션 적용


    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {


        Image(
            painter = painterResource(id = R.drawable.img_loading_background), // 원하는 이미지 추가
            contentDescription = "Logo",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )


        LottieAnimation(
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .alpha(0.3f)
                .fillMaxSize()
        )


        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = text,
                style = AchuTheme.typography.semiBold20,
                color = NewPink,)

            Text(
                text = "추억업로드중...\n잠시만 기다려주세요",
                style = AchuTheme.typography.semiBold18,
                color = PointPink,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoadingScreen() {
    AchuTheme {
        LoadingScreen("추억 업로드 중...\n잠시만 기다려 주세요!")
    }

}
