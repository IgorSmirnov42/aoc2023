package day21

import utils.readInput
import java.util.LinkedList
import kotlin.math.abs

val precalc = mutableMapOf<Triple<Int, Int, Int>, Int>()

private fun bfs1(startX: Long, startY: Long, steps: Int, grid: List<String>): Int {
    val stepsRemaining = (steps - (abs(startX) + abs(startY))).toInt()
    require(stepsRemaining >= 0)
    require(stepsRemaining <= 100000)
    val newStartX = ((startX.toInt() + grid.size / 2) % grid.size + grid.size) % grid.size
    val newStartY = ((startY.toInt() + grid.size / 2) % grid.size + grid.size) % grid.size

    if (startX > 0) {
        require(newStartX == 0)
    }
    if (startX < 0) {
        require(newStartX == 130)
    }
    if (startY > 0) {
        require(newStartY == 0)
    }
    if (startY < 0) {
        require(newStartY == 130)
    }

    if (precalc.contains(Triple(newStartX, newStartY, stepsRemaining))) {
        return precalc[Triple(newStartX, newStartY, stepsRemaining)]!!
    }
    var ctr = 0
    val queue = LinkedList<Triple<Int, Int, Int>>()
    queue.add(Triple(newStartX, newStartY, 0))
    val used = Array(grid.size) { BooleanArray(grid.size) { false } }
    used[newStartX][newStartY] = true
    while (!queue.isEmpty()) {
        val (x, y, dist) = queue.poll()
        if (dist > stepsRemaining) {
            break
        }
        if (stepsRemaining % 2 == dist % 2) {
            ++ctr
        }
        for ((moveX, moveY) in listOf(0 to -1, 0 to 1, 1 to 0, -1 to 0)) {
            val (newX, newY) = x + moveX to y + moveY
            if (newX < 0 || newY < 0 || newX >= grid.size || newY >= grid[0].length || grid[newX][newY] == '#') {
                continue
            }
            if (!used[newX][newY]) {
                used[newX][newY] = true
                queue.add(Triple(newX, newY, dist + 1))
            }
        }
    }

    precalc[Triple(newStartX, newStartY, stepsRemaining)] = ctr
    return ctr
}

private fun solve(grid: List<String>, steps: Int) {
    fun calculateMinY(blocks: Int): Long {
        if (blocks == 1) {
            return 0
        }
        return grid.size / 2 + 1 + ((blocks / 2).toLong() - 1) * grid.size
    }

    val countOdd = bfs1(0, 0, 1001, grid)
    val countEven = bfs1(0, 0, 1000, grid)

    val maxInOneDirection = steps / grid.size + 100
    var ptr = 1
    var res = 0L
    val blocksToCheck = 6
    for (x in maxInOneDirection downTo 0) {
        val isEven = (x % 2 + 2) % 2 == 0
        if (x > 0) {
            val xLeftest = grid.size.toLong() * (x - 1) + grid.size / 2 + 1
            val xRightest = -xLeftest
            if (xLeftest > steps) {
                continue
            }
            while (calculateMinY(ptr + 2) + xLeftest <= steps) {
                ptr += 2
            }
            require(calculateMinY(ptr) + abs(xRightest) <= steps)
            require(calculateMinY(ptr + 2) + abs(xRightest) > steps)
            val upBlock = ptr / 2
            for (block in upBlock downTo maxOf(1, upBlock - blocksToCheck + 1)) {
                val y = calculateMinY(2 * block + 1)
                res += bfs1(xLeftest, y, steps, grid)
                res += bfs1(xLeftest, -y, steps, grid)
                res += bfs1(xRightest, y, steps, grid)
                res += bfs1(xRightest, -y, steps, grid)
            }
            if (upBlock - blocksToCheck + 1 <= 0) {
                res += bfs1(xLeftest, 0, steps, grid)
                res += bfs1(xRightest, 0, steps, grid)
            } else {
                if (isEven) {
                    res += 2 * countOdd
                } else {
                    res += 2 * countEven
                }
            }
            val totalInBetween = maxOf(0, upBlock - blocksToCheck)
            val evens = (totalInBetween + 1) / 2
            val odds = totalInBetween - evens
            if (isEven) {
                res += 2 * countEven.toLong() * (evens * 2)
                res += 2 * countOdd.toLong() * (odds * 2)
            } else {
                res += 2 * countEven.toLong() * (odds * 2)
                res += 2 * countOdd.toLong() * (evens * 2)
            }
        } else {
            while (calculateMinY(ptr + 2) <= steps) {
                ptr += 2
            }
            val upBlock = ptr / 2
            println(upBlock)
            for (block in upBlock downTo maxOf(1, upBlock - blocksToCheck + 1)) {
                val y = calculateMinY(2 * block + 1)
                res += bfs1(0, y, steps, grid)
                res += bfs1(0, -y, steps, grid)
            }
            if (upBlock - blocksToCheck + 1 <= 0) {
                res += bfs1(0, 0, steps, grid)
            } else {
                res += countOdd
            }
            val totalInBetween = maxOf(0, upBlock - blocksToCheck)
            val evens = (totalInBetween + 1) / 2
            val odds = totalInBetween - evens
            res += countEven.toLong() * (evens * 2)
            res += countOdd.toLong() * (odds * 2)
        }
    }
    println(res)
}

fun main() {
//    solve(readInput("day21/sample"), 5000)
//    solve(readInput("day21/input"), 1501)
    solve(readInput("day21/input"), 26501365)
}