package com.vengat.calculator.di

import com.vengat.calculator.core.ExpressionEvaluator
import com.vengat.calculator.core.StandardMathExpressionEvaluator
import com.vengat.calculator.presentation.CalculatorViewModel
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

expect val platformModule: Module

val appModule = module {
    includes(platformModule)

    viewModel { CalculatorViewModel(get()) }
    single<ExpressionEvaluator> { StandardMathExpressionEvaluator() }
}

fun initializeKoin(platModule: Module? = null) {
    startKoin {
        modules(appModule)
        platModule?.let {
            modules(platModule)
        }
    }
}