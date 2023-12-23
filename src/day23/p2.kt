package day23

import utils.readInput

private fun solve(grid: List<String>) {

    var globalMax = 0
    var ctr = 0

    fun dfs(x: Int, y: Int, used: MutableSet<Pair<Int, Int>>, curDist: Int): Int? {
        if (x == grid.lastIndex) {
            ctr += 1
            if (curDist > globalMax) {
                globalMax = curDist
                println(globalMax)
            }
            return 0
        }
        if (ctr >= 10000) {
            return null
        }
        used.add(x to y)

        var bestRes: Int? = null
        for ((shiftX, shiftY) in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0).shuffled()) {
            val newX = x + shiftX
            val newY = y + shiftY
            if (newX < 0 || newY < 0 || newX >= grid.size || newY >= grid[0].length || grid[newX][newY] == '#') {
                continue
            }
            if (used.contains(newX to newY)) {
                continue
            }
            val res = dfs(newX, newY, used, curDist + 1)?.let { it + 1 }
            if (res != null) {
                bestRes = if (bestRes == null) {
                    res
                } else {
                    maxOf(bestRes, res)
                }
            }
        }

        used.remove(x to y)
        return bestRes
    }

    while (true) {
        ctr = 0
        dfs(0, grid[0].indexOf('.'), mutableSetOf(), 0)
    }
}

fun main() {
//    solve(readInput("day23/sample"))
    solve(readInput("day23/input"))
}