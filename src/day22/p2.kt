package day22

import utils.readInput

private fun solve(input: List<String>) {
    data class Brick(val x1: Int, val y1: Int, val z1: Int, val x2: Int, val y2: Int, val z2: Int, val index: Int) {
        fun minX(): Int {
            return minOf(x1, x2)
        }

        fun maxX(): Int {
            return maxOf(x1, x2)
        }

        fun minY(): Int {
            return minOf(y1, y2)
        }

        fun maxY(): Int {
            return maxOf(y1, y2)
        }
    }

    fun intersect(brick1: Brick, brick2: Brick): Boolean {
        val crossX1 = maxOf(brick1.minX(), brick2.minX())
        val crossX2 = minOf(brick1.maxX(), brick2.maxX())
        val crossY1 = maxOf(brick1.minY(), brick2.minY())
        val crossY2 = minOf(brick1.maxY(), brick2.maxY())
        return crossX1 <= crossX2 && crossY1 <= crossY2
    }

    val bricks = input.mapIndexed { idx, line ->
        val (from, to) = line.split("~").map { it.split(",").map { it.toInt() } }
        if (from[2] < to[2]) {
            Brick(from[0], from[1], from[2], to[0], to[1], to[2], idx)
        } else {
            Brick(to[0], to[1], to[2], from[0], from[1], from[2], idx)
        }
    }.sortedBy { it.z1 }

    val finalBricks = mutableListOf<Brick>()
    val holds = Array(bricks.size) { mutableListOf<Int>() }
    val holders = Array(bricks.size) { mutableListOf<Int>() }
    for (brick in bricks) {
        var minZ = 1
        val myHolders = mutableListOf<Int>()
        for ((landedBrick, index) in finalBricks zip finalBricks.indices) {
            if (intersect(brick, landedBrick)) {
                if (landedBrick.z2 + 1 > minZ) {
                    myHolders.clear()
                    myHolders.add(index)
                    minZ = maxOf(minZ, landedBrick.z2 + 1)
                } else if (landedBrick.z2 + 1 == minZ) {
                    myHolders.add(index)
                }
            }
        }
        println("${brick.index} holders are ${myHolders.map { finalBricks[it].index }}")
        for (holder in myHolders) {
            holds[holder].add(finalBricks.size)
            holders[finalBricks.size].add(holder)
        }
        println("$brick, $minZ")
        require(brick.z1 >= minZ)
        finalBricks.add(brick.copy(z1 = minZ, z2 = brick.z2 - (brick.z1 - minZ)))
    }
    var ctr = 0
    for (i in finalBricks.indices) {
        val fallBricks = mutableSetOf(i)
        for (j in (i+1)..finalBricks.lastIndex) {
            if (holders[j].all { fallBricks.contains(it) } && holders[j].isNotEmpty()) {
                fallBricks.add(j)
            }
        }
        ctr += fallBricks.size - 1
    }
    println(ctr)
}

fun main() {
//    solve(readInput("day22/sample"))
    solve(readInput("day22/input"))
}