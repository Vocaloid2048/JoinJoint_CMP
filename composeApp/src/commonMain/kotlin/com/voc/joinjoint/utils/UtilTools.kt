/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.utils

import androidx.compose.runtime.Composable
import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import coil3.util.DebugLogger
import com.voc.joinjoint.getDeviceInfo
import kotlinx.serialization.Serializable
import okio.FileSystem
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Serializable
data class AppInfo(
    val appProfile: String = "BETA",
    val appVersionName: String = "0.0.1",
    val appVersionCode: Int = 1,
){
    companion object{
        val AppInfoInstance = AppInfo(
            BuildKonfig.appProfile,
            BuildKonfig.appVersionName, BuildKonfig.appVersionCode)
    }
}

@Serializable
data class DeviceInfo(
    val deviceModel: String = "Unknown",  //DUMMY-A1000
    val deviceOSName: String = "Unknown", //Android
    val deviceOSVersion: String = "Unknown", //34
)

// ImageLoader

/**
 * Image Loader, Max Cache Size: 1GB (Since Images already reach 400MB in Stargazer 2.7.0)
 */
fun newImageLoader(context : PlatformContext, isDebug: Boolean = false): ImageLoader = ImageLoader.Builder(context)
    .networkCachePolicy(CachePolicy.ENABLED)
    .diskCachePolicy(CachePolicy.ENABLED)
    .diskCache {
        DiskCache.Builder()
            .maxSizeBytes(1024L * 1024 * 1024)
            .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY.resolve("image_cache"))
            .build()
    }
    .crossfade(true)
    .logger(if(isDebug) {DebugLogger()} else null)
    .build()

/**
 * Image Request
 */
fun newImageRequest(context: PlatformContext, data: Any, crossFade : Boolean = true) = ImageRequest.Builder(context)
    .data(data)
    .networkCachePolicy(CachePolicy.ENABLED)
    .crossfade(crossFade)
    .diskCachePolicy(CachePolicy.ENABLED)
    .build()

/**
 * Get String from StringResource
 * Remove the prefix and suffix quotes
 */
@Composable
fun resToStr(stringResource: StringResource) : String{
    return stringResource(stringResource).removePrefix("\"").removeSuffix("\"")
}

/**
 * Replace sth like ${1} to your value
 */
fun String.replaceStrRes(newValue: String, index : Int = 1) : String{
    return this.replace("\${$index}", newValue)
}

/**
 * Boolean to Int
 */
fun Boolean.toInt() = if (this) 1 else 0

/**
 * Int to Boolean
 */
fun Int.toBoolean() = this != 0

// Platforms

fun isIosPlatform(): Boolean {
    return listOf("iOS", "iPadOS").contains(getDeviceInfo().deviceOSName)
}

fun isAndroidPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Android")
}

fun isMacOSPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Mac")
}

fun isWindowsPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Windows")
}

fun isLinuxPlatform(): Boolean {
    return getDeviceInfo().deviceOSName.contains("Linux")
}

fun isProductionEnv() : Boolean {
    return BuildKonfig.appProfile == "PRODUCTION" || BuildKonfig.appProfile == "PRODUCTION_GP"
}
fun isBetaEnv() : Boolean {
    return BuildKonfig.appProfile == "BETA" || BuildKonfig.appProfile == "C.BETA"
}
fun isDevEnv() : Boolean {
    return BuildKonfig.appProfile == "DEV"
}
