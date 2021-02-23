import java.util.*

class Sort(private val command: Array<String>) {
    private var numInput = 0
    private var maxInput: String = ""
    private var occurrences = 0
    private var percent = 0
    private var listInput = mutableListOf<String>()

    private fun getDataType(input: Array<String>): String {
       return when (input.size) {
           3, 2 -> input[1]
           else -> input[0]
       }
    }

    private fun isSort() {
        val dataType = getDataType(command)
        val word = when (dataType) {
            "long" -> "number"
            "line", "word" -> "lines"
            else -> "number"
        }
        val firstLine = "Total $word: $numInput.\n"
        val secondLine = when {
            command.contains("-sortIntegers") -> {
                "Sorted data: ${listInput.sortedBy { it.toInt() }.joinToString(" ")}"
            } else -> {
                when (dataType) {
                    "long" -> "The greatest number: $maxInput ($occurrences time(s), $percent%).\n"
                    "line" -> "The longest line:\n$maxInput\n($occurrences time(s), $percent%)."
                    "word" -> "The longest word: $maxInput ($occurrences time(s), $percent%)."
                    else -> "The greatest number: $maxInput ($occurrences time(s), $percent%).\n"
                }
            }

        }
        println(firstLine + secondLine)
    }

    private fun inputs() {
        val scanner = Scanner(System.`in`)
        when {
            command.contains("line") -> while (scanner.hasNextLine()) { listInput.add(scanner.nextLine()) }
            else -> while (scanner.hasNext()) { listInput.add(scanner.next()) }
        }
    }

    private fun statistic() {
        numInput = listInput.size
        maxInput = when {
            command.contains("line") || command.contains("word") -> listInput.maxByOrNull { it.length } ?: "0"
            else -> listInput.maxByOrNull { it.toInt() } ?: "0"
        }
        occurrences = Collections.frequency(listInput, maxInput)
        percent = (occurrences / numInput.toDouble() * 100).toInt()
    }

    fun startSorting() { inputs(); statistic(); isSort() }
}

fun main(args: Array<String>) = Sort(args).startSorting()