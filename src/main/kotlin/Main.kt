package nl.joozd

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import nl.joozd.ui.screens.MainScreen
import java.io.File


fun main() {
    try {
        application {
            Window(onCloseRequest = ::exitApplication, title = "Mijn Script UI") {
                // geef de onderliggende AWT Window aan MainScreen door
                MainScreen(window)
            }
        }
    } catch (e: Throwable) {
        File("launch-error.log").printWriter().use { it.println(e.stackTraceToString()) }
        throw e
    }

}

