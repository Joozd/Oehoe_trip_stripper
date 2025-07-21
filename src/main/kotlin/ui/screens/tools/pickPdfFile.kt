package nl.joozd.ui.screens.tools

import java.io.File
import javax.swing.JFileChooser
import javax.swing.filechooser.FileNameExtensionFilter

/**
 * Open een native file-chooser voor PDF-bestanden.
 *
 * @param initialDirectory De map die opent bij het starten van de dialoog.
 *                         Als `null` wordt de standaard map van de OS-chooser gebruikt.
 * @param saveDialog       Als `true` opent een “Save” dialoog, anders een “Open” dialoog.
 * @return Het geselecteerde bestand, of `null` als de gebruiker annuleert.
 */
fun pickPdfFile(
    initialDirectory: File? = null,
    saveDialog: Boolean = false
): File? {
    val chooser = JFileChooser().apply {
        fileFilter = FileNameExtensionFilter("PDF bestanden", "pdf")
        dialogTitle = if (saveDialog) "Kies doelbestand" else "Selecteer input PDF"
        approveButtonText = if (saveDialog) "Bewaar" else "Selecteer"
        initialDirectory?.let { currentDirectory = it }
    }
    val result = if (saveDialog) {
        chooser.showSaveDialog(null)
    } else {
        chooser.showOpenDialog(null)
    }

    return if (result == JFileChooser.APPROVE_OPTION) chooser.selectedFile else null
}
