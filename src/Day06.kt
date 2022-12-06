fun main() {
    fun part1(input: List<String>): Int {
        val windowSize = 4

        val firstUniqueChars = input[0].windowed(windowSize).filter { isStringAllUniqueCharacters(it) }[0]
        return input[0].indexOf(firstUniqueChars) + windowSize
    }

    fun part2(input: List<String>): Int {
        val windowSize = 14

        val firstUniqueChars = input[0].windowed(windowSize).filter { isStringAllUniqueCharacters(it) }[0]
        return input[0].indexOf(firstUniqueChars) + windowSize
    }

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

fun isStringAllUniqueCharacters(input: String): Boolean{
    val seenChars = mutableListOf<Char>()
    input.forEach {
        if (seenChars.contains(it)) {
            return false
        }
        seenChars.add(it)
    }

    return true
}