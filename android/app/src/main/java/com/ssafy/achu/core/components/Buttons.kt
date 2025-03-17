package com.ssafy.achu.core.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
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
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.ssafy.achu.core.theme.AchuTheme
import com.ssafy.achu.core.theme.PointBlue
import com.ssafy.achu.core.theme.PointPink

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PointBlueButton(buttonText: String, radius: Dp = 12.dp, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 60.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .drawColoredShadow(
                    color = PointBlue,
                    alpha = 0.5f,
                    borderRadius = 12.dp,
                    shadowRadius = 16.dp,
                    offsetX = 4.dp,
                    offsetY = 4.dp
                )
                .height(50.dp)
                .clip(RoundedCornerShape(radius))
                .background(PointBlue)
                .clickable(
                    onClick = onClick
                ),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = Color.White,
                style = AchuTheme.typography.semiBold20White
            )
        }
    }
}

//글씨 크기에 맞춰 지는 파란 버튼
@Composable
fun PointBlueFlexibleBtn(buttonText: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth() // 텍스트의 너비에 맞게 크기 자동 조정
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .background(PointBlue, shape = RoundedCornerShape(50.dp)) // 버튼 색상 및 모서리 둥글게
                .clickable(onClick = onClick)
                .height(50.dp)
                .padding(vertical = 16.dp, horizontal = 24.dp), // 텍스트의 여백을 설정
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = Color.White,
                style = AchuTheme.typography.semiBold18White // 원하는 글씨 스타일
            )
        }
    }
}

//파란색 라인 버튼 -> 클릭시 색상반전
@Composable
fun PointBlueLineBtn(buttonText: String, onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }

    val buttonColor = if (isClicked) PointBlue else Color.White
    val textColor = if (isClicked) Color.White else Color.Black

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .background(buttonColor, shape = RoundedCornerShape(50.dp))
                .border(2.dp, PointBlue, RoundedCornerShape(50.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        isClicked = !isClicked
                        onClick()
                    }
                )
                .height(50.dp)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = textColor,
                style = AchuTheme.typography.semiBold18White
            )
        }
    }
}

//핑크색 기본 포인트 버튼
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun PointPinkBtn(buttonText: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 60.dp)
                .drawColoredShadow(
                    color = PointPink,         // 그림자의 색상을 빨간색으로 설정
                    alpha = 0.5f,              // 그림자의 투명도를 50%로 설정
                    borderRadius = 12.dp,      // 둥근 모서리로 그림자 설정
                    shadowRadius = 16.dp,      // 그림자 크기를 16.dp로 설정
                    offsetX = 4.dp,            // X축으로 그림자 이동
                    offsetY = 4.dp             // Y축으로 그림자 이동
                )
                .height(50.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(PointPink)
                .clickable(onClick = onClick),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = Color.White,
                style = AchuTheme.typography.semiBold20White
            )
        }
    }
}

//글씨 크기에 맞춰 지는 핑크 버튼
@Composable
fun PointPinkFlexibleBtn(buttonText: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .wrapContentWidth() // 텍스트의 너비에 맞게 크기 자동 조정
            .height(50.dp)
    ) {
        Box(
            modifier = Modifier
                .background(PointPink, shape = RoundedCornerShape(50.dp)) // 버튼 색상 및 모서리 둥글게
                .clickable(
                    onClick = onClick
                )
                .height(50.dp)
                .padding(horizontal = 24.dp), // 텍스트의 여백을 설정
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = Color.White,
                style = AchuTheme.typography.semiBold18White // 원하는 글씨 스타일
            )
        }
    }
}

//핑크색 라인 버튼 -> 클릭시 색상 반전
@Composable
fun PointPinkLineBtn(buttonText: String, onClick: () -> Unit) {
    var isClicked by remember { mutableStateOf(false) }

    val buttonColor = if (isClicked) PointPink else Color.White
    val textColor = if (isClicked) Color.White else PointPink

    Box(
        modifier = Modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        Box(
            modifier = Modifier
                .background(buttonColor, shape = RoundedCornerShape(50.dp))
                .border(2.dp, PointPink, RoundedCornerShape(50.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        isClicked = !isClicked
                        onClick()
                    }
                )
                .height(50.dp)
                .padding(horizontal = 24.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = buttonText,
                color = textColor,
                style = AchuTheme.typography.semiBold18White
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Modifier.drawColoredShadow(
    color: Color,           // 그림자의 색상
    alpha: Float = 1.0f,    // 그림자의 투명도 (기본값은 0.2f)
    borderRadius: Dp = 0.dp,// 그림자의 경계 둥글기 (기본값은 0.dp)
    shadowRadius: Dp = 20.dp,// 그림자의 크기 (기본값은 20.dp)
    offsetY: Dp = 0.dp,     // 그림자의 Y축 이동 (기본값은 0.dp)
    offsetX: Dp = 0.dp      // 그림자의 X축 이동 (기본값은 0.dp)
) = this.drawBehind {
    // 투명도 설정을 위한 색상 계산
    val transparentColor = android.graphics.Color.toArgb(color.copy(alpha = 0.0f).value.toLong())

    // 그림자의 색상을 계산 (alpha값을 적용)
    val shadowColor = android.graphics.Color.toArgb(color.copy(alpha = alpha).value.toLong())

    this.drawIntoCanvas {
        // 그림자 효과를 그리기 위한 Paint 객체
        val paint = Paint()
        val frameworkPaint = paint.asFrameworkPaint()

        // 그림자 효과의 색상과 투명도를 설정
        frameworkPaint.color = transparentColor
        frameworkPaint.setShadowLayer(
            shadowRadius.toPx(),    // 그림자 크기
            offsetX.toPx(),         // X축 오프셋
            offsetY.toPx(),         // Y축 오프셋
            shadowColor             // 그림자의 색상
        )

        // 그림자를 둥글게 그리고, 그림자 레이어를 적용
        it.drawRoundRect(
            0f,
            0f,
            this.size.width,
            this.size.height,
            borderRadius.toPx(),   // 둥글기
            borderRadius.toPx(),   // 둥글기
            paint
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun basicButton() {

    AchuTheme {  // ✅ AchuTheme을 감싸줌
//        PointBlueButton("수정") { }
        PointPinkBtn("확인") { }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun basicButton2() {

    AchuTheme {  // ✅ AchuTheme을 감싸줌
        PointBlueButton("수정") { }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview(showBackground = true)
@Composable
fun basicButton3() {

    AchuTheme {  // ✅ AchuTheme을 감싸줌
        PointBlueFlexibleBtn("수정") { }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun basicButton4() {

    AchuTheme {  // ✅ AchuTheme을 감싸줌
        PointPinkFlexibleBtn("수정") { }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun basicButton5() {

    AchuTheme {  // ✅ AchuTheme을 감싸줌
        PointBlueLineBtn("수정") { }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun basicButton6() {

    AchuTheme {  // ✅ AchuTheme을 감싸줌
        PointPinkLineBtn("수정") { }
    }
}

