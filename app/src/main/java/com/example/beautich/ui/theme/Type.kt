package com.example.beautich.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.beautich.R

val RedHatDisplay = FontFamily(
    Font(R.font.redhatdisplay_regular400, FontWeight.W400),
    Font(R.font.redhatdisplay_medium500, FontWeight.W500),
    Font(R.font.redhatdisplay_bold700, FontWeight.W700),
    Font(R.font.redhatdisplay_black900, FontWeight.W900)
)
val Raleway = FontFamily(
    Font(R.font.raleway_regular400, FontWeight.W400),
    Font(R.font.raleway_semibold600, FontWeight.W600),
    Font(R.font.raleway_bold700, FontWeight.W700)
)

val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = RedHatDisplay,
        fontWeight = FontWeight.W900,
        fontSize = 32.sp,
        lineHeight = 48.sp
    ),
    titleMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W700,
        fontSize = 30.sp,
        lineHeight = 45.sp
    ),
    titleSmall = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W600,
        fontSize = 25.sp,
        lineHeight = 37.sp
    ),
    headlineLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W700,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W400,
        fontSize = 20.sp,
        lineHeight = 28.sp
    ),
    headlineSmall = TextStyle(
        fontFamily = RedHatDisplay,
        fontWeight = FontWeight.W500,
        fontSize = 18.sp,
        lineHeight = 27.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = RedHatDisplay,
        fontWeight = FontWeight.W700,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = RedHatDisplay,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp,
        lineHeight = 20.sp
    ),
    bodySmall = TextStyle(
        fontFamily = RedHatDisplay,
        fontWeight = FontWeight.W400,
        fontSize = 11.sp,
        lineHeight = 15.sp
    ),
    labelLarge = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W600,
        fontSize = 17.sp,
        lineHeight = 24.sp
    ),
    labelMedium = TextStyle(
        fontFamily = RedHatDisplay,
        fontWeight = FontWeight.W500,
        fontSize = 10.sp,
        lineHeight = 14.sp
    ),
    labelSmall = TextStyle(
        fontFamily = Raleway,
        fontWeight = FontWeight.W400,
        fontSize = 13.sp,
        lineHeight = 15.sp
    )
)