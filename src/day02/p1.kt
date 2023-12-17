package day02

private fun isPossible(round: String): Boolean {
    val haveCubes = mapOf(
        "red" to 12,
        "blue" to 14,
        "green" to 13
    )
    return round.split(",").filterNot { it.isBlank() }.map {
        val (num, color) = it.trim().split(" ")
        num.toInt() to color
    }.groupBy { it.second }.mapValues { it.value.sumOf { it.first } }
        .all { haveCubes[it.key]!! >= it.value }
}

fun main(args: Array<String>) {
    var x = readlnOrNull()
    var ctr = 0L
    while (x != null) {
        val (game, roundsStr) = x.split(":")
        val gameId = game.split(" ")[1].toInt()
        val rounds = roundsStr.split(";")
        if (rounds.all { isPossible(it) }) {
            ctr += gameId
        }
        x = readlnOrNull()
    }
    print(ctr)
}