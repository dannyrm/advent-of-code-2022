import java.util.Stack

typealias MoveInstruction = Triple<Int, Int, Int>

fun main() {
    fun part1(input: List<String>): List<String> {
        val stackRows = Array(8) { listOf<Pair<Char, Int>>() }

        for (i in 0 until 8) {
            stackRows[i] = parseStackRow(input[i])
        }

        val stacks = Array(9) { Stack<String>() }

        stackRows.reverse()

        stackRows.forEach {
            it.forEach { pair ->
                stacks[pair.second-1].push(pair.first.toString())
            }
        }

        val movesOnlyInput = input.drop(10) // Drop the first 10 lines containing the stacks

        val moves = parseMoves(movesOnlyInput)

        moves.forEach {
            for (i in 0 until it.first) {
                val toMove = stacks[it.second-1].pop()
                stacks[it.third-1].push(toMove)
            }
        }

        return stacks.map { it.pop() }
    }

    fun part2(input: List<String>): Int {
        return 1
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun parseStackRow(input: String): List<Pair<Char, Int>> {
    val crateIndexes =
        input
            .mapIndexed { index, c -> if (c == '[') index else -1 }
            .filterNot { it == -1 }
    val crateContents = crateIndexes.map { input[it+1] }
    val cratePositions = crateIndexes.map { (it / 4)+1 }

    return crateContents.zip(cratePositions)
}

fun parseMoves(input: List<String>): List<MoveInstruction> {
    return input
        .map { Regex("move (\\d+) from (\\d+) to (\\d+)").find(it)!!.groupValues }
        .map { Triple(it[1].toInt(), it[2].toInt(), it[3].toInt()) }
}
