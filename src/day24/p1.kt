package day24

import utils.readInput
import java.math.BigInteger

private fun solve(input: List<String>, intersectionFrom: Long, intersectionTo: Long) {

    data class Line(val a: BigInteger, val b: BigInteger, val c: BigInteger) {
        fun intersect(other: Line): Pair<Pair<BigInteger, BigInteger>, BigInteger> {
            val denom = a * other.b - other.a * b
            val xDiv = other.c * b - c * other.b
            val yDiv = c * other.a - a * other.c
            if (denom < BigInteger.ZERO) {
                return (xDiv * BigInteger.valueOf(-1) to BigInteger.valueOf(-1) * yDiv) to denom * BigInteger.valueOf(-1)
            }
            return (xDiv to yDiv) to denom
        }
    }
    data class Hailstone(val px: Long, val py: Long, val vx: Long, val vy: Long) {
        fun toLine(): Line {
            val p2x = px + vx
            val p2y = py + vy
            val a = BigInteger.valueOf(py) - BigInteger.valueOf(p2y)
            val b = BigInteger.valueOf(p2x) - BigInteger.valueOf(px)
            val c = BigInteger.valueOf(p2y) * BigInteger.valueOf(px) - BigInteger.valueOf(py) * BigInteger.valueOf(p2x)
            return Line(a, b, c)
        }
    }

    fun withinBounds(div: BigInteger, denom: BigInteger): Boolean {
        // div / denom >= intersectionFrom <=> div >= intersectionFrom * denom
        if (div < denom * BigInteger.valueOf(intersectionFrom)) {
            return false
        }
        if (div > denom * BigInteger.valueOf(intersectionTo)) {
            return false
        }
        return true
    }

    fun onTheWay(px: Long, vx: Long, div: BigInteger, denom: BigInteger): Boolean {
        // div/denom > px <=> div > px * denom
        if (div > BigInteger.valueOf(px) * denom) {
            return vx > 0
        } else if (div < BigInteger.valueOf(px) * denom) {
            return vx < 0
        } else {
            return true
        }
    }

    fun sameLine(a: Line, b: Line): Boolean {
        require(b.a != BigInteger.ZERO && b.b != BigInteger.ZERO && b.c != BigInteger.ZERO)
        return a.a * b.b == a.b * b.a && a.a * b.c == a.c * b.a
    }

    fun intersects(h1: Hailstone, h2: Hailstone): Boolean {
        val intersectLines = h1.toLine().intersect(h2.toLine())
        if (intersectLines.second == BigInteger.ZERO) {
            if (!sameLine(h1.toLine(), h2.toLine())) {
                return false
            }
            println("Same line $h1 $h2")
            return false
        }
//        println("Intersection: ${intersectLines.first.first.toDouble() / intersectLines.second.toDouble()} ${intersectLines.first.second.toDouble() / intersectLines.second.toDouble()}")
        if (!withinBounds(intersectLines.first.first, intersectLines.second) || !withinBounds(intersectLines.first.second, intersectLines.second)) {
            return false
        }
        if (!onTheWay(h1.px, h1.vx, intersectLines.first.first, intersectLines.second) || !onTheWay(h2.px, h2.vx, intersectLines.first.first, intersectLines.second)) {
            return false
        }
        return true
    }

    val hailstones = input.map { line ->
        val pp = line.split(" ").map { if (it.endsWith(",")) it.split(",")[0] else it }.filter { it.isNotBlank() }
        Hailstone(pp[0].toLong(), pp[1].toLong(), pp[4].toLong(), pp[5].toLong())
    }
    var ctr = 0
//    intersects(hailstones[1], hailstones[0])
    for ((h1, idx1) in hailstones zip hailstones.indices) {
        for (idx2 in (idx1 + 1)..hailstones.lastIndex) {
            val h2 = hailstones[idx2]
            if (intersects(h1, h2) != intersects(h2, h1)) {
                println("$idx1 $idx2 $h1 $h2 ${intersects(h1, h2)} ${intersects(h2, h1)}")
            }
            if (intersects(h1, h2)) {
//                println("$idx1, $idx2")
                ctr += 1
            }
        }
    }
    println(ctr)
}

fun main() {
//    solve(readInput("day24/sample"), 7, 27)
    solve(readInput("day24/input"), 200000000000000L, 400000000000000L)
}