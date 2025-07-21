package nl.joozd.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itextpdf.text.pdf.PdfReader
import kotlinx.coroutines.launch
import nl.joozd.tripfilter.filterUniqueTrips
import nl.joozd.ui.screens.tools.pickPdfFile
import java.awt.Window
import java.io.File

/**
 * Scherm voor:
 * 1) input-PDF selecteren
 * 2) output-bestand kiezen en de magie uitvoeren
 * 3) voortgang tonen met een cirkeltje
 * 4) bevestiging na voltooiing
 *
 * Alles gecentreerd in het scherm.
 * Bij inProgress worden alleen de voortgangscirkel en percentage getoond.
 *
 * @param window de onderliggende AWT Window, nodig voor de file-chooser
 */
@Composable
fun MainScreen(window: Window) {
    var inputFile by remember { mutableStateOf<File?>(null) }
    var outputFile by remember { mutableStateOf<File?>(null) }
    var inProgress by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var showCompletedDialog by remember { mutableStateOf(false) }
    var dialogMessage by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        if (inProgress) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(64.dp),
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(text = "${(progress * 100).toInt()}%")
            }
        } else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(onClick = {
                    val initialDir = inputFile?.parentFile
                    pickPdfFile(initialDirectory = initialDir).let { inputFile = it }
                }) {
                    Text("Kies input PDF…")
                }

                Spacer(Modifier.height(8.dp))
                Text(inputFile?.name ?: "Geen bestand geselecteerd")

                Spacer(Modifier.height(16.dp))

                Button(
                    onClick = {
                        val initialDir = inputFile?.parentFile
                        pickPdfFile(
                            initialDirectory = initialDir,
                            saveDialog = true
                        )?.let { outputFile = it }
                        if (inputFile != null && outputFile != null) {
                            scope.launch {
                                inProgress = true
                                dialogMessage = PdfReader(inputFile!!.inputStream())
                                    .filterUniqueTrips(outputFile!!) { p ->
                                        progress = p
                                    }
                                inProgress = false
                                showCompletedDialog = true
                            }
                        }
                    },
                    enabled = inputFile != null && !inProgress
                ) {
                    Text("Run magic")
                }
            }
        }
    }

    if (showCompletedDialog) {
        CompletionDialog(
            message = dialogMessage,
            onClose = { showCompletedDialog = false }
        )
    }
}
