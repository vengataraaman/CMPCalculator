package com.vengat.calculator

import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyShortcut
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import cmpcalculator.composeapp.generated.resources.Res
import cmpcalculator.composeapp.generated.resources.calc_icon
import com.vengat.calculator.di.initializeKoin
import com.vengat.calculator.di.platformModule
import org.jetbrains.compose.resources.painterResource

fun main() {
    initializeKoin(platformModule)

    application {
        println("Starting application...")
        Window(
            onCloseRequest = ::exitApplication,
            title = "CMPCalculator",
            state = rememberWindowState(width = 400.dp, height = 840.dp),
            icon = painterResource(Res.drawable.calc_icon),
            undecorated = false // Set to true to hide the title bar
        ) {
//            MenuBar {
//                Menu("File", mnemonic = 'F') {
//                    Item("Exit", shortcut = KeyShortcut(Key.Q, ctrl = true), onClick = ::exitApplication)
//                    Separator()
//                    Item("About", onClick = {
//                        // Handle the about action
//                    })
//                }
//            }
            App()
        }
    }
}