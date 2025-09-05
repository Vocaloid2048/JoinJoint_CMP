/*
 * Created by Voc-夜芷冰 (Vocaloid2048)
 * Copyright © 2025 . All rights reserved.
 */

package com.voc.joinjoint.utils

/**
 * This is a class that reminding devs to do something later.
 * @param whatHaveToDo What have to do later.
 */

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.LOCAL_VARIABLE
)
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class DoItLater(val whatHaveToDo : String)


/**
 * This is a class that reminding devs finish app-translation task for that part.
 */

@Target(
    AnnotationTarget.CLASS,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.FUNCTION,
    AnnotationTarget.EXPRESSION,
    AnnotationTarget.LOCAL_VARIABLE
)
@Repeatable
@Retention(AnnotationRetention.SOURCE)
annotation class TranslationPls

/**
 * This is a class that reminding devs to do checking
 * During each Version Update.
 */
annotation class VersionUpdateCheck

/**
 * Once you see this, which means the reviewer/ author think it is tooo complex and messy!
 */
annotation class YouMustKiddingMe
