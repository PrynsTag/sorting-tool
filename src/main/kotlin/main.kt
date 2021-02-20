import java.util.*

class Sort(private val dataType: String) {
    private var numInput = 0
    private var maxInput: String? = ""
    private var occurrences = 0
    private var percent = 0
    private var listInput = mutableListOf<String>()
    private var template = ""

    override fun toString(): String {
        template = when (dataType) {
            "long" -> {
                "Total number: $numInput.\n" +
                "The greatest number: $maxInput ($occurrences time(s), $percent%)."
            }
            "line" -> {
                "Total lines: $numInput.\n" +
                "The longest line:\n$maxInput\n($occurrences time(s), $percent%)."
            }
            "word" -> {
                "Total lines: $numInput.\n" +
                "The longest word: $maxInput ($occurrences time(s), $percent%)."
            }
            else -> "Wrong Input.."

        }
        return template
    }

    private fun inputs() {
        val scanner = Scanner(System.`in`)
        when (dataType) {
            "long", "word" -> while (scanner.hasNext()) { listInput.add(scanner.next()) }
            "line" -> while (scanner.hasNextLine()) { listInput.add(scanner.nextLine()) }
        }
    }

    private fun statistic() {
        numInput = listInput.size
        maxInput = when (dataType) {
            "long" -> listInput.maxByOrNull { it.toInt() }
            "line", "word" -> listInput.maxByOrNull { it.length }
            else -> ""
        }
        occurrences = Collections.frequency(listInput, maxInput)
        percent = (occurrences / numInput.toDouble() * 100).toInt()
    }

    fun startSorting() {
        inputs()
        statistic()
        println(this)
    }
}

fun main(args: Array<String>) = Sort(args[1]).startSorting()