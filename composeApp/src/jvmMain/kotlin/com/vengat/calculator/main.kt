package com.vengat.calculator

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.vengat.calculator.di.initializeKoin
import com.vengat.calculator.di.platformModule

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "CMPCalculator",
    ) {
        initializeKoin(platformModule)
        App()
    }
}