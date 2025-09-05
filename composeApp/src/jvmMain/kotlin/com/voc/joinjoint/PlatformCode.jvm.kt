/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint

import androidx.compose.runtime.Composable
import com.voc.joinjoint.utils.DeviceInfo
import com.voc.joinjoint.utils.DoItLater
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.cio.CIO
import okio.Path
import okio.Path.Companion.toPath
import java.util.Locale

actual fun getDeviceInfo(): DeviceInfo {
    //Return a DeviceInfo object that contains suitable OS version, device name data
    return DeviceInfo("Unspecified", System.getProperty("os.name"), System.getProperty("os.version"))
}

@Composable
actual fun setKeyboardDarkMode() {
    //Nothing will do since it even don't have virtual keyboard!
}

actual fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient{
    return HttpClient(engineFactory = CIO, block = function)
}

actual fun changeLanguage(language: String, region : String?) {
    val locale = if(region == null) Locale(language) else Locale(language, region)
    Locale.setDefault(locale)
}

@DoItLater("Desktop Apply To AppData / Applicaton Support Directory")
actual fun getAppSpecificDirectory(): Path {
    val userHome = System.getProperty("user.home")
    return "$userHome/.Stargazer3".toPath()
}

//Desktop will never use this
actual class ContextFactory {
    actual fun getContext(): Any {
        TODO("Not yet implemented")
    }

    actual fun getApplication(): Any {
        TODO("Not yet implemented")
    }

    actual fun getActivity(): Any {
        TODO("Not yet implemented")
    }
}