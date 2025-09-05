/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import appLanguageState
import coil3.compose.setSingletonImageLoaderFactory
import com.voc.joinjoint.ui.navigation.RootFrame
import com.voc.joinjoint.utils.AppLanguage
import com.voc.joinjoint.utils.AppLanguage.Companion.AppLanguageInstance
import com.voc.joinjoint.utils.JoinJointTheme
import globalDensity
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import joinjoint.composeapp.generated.resources.Res
import joinjoint.composeapp.generated.resources.compose_multiplatform
import platformContext
import kotlin.collections.contains

// Application-wide variables
lateinit var platformContext: ContextFactory
lateinit var globalDensity: Density
lateinit var appLanguageState : MutableState<AppLanguage.AppLanguageEnum>

@Composable
@Preview

/**
 * - The door of the app, only should put things that need to initialize at the beginning of the app start
 * - Handling and deciding which display case it is (Horizontal / Landscape) , (Pad Mode ? Phone Mode?)
 * - Display specific screen as the login in Figma Design expect
 */
fun App(platformContextFactory: ContextFactory) {
    globalDensity = LocalDensity.current // Not yet applied to the app
    platformContext = platformContextFactory
    setSingletonImageLoaderFactory { context ->
        newImageLoader(
            context,
            arrayOf("DEV").contains(BuildKonfig.appProfile)
        )
    }

    // 用 State 管理語言
    if( !::appLanguageState.isInitialized) {
        appLanguageState = remember { mutableStateOf(AppLanguageInstance) }
    }

    // Not useful for now, keep it for future if need to support RTL language
    val direction = remember(appLanguageState.value) {
        mutableStateOf(LayoutDirection.Ltr)
    }

    CompositionLocalProvider(LocalLayoutDirection provides direction.value) {
        JoinJointTheme{
            // DoItLater: Initialize Other functions (Not for data grabbing)
            RootFrame()
        }

    }
}