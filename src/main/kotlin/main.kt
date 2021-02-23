import java.util.*

class Sort(private val command: Array<String>) {
    private var numInput = 0
    private var maxInput: String = ""
    private var occurrences = 0
    private var percent = 0
    private var listInput = mutableListOf<String>()

    override fun toString(): String {
        return when {
            "long" in command -> {
                "Total number: $numInput.\n" +
                if ("-sortIntegers" in command)
                    "Sorted data: ${listInput.sortedBy { it.toInt() }.joinToString(" ")}"
                else
                    "The greatest number: $maxInput ($occurrences time(s), $percent%).\n"
            } "line" in command -> {
                "Total lines: $numInput.\n" +
                if ("-sortIntegers" in command)
                    "Sorted data: ${listInput.sortedBy { it.toInt() }.joinToString(" ")}"
                else
                    "The longest line:\n$maxInput\n($occurrences time(s), $percent%)."
            } "word" in command -> {
                "Total lines: $numInput.\n" +
                if ("-sortIntegers" in command)
                    "Sorted data: ${listInput.sortedBy { it.toInt() }.joinToString(" ")}"
                else
                    "The longest word: $maxInput ($occurrences time(s), $percent%)."
            } else -> {
                "Total number: $numInput.\n" +
                if ("-sortIntegers" in command)
                    "Sorted data: ${listInput.sortedBy { it.toInt() }.joinToString(" ")}"
                else
                    "The greatest number: $maxInput ($occurrences time(s), $percent%).\n"
            }
        }
    }

    private fun isSort(): String {
        return if ("-sortIntegers" in command)
            "Sorted data: ${listInput.sortedBy { it.toInt() }.joinToString(" ")}"
        else
            "The greatest number: $maxInput ($occurrences time(s), $percent%).\n"
    }

    private fun inputs() {
        val scanner = Scanner(System.`in`)
        when {
//            "long" in command || "word" in command -> while (scanner.hasNext()) { listInput.add(scanner.next()) }
            "line" in command -> while (scanner.hasNextLine()) { listInput.add(scanner.nextLine()) }
            else -> while (scanner.hasNext()) { listInput.add(scanner.next()) }
        }
    }

    private fun statistic() {
        numInput = listInput.size
        maxInput = when {
//            "long" in command -> listInput.maxByOrNull { it.toInt() } ?: "0"
            "line" in command || "word" in command -> listInput.maxByOrNull { it.length } ?: "0"
            else -> listInput.maxByOrNull { it.toInt() } ?: "0"
        }
        occurrences = Collections.frequency(listInput, maxInput)
        percent = (occurrences / numInput.toDouble() * 100).toInt()
    }

    fun startSorting() { inputs(); statistic(); println(this) }
}

fun main(args: Array<String>) = Sort(args).startSorting()