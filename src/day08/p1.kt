package day08

fun main(args: Array<String>) {
    val map = mutableMapOf<String, Pair<String, String>>()
    val commands = readln()
    readln()
    var mapItem = readlnOrNull()
    while (mapItem != null) {
        val node = mapItem.split("=")[0].trim()
        val (left, right) = mapItem.split("=")[1].trim('(', ' ', ')').split(',').map { it.trim() }
        map[node] = left to right
        mapItem = readlnOrNull()
    }
    var ctr = 0
    var cur = "AAA"
    while (cur != "ZZZ") {
        cur = if (commands[ctr % commands.length] == 'L') {
            map[cur]!!.first
        } else {
            map[cur]!!.second
        }
        ctr += 1
    }
    println(ctr)
}