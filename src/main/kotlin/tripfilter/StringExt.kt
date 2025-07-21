package nl.joozd.tripfilter

/**
 * Removes the digit following any time of the form "HH:MM " (two digits, colon, two digits, space)
 * so that "HH:MM D" becomes "HH:MM ".
 *
 * @receiver the string to process
 * @return a new string with the trailing digit and whitespace after each "HH:MM " removed
 */
fun String.removeTrailingDigitAfterTime(): String =
    replace(Regex("""(\d{2}:\d{2}\s)\d\s?"""), "$1")

///**
// * Extracts all consecutive three-letter uppercase sequences (`[A-Z]{3}`)
// * from the given string and concatenates them.
// *
// * @receiver the string to process
// * @return a new string containing only the matched uppercase triplets in order
// */
//fun String.extractAndJoinTriplets(): String =
//    Regex("[A-Z]{3}")
//        .findAll(this)
//        .joinToString("") { it.value }