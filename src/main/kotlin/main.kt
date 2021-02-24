import java.util.*
import kotlin.math.round

class Sort(private val command: Array<String>) {
    private var listInput = mutableListOf<String>()

    fun startSorting() { inputs(); sorting(); }

    private fun inputs() {
        val scanner = Scanner(System.`in`)
        when {
            command.contains("line") -> while (scanner.hasNextLine()) { listInput.add(scanner.nextLine()) }
            else -> while (scanner.hasNext()) { listInput.add(scanner.next()) }
        }
    }

    private fun sorting() {
        val (order, dataType) = getData()
        val wordToUse = when (dataType) {
            "long" -> "numbers"
            "line" -> "lines"
            "word" -> "words"
            else -> "numbers"
        }
        val firstLine = "Total $wordToUse: ${listInput.size}.\n"
        var secondLine = StringBuilder()
        when (order) {
            "natural" -> {
                val list = sortByNatural(listInput)
                secondLine =
                if(dataType != "lines") {
                    secondLine.append("Sorted data: ${list.joinToString(" ")}")
                } else {
                    secondLine.append("Sorted data:\n${list.joinToString("\n")}")
                }
            }
            "byCount" -> {
                val list = sortByCount(listInput).toList()
                    .sortedBy { it.second }
                    .toMap()
                list.forEach { (element, count) ->
                    val percentage = round(count.div(listInput.size.toDouble()) * 100).toInt()
                    secondLine.appendLine("$element: $count time(s), $percentage%")
                }
            }
        }
        println(firstLine + secondLine)
    }

    private fun getData(): List<String> {
        val order = if("natural" in command) "natural" else if("byCount" in command) "byCount" else "natural"
        val dataType = when {
            "long" in command -> "long"
            "word" in command -> "word"
            "lines" in command -> "lines"
            else -> "word"
        }
        return listOf(order, dataType)
    }

    private fun sortByNatural(list: MutableList<String>): List<String> {
        return when {
            "long" in command || "word" in command -> list.sortedBy { it.toInt() }
            else -> list.sortedByDescending { it.length }
        }
    }

    private fun sortByCount(list: MutableList<String>): SortedMap<String, Int> {
        val (_, dataType) = getData()
        val countOccur = list.groupingBy { it }.eachCount()
        return when (dataType) {
            "word" -> countOccur.toSortedMap(compareBy<String> { it }.thenBy { it })
            "line" -> countOccur.toSortedMap(compareBy<String> { it.length }.thenBy { it })
            else -> countOccur.toSortedMap(compareBy<String> { it.toInt() }.thenBy { it })
        }
    }
}

fun main(args: Array<String>) = Sort(args).startSorting()