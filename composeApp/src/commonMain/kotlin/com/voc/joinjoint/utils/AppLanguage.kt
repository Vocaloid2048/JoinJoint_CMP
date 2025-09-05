/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.utils

@DoItLater("Add Get/Setter function, Instances and Preference I/O")
class AppLanguage(){
    enum class AppLanguageEnum(var localeName: String, var localeCode: String) {
        CANTONESE("粵語", "zh-HK"),
        EN("English", "en-US"),
        ZH_CN("简体中文", "zh-CN"),
        ZH_HK("繁體中文", "zh-TW")
    }
}