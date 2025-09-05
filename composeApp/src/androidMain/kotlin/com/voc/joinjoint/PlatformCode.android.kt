/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint

import android.app.Activity
import android.content.Context
import android.os.Build
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.core.view.WindowInsetsControllerCompat
import com.voc.joinjoint.utils.DeviceInfo
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import okio.Path
import okio.Path.Companion.toPath
import java.util.Locale

actual fun getDeviceInfo(): DeviceInfo = DeviceInfo(
    deviceModel = Build.MODEL,
    deviceOSName = "Android",
    deviceOSVersion = Build.VERSION.SDK_INT.toString()
)

@Composable
actual fun setKeyboardDarkMode() {
    val context = LocalContext.current
    val keyboardController = LocalSoftwareKeyboardController.current

    SideEffect {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val activity = context as? Activity
            val window = activity?.window
            if (window != null) {
                val windowInsetsController = WindowInsetsControllerCompat(window, window.decorView)
                windowInsetsController.isAppearanceLightNavigationBars = false
            }
        } else {
            //Nothing will do
        }
    }
}

actual fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient{
    return HttpClient(engineFactory = CIO, block = function)
}

actual fun changeLanguage(language: String, region: String?) {
    val locale = if(region == null) Locale(language) else Locale(language, region)
    Locale.setDefault(locale)
}

actual fun getAppSpecificDirectory(): Path {
    val context: Context = platformContext.getContext() as Context
    return context.filesDir.absolutePath.toPath()
}

actual class ContextFactory(private val activity: ComponentActivity) {
    actual fun getContext(): Any = activity.baseContext
    actual fun getApplication(): Any = activity.application
    actual fun getActivity(): Any = activity
}