/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.utils

import com.russhwolf.settings.Settings
import com.voc.joinjoint.appLanguageState
import com.voc.joinjoint.changeLanguage

@DoItLater("Add Get/Setter function, Instances and Preference I/O")
class AppLanguage(){
    enum class AppLanguageEnum(var localeName: String, var localeCode: String) {
        CANTONESE("粵語", "zh-HK"),
        EN("English", "en-US"),
        ZH_CN("简体中文", "zh-CN"),
        ZH_HK("繁體中文", "zh-TW")
    }

    companion object{
        var AppLanguageInstance = AppLanguageEnum.valueOf(Settings().getString("appLanguage", AppLanguageEnum.EN.name))
    }

    fun setAppLanguage(lang: AppLanguageEnum = AppLanguageInstance, isFirstInit: Boolean = false){
        Settings().putString("appLanguage", lang.name)
        val langC = lang.localeCode.split("-")
        changeLanguage(langC[0], langC.getOrNull(1))
        AppLanguageInstance = lang
        appLanguageState = appLanguageState.apply {
            value = lang
        }
    }

    fun getAppLangLocaleNameList(): ArrayList<String> {
        val langLocaleNameList = arrayListOf<String>()
        AppLanguageEnum.entries.forEach { language ->
            langLocaleNameList.add(language.localeName)
        }

        return langLocaleNameList
    }

    fun getAppLangEnumList(): ArrayList<AppLanguageEnum> {
        val appLangList = arrayListOf<AppLanguageEnum>()
        AppLanguageEnum.entries.forEach { language ->
            appLangList.add(language)
        }

        return appLangList
    }
}