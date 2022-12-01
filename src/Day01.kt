fun main() {
    fun part1(input: List<String>): Int {
        return splitByElf(input).max()
    }

    fun part2(input: List<String>): Int {
        return splitByElf(input).sortedDescending().take(3).sum()
    }

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}

fun splitByElf(input: List<String>): List<Int> {
    var allElvesCalorieList = input
    val elfCaloriesList = mutableListOf<Int>()

    while (allElvesCalorieList.isNotEmpty()) {
        elfCaloriesList.add(allElvesCalorieList.takeWhile { it.isNotEmpty()}.sumOf { it.toInt() })
        allElvesCalorieList = allElvesCalorieList.dropWhile { it.isNotEmpty() }.drop(1)
    }

    return elfCaloriesList
}
