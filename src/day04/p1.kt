package day04

fun main(args: Array<String>) {
    var x = readlnOrNull()
    var ans = 0L
    while (x != null) {
        val (_, winning, my) = x.split(':', '|').map { it.trim() }
        val winningSet = winning.split(' ').map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }.toSet()
        val res = my.split(' ').map { it.trim() }.filter { it.isNotBlank() }.map { it.toInt() }.filter { winningSet.contains(it) }.size
        ans += 1L.shl(res - 1)
        x = readlnOrNull()
    }
    println(ans)
}