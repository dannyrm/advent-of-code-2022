fun main() {
    fun part1(input: List<String>): Int {
        return input.map { findCommonItem(it) }.sumOf { calculatePriority(it) }
    }

    fun part2(input: List<String>): Int {
        return input.chunked(3).map { findBadge(Triple(it[0], it[1], it[2])) }.sumOf { calculatePriority(it) }
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

fun findBadge(rucksacks: Triple<String, String, String>): Char {
    // Should only be one item (the "badge") shared by all three elves.
    return rucksacks.first.filter { rucksacks.second.contains(it) }.filter { rucksacks.third.contains(it) }.toCharArray()[0]
}

fun calculatePriority(theChar: Char): Int {
    if (theChar.isLowerCase()) {
        return theChar.code - ('a'.code-1)
    }
    else if (theChar.isUpperCase()) {
        return theChar.code - ('A'.code) + 27
    }

    throw Exception("Unexpected input")
}