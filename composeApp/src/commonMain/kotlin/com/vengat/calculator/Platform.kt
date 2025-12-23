package com.vengat.calculator

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform