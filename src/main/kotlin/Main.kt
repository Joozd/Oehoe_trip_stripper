package nl.joozd

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import nl.joozd.ui.screens.MainScreen


fun main() = application {
    Window(onCloseRequest = ::exitApplication, title = "Mijn Script UI") {
        // geef de onderliggende AWT Window aan MainScreen door
        MainScreen(window)
    }
}
