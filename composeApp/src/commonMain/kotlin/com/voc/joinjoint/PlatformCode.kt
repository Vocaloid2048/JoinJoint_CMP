/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint

import androidx.compose.runtime.Composable
import com.voc.joinjoint.utils.DeviceInfo
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig


expect fun getDeviceInfo(): DeviceInfo

@Composable
expect fun setKeyboardDarkMode()

expect fun getLocalHttpClient(function: HttpClientConfig<*>.() -> Unit): HttpClient

expect fun getAppSpecificDirectory(): okio.Path

//ref: https://medium.com/@robert.jamison/passing-android-context-in-kmp-jetpack-compose-8de5b5de7bdd
expect class ContextFactory {
    fun getContext(): Any
    fun getApplication(): Any
    fun getActivity(): Any
}

expect fun changeLanguage(language: String, region: String? = null)