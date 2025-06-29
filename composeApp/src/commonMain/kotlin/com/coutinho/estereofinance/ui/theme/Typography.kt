package com.coutinho.estereofinance.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import estereofinancekmp.composeapp.generated.resources.Res
import estereofinancekmp.composeapp.generated.resources.SpaceGrotesk_Bold
import estereofinancekmp.composeapp.generated.resources.SpaceGrotesk_Light
import estereofinancekmp.composeapp.generated.resources.SpaceGrotesk_Medium
import estereofinancekmp.composeapp.generated.resources.SpaceGrotesk_Regular
import estereofinancekmp.composeapp.generated.resources.SpaceGrotesk_SemiBold
import org.jetbrains.compose.resources.Font

val spaceGroteskFamily @Composable get() = FontFamily(
    Font(
        resource = Res.font.SpaceGrotesk_Light,
        weight = FontWeight.Light,
    ),
    Font(
        resource = Res.font.SpaceGrotesk_Regular,
        weight = FontWeight.Normal,
    ),
    Font(
        resource = Res.font.SpaceGrotesk_Medium,
        weight = FontWeight.Medium,
    ),
    Font(
        resource = Res.font.SpaceGrotesk_Bold,
        weight = FontWeight.Bold,
    ),
    Font(
        resource = Res.font.SpaceGrotesk_SemiBold,
        weight = FontWeight.SemiBold,
        ),
    )

val Typo: Typography @Composable get() = Typography(
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