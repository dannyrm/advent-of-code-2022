fun main() {
    fun part1(input: List<String>): Int {
        return input.map { splitIntoRange(it) }.filter { it.first.subtract(it.second).isEmpty() }.size
    }

    fun part2(input: List<String>): Int {
        return input.map { splitIntoRange(it) }.filter { (it.first.subtract(it.second).size) < it.first.count() }.size
    }

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}

fun splitIntoRange(input: String): Pair<IntRange, IntRange> {
    return input
        .split(",")
        .map { it.split("-") }
        .map { IntRange(it[0].toInt(), it[1].toInt()) }
        .sortedBy { it.count() }
        .run { Pair(this[0], this[1]) }
}