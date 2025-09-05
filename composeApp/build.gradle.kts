/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

import com.android.build.gradle.internal.api.ApkVariantOutputImpl
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Properties

//BETA | C.BETA | DEV | PRODUCTION
//VersionUpdateCheck
val isForAppStore = true
val isForPlayStore = false
var appProfile = "BETA" //Please Modify this String ONLY IF NECESSERY
val appVersionCodeName = "Herta"
val appVersionDesktop = "1.0.6"

/**
 * tasks to gradle.properties
 */
val properties = Properties()
file("../gradle.properties").inputStream().use { properties.load(it) }

val appVersion: String = SimpleDateFormat("yyyy.MM.dd").format(Date())
val versionCodeFinal = properties.getProperty("APP_VERSION_CODE").toInt() + 1
//val schemeProfile: String? = System.getenv("SCHEME_PROFILE")
if(isForPlayStore) appProfile = "PRODUCTION_GP"

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val xcf = XCFramework()

    listOf(
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            binaryOption("bundleVersion", versionCodeFinal.toString())
            binaryOption("bundleShortVersionString", appVersion)
            xcf.add(this)
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }
    }
}

android {
    namespace = "com.voc.joinjoint"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.voc.joinjoint"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = versionCodeFinal
        versionName = "1.0"
    }

    flavorDimensions += "version"
    productFlavors{
        properties["APP_PLATFORM"] = "Android"

        create("0dev"){
            versionName = "DEV ${appVersion} (${versionCodeFinal})"
        }
        create("beta"){
            applicationId = "com.voc.joinjoint.beta"
            versionName = "BETA ${appVersion} (${versionCodeFinal})"
        }
        create("closeBeta"){
            versionName = "C.BETA ${appVersion} (${versionCodeFinal})"
        }
        create("production_googleplay"){
            versionName = "GP ${appVersion} (${versionCodeFinal})"
        }
        create("production"){
            applicationId = "com.voc.joinjoint"
            versionName = "${appVersion} (${versionCodeFinal})"
        }

        properties.store(file("../gradle.properties").outputStream(),null)
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    bundle {
        language {
            enableSplit = false
        }
    }

    applicationVariants.all {
        outputs.all {
            if (this is ApkVariantOutputImpl) {
                val suffix = outputFileName.split(".").last()
                outputFileName = "JoinJoint-${appProfile}-${appVersion} (${versionCodeFinal}).$suffix"
            }
        }
    }
}

val macExtraPlistKeys: String
    get() = """
      <key>ITSAppUsesNonExemptEncryption</key>
      <false/>
    """.trimIndent()

dependencies {
    debugImplementation(compose.uiTooling)
}


compose.desktop {
    application {
        mainClass = "com.voc.joinjoint.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb, TargetFormat.Exe, TargetFormat.Pkg)
            packageName = "JoinJoint${if(appProfile.contains("PRODUCTION")) "" else " ($appProfile)"}"
            packageVersion = appVersionDesktop
            copyright = "Copyright © 2025 夜芷冰 (@Vocaloid2048) 版權所有"
            description = "JoinJoint is a CMP demo app develop by 夜芷冰 (@Vocaloid2048)"
            vendor = "夜芷冰"

            val betaIconSuffix = if(
                when(appProfile){
                    "BETA" -> true
                    "C.BETA" -> true
                    "DEV" -> true
                    else -> false
                }
            ) { "_beta" } else { "" }

            linux {
                iconFile.set(project.file("icon/app_icon${betaIconSuffix}.png"))
                shortcut = true
            }
            windows {
                iconFile.set(project.file("icon/app_icon${betaIconSuffix}.ico"))
                shortcut = true
                msiPackageVersion = "1.0.$versionCodeFinal"
                menu = true
                dirChooser = true
            }
            macOS{
                packageName = "JoinJoint"
                iconFile.set(project.file("icon/app_icon${betaIconSuffix}.icns"))
                packageBuildVersion = versionCodeFinal.toString()
                //ref : https://github.com/JetBrains/compose-multiplatform/blob/master/tutorials/Signing_and_notarization_on_macOS/README.md#configuring-gradle
                bundleID = "com.voc.joinjoint"
                minimumSystemVersion = "12.0"
                infoPlist{
                    extraKeysRawXml = macExtraPlistKeys
                }
                signing {
                    appStore = isForAppStore //https://youtrack.jetbrains.com/issue/CMP-4272
                    sign.set(isForAppStore) //https://github.com/electron/notarize/issues/120#issuecomment-1605886244
                    identity.set("Chun Man Tsang")
                }

                if(isForAppStore){
                    //ref : https://youtrack.jetbrains.com/issue/CMP-2096
                    //Please don't modify the profile name to other custom name, it will send u a jpackage error :)
                    provisioningProfile.set(project.file("stores/embedded.provisionprofile"))
                    runtimeProvisioningProfile.set(project.file("stores/runtime.provisionprofile"))
                    entitlementsFile.set(project.file("stores/entitlements.plist"))
                    runtimeEntitlementsFile.set(project.file("stores/runtime-entitlements.plist"))
                }
            }
        }
    }
}

fun initGradleProperties(){
    //Write only
    properties["APP_PROFILE"] = appProfile
    properties["APP_VERSION"] = appVersion
    properties["APP_VERSION_CODENAME"] = appVersionCodeName
    properties["APP_VERSION_DESKTOP"] = appVersionDesktop
    properties["APP_VERSION_CODE"] = versionCodeFinal.toString()
    properties.store(file("../gradle.properties").outputStream(),null)
}

