typealias Forest = List<IntArray>

fun main() {
    fun part1(input: List<String>): Int {
        return input
            .map { it.toCharArray() }
            .map { IntArray(it.size) { i -> it[i].digitToInt() } }
            .numberOfVisibleTrees()
    }

    fun part2(input: List<String>): Int {
        return input
            .map { it.toCharArray() }
            .map { IntArray(it.size) { i -> it[i].digitToInt() } }
            .scenicScores().max()
    }

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
}

fun Forest.scenicScores(): List<Int> {
    val scenicScores = mutableListOf<Int>()

    (this.indices).forEach { row ->
        (0 until this[0].size).forEach { column ->
            scenicScores.add(treeScenicScore(row, column))
        }
    }

    return scenicScores
}

fun Forest.numberOfVisibleTrees(): Int {
    var count = 0

    (this.indices).forEach { row ->
        (0 until this[0].size).forEach { column ->
            if (isTreeVisible(row, column)) {
                count++
            }
        }
    }

    return count
}

fun Forest.toStringRep(): String {
    val stringBuilder = StringBuilder()

    (this.indices).forEach { row ->
        stringBuilder.appendLine()
        (0 until this[0].size).forEach { column ->
            stringBuilder.append(this[row][column])
            if (isTreeVisible(row, column)) {
                stringBuilder.append("(V) ")
            } else {
                stringBuilder.append("(I) ")
            }
        }
    }

    return stringBuilder.toString()
}

private fun Forest.surroundingRowAndColumn(x: Int, y: Int): Array<List<Int>> {
    return arrayOf(this[y].copyOfRange(0, x).toList(), // West
        this[y].copyOfRange(x+1, this[0].size).toList(), // East
        this.map { it[x] }.subList(0, y), // North
        this.map { it[x] }.subList(y+1, this.size)) // South
}

private fun Forest.isTreeVisible(x: Int, y: Int): Boolean {
    if (x == 0 || x == this[0].size-1 || y == 0 || y == this.size-1) { // Tree is on the boundary of the forest
        return true
    }

    val treeHeight = this[y][x]

    val (treesWest, treesEast, treesNorth, treesSouth) = surroundingRowAndColumn(x, y)

    return treeHeight > treesWest.max() ||
            treeHeight > treesEast.max() ||
            treeHeight > treesNorth.max() ||
            treeHeight > treesSouth.max()
}

private fun Forest.treeScenicScore(x: Int, y: Int): Int {
    val treeHeight = this[y][x]

    fun calculateScoreForDirection(direction: List<Int>): Int {
        var count = 0

        for (i in direction.indices) {
            count++

            if (direction[i] >= treeHeight) {
                break
            }
        }

        return count
    }

    val (treesWest, treesEast, treesNorth, treesSouth) = surroundingRowAndColumn(x, y)

    return calculateScoreForDirection(treesEast) *
           calculateScoreForDirection(treesWest.reversed()) *
           calculateScoreForDirection(treesNorth.reversed()) *
           calculateScoreForDirection(treesSouth)
}