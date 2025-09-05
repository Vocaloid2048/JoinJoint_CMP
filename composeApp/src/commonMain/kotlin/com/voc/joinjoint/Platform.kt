package com.voc.joinjoint

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform