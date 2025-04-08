package com.ssafy.achu.core

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import com.ssafy.achu.R
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.NewPink
import com.ssafy.achu.core.theme.White

@Composable
fun LoadingImgScreen(text: String, modifier: Modifier, fontSize: Int = 14, lottieSize: Int) {


    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.loading_lottie))


    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever // 무한 반복
    )


    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {

        LottieAnimation(
            composition = composition,
            progress = { progress },
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(lottieSize.dp)
        )



        Text(
            text = text,
            style = AchuTheme.typography.semiBold14PointBlue,
            color = NewPink,
            fontSize = fontSize.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Preview
@Composable
fun PreviewScreen() {
    AchuTheme {
        LoadingImgScreen("이미지 로드중...", modifier = Modifier.fillMaxWidth(), 16, 250)

    }

}
