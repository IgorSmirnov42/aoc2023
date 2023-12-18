package day18

import utils.readInput



private fun solve(input: List<String>) {
    var distTotal = 0
    var res = 0L
    val polygon = mutableListOf(0 to 0)
    var curX = 0
    var curY = 0
    for (line in input) {
        val (_, _, color) = line.split(' ')
        val distHex = color.substring(2..6)
        val dirHex = color[7]
        val dist = distHex.toInt(16)
        val direction = when(dirHex) {
            '0' -> "R"
            '1' -> "D"
            '2' -> "L"
            else -> "U"
        }
        distTotal += dist
        when (direction) {
            "U" -> curX -= dist
            "D" -> curX += dist
            "L" -> curY -= dist
            "R" -> curY += dist
        }
        polygon.add(curX to curY)
    }
    println(polygon)
    val minX = polygon.minOf { it.first }
    val maxX = polygon.maxOf { it.first }
    println("$minX $maxX")
    val seq = (minX..maxX).toList().parallelStream().map { x ->
        val ysTemp = polygon.windowed(2).filter { (a, b) -> a.first != b.first }.filter { (a, b) ->
            minOf(a.first, b.first) <= x && x <= maxOf(a.first, b.first)
        }.sortedBy { it.first().second }
        val xs = polygon.windowed(2).mapNotNull { (a, b) ->
            if (a.first == b.first) {
                if (a.first == x) {
                    minOf(a.second, b.second) to maxOf(a.second, b.second)
                } else {
                    null
                }
            } else {
                if (minOf(a.first, b.first) < x && x < maxOf(a.first, b.first)) {
                    a.second to b.second
                } else {
                    null
                }
            }
        }
        var isInside = false
        var prevY = 0
        var tRes = 0L
        for ((a, b) in ysTemp) {
            val isChange = if (x != a.first && x != b.first) {
                true
            } else if (x == minOf(a.first, b.first)) {
                true
            } else {
                false
            }
            if (!isChange) {
                continue
            }
            if (!isInside) {
                isInside = true
                prevY = a.second
            } else {
                isInside = false
                val added = maxOf(
                    0,
                    a.second - prevY + 1 - xs.map { maxOf(prevY, it.first) to minOf(a.second, it.second) }
                    .sumOf { maxOf(it.second - it.first + 1, 0) }
                )
//                println("$x $prevY ${a.second} ${xs.filter { prevY <= it.first && it.second <= a.second }} $added")
                tRes += added
            }
        }
        tRes
    }
    println(seq.toList().sum() + distTotal)
}

//#######
//#.....#
//###...#
//..#...#
//..#...#
//###.### 5
//#...#..
//##..### 7
//.#....#
//.######


// ##...##
// 0123456
// 5 - 2 - 2

fun main() {
//    solve(readInput("day18/sample"))
    solve(readInput("day18/input"))
}