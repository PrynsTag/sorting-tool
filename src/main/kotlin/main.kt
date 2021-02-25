import java.util.*
import kotlin.math.round
import kotlin.system.exitProcess

class Sort(private val command: Array<String>) {
    private var listInput = mutableListOf<String>()

    fun startSorting() { inputs(); sorting(); }

    private fun inputs() {
        val scanner = Scanner(System.`in`)
        var newNum = ""
        when {
            command.contains("line") -> while (scanner.hasNextLine()) { listInput.add(scanner.nextLine()) }
            command.contains("long") -> while (scanner.hasNext()) {
                try {
                    newNum = scanner.next()
                    newNum.toInt()
                    listInput.add(newNum)
                } catch (e: NumberFormatException) {
                    println("\"$newNum\" is not a long. It will be skipped.")
                    continue
                }
            }
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
                    if(dataType != "lines") secondLine.append("Sorted data: ${list.joinToString(" ")}")
                    else secondLine.append("Sorted data:\n${list.joinToString("\n")}")
            }
            "byCount" -> {
                val map = sortByCount(listInput).toList().sortedBy { it.second }.toMap()
                map.forEach { (element, count) ->
                    val percentage = round(count.div(listInput.size.toDouble()) * 100).toInt()
                    secondLine.appendLine("$element: $count time(s), $percentage%")
                }
            }
        }
        println(firstLine + secondLine)
    }

    private fun getData(): List<String> {
        val order: String = when {
            command.contains("-sortingType") -> {
                when {
                    command.contains("natural") -> "natural"
                    command.contains("byCount") -> "byCount"
                    else -> { println("No sorting type defined!"); exitProcess(1) }
                }
            } else -> "natural"
        }
        val dataType: String = when {
            command.contains("-dataType") -> {
                when {
                    command.contains("long") -> "long"
                    command.contains("word") -> "word"
                    command.contains("line") -> "line"
                    else -> { println("No data type defined!"); exitProcess(1) }
                }
            } else -> "word"
        }

        return listOf(order, dataType)
    }

    private fun sortByNatural(list: MutableList<String>): List<String> {
        return when {
            "long" in command || "word" in command -> list.sortedBy { it.toInt() }
            else -> list.sortedByDescending { it.length }
        }
    }

    private fun sortByCount(list: MutableList<String>): Map<String, Int> {
        val (_, dataType) = getData()
        val countOccur = list.groupingBy { it }.eachCount()
        return when (dataType) {
            "word" -> countOccur.toSortedMap(compareBy<String> { it }.thenBy { it })
            "line" -> countOccur.toList().sortedWith(compareBy({ it.second }, { it.first })).toMap()
            else -> countOccur.toSortedMap(compareBy<String> { it.toInt() }.thenBy { it })
        }
    }
}

fun main(args: Array<String>) = Sort(args).startSorting()