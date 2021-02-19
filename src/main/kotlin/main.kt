import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)
    val numbers = mutableListOf<Int>()

    while (scanner.hasNextInt()) { numbers.add(scanner.nextInt()) }
    println("Total numbers: ${numbers.size}.")
    println("The greatest number: ${numbers.maxOrNull()} (${Collections.frequency(numbers, numbers.maxOrNull())} time(s))")
}