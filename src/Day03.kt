fun main() {
    fun part1(input: List<String>): Int {
        return input.map { findCommonItem(it) }.sumOf { convertToInt(it) }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}

fun findCommonItem(rucksack: String) : Char {
    val compartments = rucksack.chunked(rucksack.length/2)

    // Should only be one type of item shared
    return compartments[0].filter { compartments[1].contains(it) }.toCharArray()[0]
}

fun convertToInt(theChar: Char): Int {
    if (theChar.isLowerCase()) {
        return theChar.code - ('a'.code-1)
    }
    else if (theChar.isUpperCase()) {
        return theChar.code - ('A'.code) + 27
    }

    throw Exception("Unexpected input")
}