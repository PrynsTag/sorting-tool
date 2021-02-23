import java.util.*

class Sort(private val command: Array<String>) {
    private var listInput = mutableListOf<String>()

    private fun getDataType(input: Array<String>): String {
       return when (input.size) {
           3, 2 -> input[1]
           else -> input[0]
       }
    }

    private fun isSort(numberOfInput: Int, max: String, timesOccurred: Int, percentage: Int) {
        val dataType = getDataType(command)
        val word = when (dataType) {
            "long" -> "number"
            "line", "word" -> "lines"
            else -> "number"
        }
        val firstLine = "Total $word: $numberOfInput.\n"
        val secondLine = when {
            command.contains("-sortIntegers") -> {
                "Sorted data: ${listInput.sortedBy { it.toInt() }.joinToString(" ")}"
            } else -> {
                when (dataType) {
                    "long" -> "The greatest number: $max ($timesOccurred time(s), $percentage%).\n"
                    "line" -> "The longest line:\n$max\n($timesOccurred time(s), $percentage%)."
                    "word" -> "The longest word: $max ($timesOccurred time(s), $percentage%)."
                    else -> "The greatest number: $max ($timesOccurred time(s), $percentage%).\n"
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
        val numInput = listInput.size
        val maxInput = when {
            command.contains("line") || command.contains("word") -> listInput.maxByOrNull { it.length } ?: "0"
            else -> listInput.maxByOrNull { it.toInt() } ?: "0"
        }
        val occurrences = Collections.frequency(listInput, maxInput)
        val percent = (occurrences / numInput.toDouble() * 100).toInt()
        return isSort(numInput, maxInput, occurrences, percent)
    }

    fun startSorting() { inputs(); statistic(); }
}

fun main(args: Array<String>) = Sort(args).startSorting()