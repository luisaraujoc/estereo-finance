package com.coutinho.estereof.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.coutinho.estereof.R

val spaceGroteskFamily = FontFamily(
    Font(R.font.spacegroteskregular, FontWeight.Normal),
    Font(R.font.spacegroteskmedium, FontWeight.Medium),
    Font(R.font.spacegroteskbold, FontWeight.Bold),
    Font(R.font.spacegrotesksemibold, FontWeight.SemiBold),
    Font(R.font.spacegrotesklight, FontWeight.Light)
)

val Typography = Typography(
    displayLarge = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 57.sp,
    ),
    displayMedium = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 45.sp,
    ),
    displaySmall = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 36.sp,
    ),
    headlineLarge = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 32.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 28.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 24.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 22.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
    ),
    bodyLarge = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
    ),
    bodyMedium = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    bodySmall = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelLarge = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
    ),
    labelMedium = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = spaceGroteskFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 11.sp,
    ),
)