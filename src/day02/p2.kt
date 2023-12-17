package day02

fun power(round: String): Map<String, Long> {
    return round.split(",").filterNot { it.isBlank() }.map {
        val (num, color) = it.trim().split(" ")
        num.toInt() to color
    }.groupBy { it.second }.mapValues { it.value.sumOf { it.first.toLong() } }
}

fun main(args: Array<String>) {
    var x = readlnOrNull()
    var ctr = 0L
    while (x != null) {
        val (game, roundsStr) = x.split(":")
        val gameId = game.split(" ")[1].toInt()
        val rounds = roundsStr.split(";").map { power(it) }
        ctr += rounds.maxOf { it["red"] ?: 0L } * rounds.maxOf { it["green"] ?: 0L } * rounds.maxOf { it["blue"] ?: 0L }
        x = readlnOrNull()
    }
    print(ctr)
}