package com.vengat.calculator

import android.app.Application
import com.vengat.calculator.di.initializeKoin
import com.vengat.calculator.di.platformModule

class CalculatorApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initializeKoin(platformModule)
    }
}