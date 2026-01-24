package com.sequenia.kmp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform