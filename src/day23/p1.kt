package day23

import utils.readInput

private fun solve(grid: List<String>) {

    fun dfs(x: Int, y: Int, used: MutableSet<Pair<Int, Int>>): Int? {
        if (x < 0 || y < 0 || x >= grid.size || y >= grid[0].length || grid[x][y] == '#') {
            return null
        }
        if (used.contains(x to y)) {
            return null
        }
        if (x == grid.lastIndex) {
            return 0
        }
        used.add(x to y)
        val c = grid[x][y]
        if (c != '.') {
            val res = when (c) {
                '>' -> dfs(x, y + 1, used)
                '<' -> dfs(x, y - 1, used)
                '^' -> dfs(x - 1, y, used)
                'v' -> dfs(x + 1, y, used)
                else -> TODO()
            }
            used.remove(x to y)
            return res?.let { it + 1 }
        }

        var bestRes: Int? = null
        for ((shiftX, shiftY) in listOf(0 to 1, 0 to -1, 1 to 0, -1 to 0)) {
            val res = dfs(x + shiftX, y + shiftY, used)?.let { it + 1 }
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

    println(dfs(0, grid[0].indexOf('.'), mutableSetOf()))
}

fun main() {
//    solve(readInput("day23/sample"))
    solve(readInput("day23/input"))
}