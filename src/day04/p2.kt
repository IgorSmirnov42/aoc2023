package day04

fun main(args: Array<String>) {
    var x = readlnOrNull()
    var ans = 0L
    var ctr = 0
    val cards = LongArray(1000) { 1L }
    while (x != null) {
        val cardsCur = cards[ctr]
        val (_, winning, my) = x.split(':', '|').map { it.trim() }
        val winningSet = winning.split(' ').map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        val res = my.split(' ').map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }.filter { winningSet.contains(it) }.size
        for (i in (ctr+1)..(ctr+res)) {
            cards[i] += cardsCur
        }
        ctr += 1
        x = readlnOrNull()
    }
    println(cards.copyOfRange(0, ctr).sum())
}