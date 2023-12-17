package day17

import utils.readInput
import java.util.TreeSet

private fun solve(input: List<String>) {

    data class Node(val posX: Int, val posY: Int, val direction: Int, val stepsPassed: Int): Comparable<Node> {
        override fun compareTo(other: Node): Int {
            if (posX != other.posX) {
                return posX.compareTo(other.posX)
            }
            if (posY != other.posY) {
                return posY.compareTo(other.posY)
            }
            if (direction != other.direction) {
                return direction.compareTo(other.direction)
            }
            return stepsPassed.compareTo(other.stepsPassed)
        }

    }

    val grid = input.map { it.map { it.digitToInt() } }
    val distance = Array(grid.size) { Array(grid[0].size) { Array(4) { IntArray(11) { Int.MAX_VALUE } } } }
    distance[0][0][0][0] = 0
    distance[0][0][1][0] = 0
    val queue = TreeSet<Pair<Int, Node>> { o1, o2 ->
        if (o1.first != o2.first) {
            return@TreeSet o1.first.compareTo(o2.first)
        }
        o1.second.compareTo(o2.second)
    }
    queue.add(0 to Node(0, 0, 0, 0))
    queue.add(0 to Node(0, 0, 1, 0))

    fun isInsideGrid(x: Int, y: Int): Boolean {
        if (x < 0 || y < 0 || x >= grid.size || y >= grid[0].size) {
            return false
        }
        return true
    }

    while (!queue.isEmpty()) {
        val (dist, node) = queue.pollFirst()
        if (dist != distance[node.posX][node.posY][node.direction][node.stepsPassed]) {
            continue
        }
        if (node.posX == grid.lastIndex && node.posY == grid[0].lastIndex && node.stepsPassed >= 4) {
            println(dist)
            break
        }
        println("$dist $node")
        val x = node.posX
        val y = node.posY
        val next = (when(node.direction) {
            0 -> listOf(x to y + 1 to 0, x - 1 to y to 3, x + 1 to y to 1)
            1 -> listOf(x + 1 to y to 1, x to y + 1 to 0, x to y - 1 to 2)
            2 -> listOf(x to y - 1 to 2, x - 1 to y to 3, x + 1 to y to 1)
            3 -> listOf(x - 1 to y to 3, x to y + 1 to 0, x to y - 1 to 2)
            else -> TODO()
        }).filter { (pos, dir) -> isInsideGrid(pos.first, pos.second) && ((dir != node.direction && node.stepsPassed >= 4) || (dir == node.direction && node.stepsPassed < 10)) }
        for ((newPos, direction) in next) {
            val newStepsPassed = if (direction != node.direction) {
                1
            } else {
                node.stepsPassed + 1
            }
            val newDistance = dist + grid[newPos.first][newPos.second]
            if (newDistance < distance[newPos.first][newPos.second][direction][newStepsPassed]) {
                distance[newPos.first][newPos.second][direction][newStepsPassed] = newDistance
                queue.add(newDistance to Node(newPos.first, newPos.second, direction, newStepsPassed))
            }
        }
    }
}

fun main() {
//    val input = readInput("day17/sample")
    val input = readInput("day17/input")
    solve(input)
}