/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint

import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import com.voc.joinjoint.utils.DeviceInfo
import com.voc.joinjoint.utils.DoItLater
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.darwin.Darwin
import okio.Path.Companion.toPath
import platform.Foundation.NSApplicationSupportDirectory
import platform.Foundation.NSBundle
import platform.Foundation.NSFileManager
import platform.Foundation.NSURL
import platform.Foundation.NSUserDefaults
import platform.Foundation.NSUserDomainMask
import platform.UIKit.UIApplication
import platform.UIKit.UIDevice
import platform.UIKit.UIKeyboardAppearanceDark
import platform.UIKit.UITextField

actual fun getDeviceInfo(): DeviceInfo = DeviceInfo(
    deviceModel = UIDevice.currentDevice.model(),
    deviceOSName = UIDevice.currentDevice.systemName(),
    deviceOSVersion = UIDevice.currentDevice.systemVersion()
)

@DoItLater("iOS testing")
@Composable
actual fun setKeyboardDarkMode() {
    SideEffect {
        val textField = UITextField()
        textField.keyboardAppearance = UIKeyboardAppearanceDark
        textField.becomeFirstResponder()
        textField.resignFirstResponder()
    }
}

actual fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient{
    return HttpClient(engineFactory = Darwin, block = function)
}

actual fun changeLanguage(language: String, region : String?) {
    val lang = if(region != null && language.lowercase() == "zh") {
        "$language-$region"
    }else {
        language
    }
    NSUserDefaults.standardUserDefaults.setObject(arrayListOf(lang),"AppleLanguages")
}


//iOS will never use this
actual class ContextFactory {
    // Bundle allows you to lookup resources
    actual fun getContext(): Any = NSBundle
    // UIApplication allows you to access all app info
    actual fun getApplication(): Any = UIApplication
    // RootViewController can be used to identify your current screen
    actual fun getActivity(): Any = UIApplication.sharedApplication.keyWindow?.rootViewController ?: ""
}

actual fun getAppSpecificDirectory(): okio.Path {
    val fileManager = NSFileManager.defaultManager()
    val urls = fileManager.URLsForDirectory(NSApplicationSupportDirectory, NSUserDomainMask)
    val appSupportDir = urls.last() as NSURL
    return appSupportDir.path?.toPath() ?: throw IllegalStateException("Could not get the path for app support directory")
}