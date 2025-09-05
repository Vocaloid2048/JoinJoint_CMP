/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.utils

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavBackStackEntry
import com.voc.joinjoint.setKeyboardDarkMode
import joinjoint.composeapp.generated.resources.Res
import joinjoint.composeapp.generated.resources.sarasa_gothic_tc_regular
import kotlin.math.roundToInt

/**
 * Color Constants
 */

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
)

/**
 * Theme Constants
 */
@Composable
fun JoinJointTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    if(darkTheme) setKeyboardDarkMode()
    MaterialTheme(
        colorScheme = colorScheme,
        typography = AppTypography(),
        content = content
    )
}

/**
 * Set up the global font family for the app
 * ref: https://medium.com/@aleksandr.komolkin/styling-your-compose-multiplatform-app-a-guide-to-fonts-and-themes-f89187a48c77
 */
@Composable
fun AppTypography() : Typography{
    val sarasaGothicTC = FontFamily(org.jetbrains.compose.resources.Font(Res.font.sarasa_gothic_tc_regular))

    return Typography(
        headlineLarge = TextStyle(
            fontFamily = sarasaGothicTC,
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp
        ),
        bodyMedium = TextStyle(
            fontFamily = sarasaGothicTC,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp
        ),
        displaySmall = TextStyle(
            fontFamily = sarasaGothicTC,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
    )
}

@DoItLater("Add font size text style")

/**
 * Bezier Easing for the app, suggested by 2O48, the designer of JoinJoint
 */
val BezierEasing2O48 = CubicBezierEasing(0.3f, 0f, 0.3f, 1f)

/**
 * Navigation Animation Transitions
 */
object AppNavTransitions {
    val enterTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            )
        )
    }
    val exitTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.Start,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            ),
            targetOffset = { fullOffset -> (fullOffset * 0.3f).toInt() }
        )
    }
    val popEnterTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition = {
        slideIntoContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.End,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            ),
            initialOffset = { fullOffset -> (fullOffset * 0.3f).toInt() }
        )
    }
    val popExitTransition:
            AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition = {
        slideOutOfContainer(
            towards = AnimatedContentTransitionScope.SlideDirection.End,
            animationSpec = tween(
                durationMillis = 250,
                easing = if(isIosPlatform()) LinearEasing else BezierEasing2O48
            )
        )
    }
    val sizeTransform:
            (AnimatedContentTransitionScope<NavBackStackEntry>.() -> SizeTransform?)? = null
}

/**
 * Convert Px to Dp
 */
fun pxToDp(px : Int, density: Float) : Dp {
    return Dp(px / density)
}

/**
 * Convert Dp to Px
 */
fun DpToPx(dp : Dp, density: Float) : Int {
    return (dp.value * density).roundToInt()
}
