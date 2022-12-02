import DesiredOutcome.*
import Move.*
import kotlin.reflect.KFunction2

fun main() {
    fun part1(input: List<String>): Int {
        return input.map { convertToMovePair(it, ::calculatePart1Move) }.sumOf { calculateMoveResult(it) }
    }

    fun part2(input: List<String>): Int {
        return input.map { convertToMovePair(it, ::calculatePart2Move) }.sumOf { calculateMoveResult(it) }
    }

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

fun calculatePart1Move(opponentMove: Move, move: Char): Move {
    return when (move) {
        'X' -> ROCK
        'Y' -> PAPER
        'Z' -> SCISSORS
        else -> { throw Exception("Unexpected character") }
    }
}

fun calculatePart2Move(opponentMove: Move, move: Char): Move {
    return when (move) {
        'X' -> calculateAppropriateMove(opponentMove, LOSE)
        'Y' -> calculateAppropriateMove(opponentMove, DRAW)
        'Z' -> calculateAppropriateMove(opponentMove, WIN)
        else -> { throw Exception("Unexpected character") }
    }
}

fun convertToMovePair(input: String, moveFunc: KFunction2<Move, Char, Move>): Pair<Move, Move> {
    val move1 = when (input[0]) {
        'A' -> ROCK
        'B' -> PAPER
        'C' -> SCISSORS
        else -> { throw Exception("Unexpected character") }
    }

    return Pair(move1, moveFunc(move1, input[2]))
}

fun calculateMoveResult(move: Pair<Move, Move>): Int {
    val win = 6
    val draw = 3
    val lose = 0

    if (move.first == move.second) {
        return draw + move.second.ordinal + 1
    }

    val result = when (move) {
        ROCK to PAPER -> win
        PAPER to SCISSORS -> win
        SCISSORS to ROCK -> win
        else -> lose
    }

    return result + move.second.ordinal + 1
}

fun calculateAppropriateMove(move: Move, desiredOutcome: DesiredOutcome): Move {
    return when (move to desiredOutcome) {
        ROCK to WIN -> PAPER
        ROCK to LOSE -> SCISSORS
        PAPER to WIN -> SCISSORS
        PAPER to LOSE -> ROCK
        SCISSORS to WIN -> ROCK
        SCISSORS to LOSE -> PAPER
        else -> move // We want a draw so choose the same move
    }
}

enum class Move {
    ROCK,
    PAPER,
    SCISSORS
}

enum class DesiredOutcome {
    WIN,
    DRAW,
    LOSE
}
