package nl.joozd.tripfilter

class TripPageContents(private val page: List<String>){
    override fun toString(): String =
        page.joinToString("\n")

    /**
     * The hash is the entire string without linebreaks
     */
    val dutyBlockStringHash = buildDutyBlockString(page)

    /**
     *
     */
    private fun buildDutyBlockString(lines: List<String>) = buildString {
        val iterator = lines.iterator()
        // fails hard on bad data
        while(iterator.next() != DUTY_BLOCK_START_LINE){ /* do nothing, just consume line */ }
        var currentLine = iterator.next()
        while(currentLine != TRIP_STATS_START_LINE){
            append(currentLine.removeTrailingDigitAfterTime() + "\n")
//            currentLine.extractAndJoinTriplets().takeIf{it.isNotBlank()}?.let { append(it + "\n") }
            currentLine = iterator.next()
        }
    }

    companion object{
        private const val DUTY_BLOCK_START_LINE = "Check in Duty Block Check out Rest Schedule"
        private const val TRIP_STATS_START_LINE = " Trip statistics"

    }
}