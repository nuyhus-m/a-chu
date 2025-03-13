package com.ssafy.achu.core.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.ssafy.achu.R

// Set of Material typography styles to start with
val pretandard = FontFamily(
    Font(R.font.pretendardbold, FontWeight.Bold),
    Font(R.font.pretendardsemibold, FontWeight.SemiBold),
    Font(R.font.pretendardregular, FontWeight.Normal),
)


val myTypography = MyFont(

    bold24 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    ),

    semiBold20 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
    ),
    semiBold20White = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        color = Color.White
    ),

    semiBold18 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
    ),

    semiBold18White = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = Color.White
    ),

    semiBold18Pink = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 18.sp,
        color = FontPink
    ),

    semiBold16 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
    ),
    
    semiBold16Pink = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = FontPink
    ),

    semiBold16White = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp,
        color = White
    ),

    semiBold12 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
    ),

    semiBold12LightGray = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = LightGray
    ),

    semiBold12PointBlue = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = PointBlue
    ),

    semiBold12FontGray = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.SemiBold,
        fontSize = 12.sp,
        color = FontGray,
    ),

    regular18 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
    ),
    regular16 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    regular14 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    regular12 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    )

    )

@Immutable
data class MyFont(
    val bold24: TextStyle,
    val semiBold20: TextStyle,
    val semiBold20White: TextStyle,
    val semiBold18: TextStyle,
    val semiBold18White: TextStyle,
    val semiBold18Pink: TextStyle,
    val semiBold16: TextStyle,
    val semiBold16Pink: TextStyle,
    val semiBold16White: TextStyle,
    val semiBold12: TextStyle,
    val semiBold12LightGray: TextStyle,
    val semiBold12FontGray: TextStyle,
    val semiBold12PointBlue: TextStyle,
    val regular18: TextStyle,
    val regular16: TextStyle,
    val regular14: TextStyle,
    val regular12: TextStyle,
) {

}