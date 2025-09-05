/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.ui.navigation

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.layout.positionInWindow
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.platform.UriHandler
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.window.core.layout.WindowWidthSizeClass
import com.russhwolf.settings.Settings
import com.voc.joinjoint.ui.screens.HomePage
import com.voc.joinjoint.utils.AppLanguage
import com.voc.joinjoint.utils.AppNavTransitions
import com.voc.joinjoint.utils.INFO_MAX_WIDTH
import kotlinx.datetime.Clock

// Only use if it is not allowed to pass NavController by parameter
lateinit var navigatorInstance : NavHostController

// In case if need to open URL externally
lateinit var urlHandler: UriHandler

// To decide the current window size class
var globalWindowWidthSizeClass: WindowWidthSizeClass = WindowWidthSizeClass.COMPACT

private val doAppInit = mutableStateOf(false)

/**
 * Run the set of initialization of static data before enter to the HomePage
 */
@Composable
fun initStaticData() {

}

/**
 * Check if the current window is in Pad mode.
 * - Pad mode is when the window is in landscape mode, also not phone.
 * @return True if the window is in Pad mode, false otherwise.
 */
@Composable
fun isPadMode(): Boolean {
    return when (globalWindowWidthSizeClass) {
        //Phone, Pad that is not in landscape mode
        WindowWidthSizeClass.COMPACT -> {
            false
        }
        WindowWidthSizeClass.MEDIUM -> {
            //Due to the request from our designer 2O48
            //There have to allow Pad in portrait mode of iPad, etc.
            //getOrientation() == Orientation.Horizontal
            true
        }
        //Pad in landscape mode
        WindowWidthSizeClass.EXPANDED -> {
            true
        }
        else -> {
            false
        }
    }
}

/**
 * Root Frame of the app.
 * Enter point graph :
 * - App.kt -> RootFrame -> navHostInit(navBuilder) -> Pages
 */
@Composable
fun RootFrame() {
    val isPadMode = remember { mutableStateOf(false) }
    val isRotate = remember { mutableStateOf(false) }
    val navigator = rememberNavController()
    val focusManager = LocalFocusManager.current
    navigatorInstance = navigator
    urlHandler = LocalUriHandler.current

    key(isRotate.value) {
        globalWindowWidthSizeClass = currentWindowAdaptiveInfo().windowSizeClass.windowWidthSizeClass
        isPadMode.value = isPadMode()
        AppLanguage().setAppLanguage()
    }

    key(doAppInit.value){
        if (!doAppInit.value){
            initStaticData()
            doAppInit.value = true
        }
    }

    val screenWidth = remember { mutableStateOf(INFO_MAX_WIDTH) }
    val homePageWithCutOutWidth = remember { mutableStateOf(0.dp) }
    val densityC = LocalDensity.current
    val density = LocalDensity.current.density
    Scaffold(
        modifier = Modifier.onSizeChanged { isRotate.value = !isRotate.value },
        //snackbarHost = { SnackbarHost(snackbarInstance, modifier = Modifier.navigationBarsPadding()) }
    ) {
        BoxWithConstraints {
            screenWidth.value = maxWidth

            // DoItLater: Add Pad Mode Support

            Box {
                navHostInit(navigatorInstance, isPadMode)
            }
        }
    }
}

/**
 * Navigation Host Initialization
 * - Since Pad Mode / iOS Version will have different navigation animation
 * - So will need to keep
 * - Only contains the top-level navigation host
 */
@Composable
fun navHostInit(navigator : NavHostController, isPadMode: MutableState<Boolean>){
    // DoItLater: Adapt for Pad Mode

    NavHost(
        navController = navigator,
        startDestination = HomeRoute,
        enterTransition = AppNavTransitions.enterTransition,
        exitTransition = AppNavTransitions.exitTransition ,
        popEnterTransition = AppNavTransitions.popEnterTransition ,
        popExitTransition = AppNavTransitions.popExitTransition ,
        builder = navBuilder(isPadMode, navigator)
    )
}

/**
 * Navigation Graph Builder
 * - For registing all the routes in the app
 */
fun navBuilder(isPadMode: MutableState<Boolean>, navigator: NavHostController) : NavGraphBuilder.() -> Unit = {
    composable<HomeRoute> {
        HomePage(navigator = navigator)
    }
}

/**
 * Navigate to a route with a limited interval.
 */
fun NavHostController.navigateLimited(route: Any, options: NavOptions? = null) {
    val navigationInterval: Long = 500 // 500ms is enough for most cases
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        navigate(route, options)
        Settings().putLong("lastNavigationTime", currentTime)
    }
}

fun NavHostController.navigateLimited(route: Any, builder: NavOptionsBuilder.() -> Unit) {
    val navigationInterval: Long = 500 // 500ms is enough for most cases
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        navigate(route, builder)
        Settings().putLong("lastNavigationTime", currentTime)
    }
}

fun NavHostController.popBackStackLimited() {
    val navigationInterval: Long = 500 // 500ms is enough for most cases
    val lastNavigationTime: Long = Settings().getLong("lastNavigationTime", 0)

    val currentTime = Clock.System.now().toEpochMilliseconds()
    if (currentTime - lastNavigationTime >= navigationInterval) {
        popBackStack()
        Settings().putLong("lastNavigationTime", currentTime)
    }
}
