package day21

import utils.readInput
import java.util.LinkedList
import kotlin.math.abs

private fun bfs(startX: Int, startY: Int, grid: List<String>, steps: Int): Int {
    val distance = Array(grid.size) { IntArray(grid[0].length) { Int.MAX_VALUE } }
    distance[startX][startY] = 0
    val queue = LinkedList<Pair<Int, Int>>()
    queue.add(startX to startY)
    while (!queue.isEmpty()) {
        val (x, y) = queue.poll()
        val dst = distance[x][y]
        if (dst != abs(x - startX) + abs(y - startY)) {
            println("$x $y $dst")
        }
        if (dst > steps) {
            break
        }
        for ((moveX, moveY) in listOf(0 to -1, 0 to 1, 1 to 0, -1 to 0)) {
            val (newX, newY) = x + moveX to y + moveY
            if (newX < 0 || newY < 0 || newX >= grid.size || newY >= grid[0].length || grid[newX][newY] != '.') {
                continue
            }
            if (distance[newX][newY] > dst + 1) {
                distance[newX][newY] = dst + 1
                queue.add(newX to newY)
            }
        }
    }
    return distance.sumOf { it.count { it % 2 == steps % 2 && it <= steps } }
}

private fun solve(grid: List<String>, steps: Int) {
    val startX = grid.indexOfFirst { it.contains('S') }
    val startY = grid[startX].indexOf('S')
    println(bfs(startX, startY, grid, steps))
}

fun main() {
//    solve(readInput("day21/sample"), 6)
    solve(readInput("day21/input"), 64)
}