package com.ssafy.achu.core.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
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
    Font(R.font.pretendard_medium,FontWeight.Medium)
)


internal val Typography = MyTypography(

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
    ),
    medium18 = TextStyle(
        fontFamily = pretandard,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
    ),

)

@Immutable
data class MyTypography(
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
    val medium18: TextStyle,
)

val LocalTypography = staticCompositionLocalOf {
    MyTypography(
        bold24 = TextStyle.Default,
        semiBold20 = TextStyle.Default,
        semiBold20White = TextStyle.Default,
        semiBold18 = TextStyle.Default,
        semiBold18White = TextStyle.Default,
        semiBold18Pink = TextStyle.Default,
        semiBold16 = TextStyle.Default,
        semiBold16Pink = TextStyle.Default,
        semiBold16White = TextStyle.Default,
        semiBold12 = TextStyle.Default,
        semiBold12LightGray = TextStyle.Default,
        semiBold12FontGray = TextStyle.Default,
        semiBold12PointBlue = TextStyle.Default,
        regular18 = TextStyle.Default,
        regular16 = TextStyle.Default,
        regular14 = TextStyle.Default,
        regular12 = TextStyle.Default,
        medium18 = TextStyle.Default,

    )
}