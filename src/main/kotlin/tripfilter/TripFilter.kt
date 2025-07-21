package nl.joozd.tripfilter

import com.itextpdf.text.Document
import com.itextpdf.text.pdf.PdfCopy
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.parser.PdfTextExtractor
import com.itextpdf.text.pdf.parser.SimpleTextExtractionStrategy
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

/**
 * Extracts all pages whose TripPageContents hash is unique
 * and writes them in order into [targetFile].
 *
 * @receiver the source PdfReader
 * @param targetFile the file to write the filtered PDF into
 */
suspend fun PdfReader.filterUniqueTrips(targetFile: File, progressCallBack: (Float) -> Unit): String = withContext(Dispatchers.IO) {
    val reader = this@filterUniqueTrips
    // wrap only the FileOutputStream in 'use' (PdfCopy itself isn't Closeable)
    progressCallBack(0.0f)

    var written = 0

    FileOutputStream(targetFile).use { fos ->
        val document = Document()
        val copy = PdfCopy(document, fos)
        document.open()

        val visited = HashSet<String>(numberOfPages)


        for (i in 1..numberOfPages) {
            val lines = PdfTextExtractor
                .getTextFromPage(reader, i, SimpleTextExtractionStrategy())
                .lines()

            val contents = TripPageContents(lines)
                .takeIf { it.toString().isNotBlank() }
                ?: continue

            if (visited.add(contents.dutyBlockStringHash)) {
                val imported = copy.getImportedPage(reader, i)
                copy.addPage(imported)
                written++
            }
            progressCallBack(i.toFloat() / numberOfPages)
        }

        document.close()  // closes PdfCopy internally

        progressCallBack(1.0f)

    }
    reader.close()


    //return:
    "Before: $numberOfPages\nAfter: $written"


}
