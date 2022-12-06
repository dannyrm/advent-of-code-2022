import java.util.Stack

typealias MoveInstruction = Triple<Int, Int, Int>

fun main() {
    fun part1(input: List<String>): List<String> {
        val (stackInput, moveInput) = obtainInputs(input)
        val stacks = obtainStacks(stackInput)

        parseMoves(moveInput).forEach {
            for (i in 0 until it.first) {
                val toMove = stacks[it.second-1].pop()
                stacks[it.third-1].push(toMove)
            }
        }

        return stacks.map { it.pop() }
    }

    fun part2(input: List<String>): List<String> {
        val (stackInput, moveInput) = obtainInputs(input)
        val stacks = obtainStacks(stackInput)

        parseMoves(moveInput).forEach { move ->
            val toMove = (0 until move.first).map { stacks[move.second-1].pop() }
            toMove.reversed().forEach { element ->
                stacks[move.third-1].push(element)
            }
        }

        return stacks.map { it.pop() }
    }

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

fun obtainInputs(input: List<String>): Pair<List<String>, List<String>> {
    var currentIndex = 0
    while (input[currentIndex] != "") {
        currentIndex++
    }

    return Pair(input.subList(0, currentIndex-1), input.subList(currentIndex+1, input.size))
}

fun obtainStacks(stackInput: List<String>): Array<Stack<String>> {
    val stacks = Array(9) { Stack<String>() }

    stackInput.map { parseStackRow(it) }.reversed().forEach {
        it.forEach { pair ->
            stacks[pair.second-1].push(pair.first.toString())
        }
    }

    return stacks
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
