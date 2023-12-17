package day11

import kotlin.math.abs

fun main(args: Array<String>) {
    var x = readlnOrNull()
    val grid = mutableListOf<String>()
    while (x != null) {
        grid.add(x)
        x = readlnOrNull()
    }
    val n = grid.size
    val m = grid[0].length

    val emptyRows = grid.indices.filter { grid[it].all { it == '.' } }.toSet()
    val emptyCols = (0 until m).filter { col ->(0 until n).all { grid[it][col] == '.' } }.toSet()

    val positions = mutableListOf<Pair<Int, Int>>()

    var currentRow = 0
    var currentCol = 0

    for (i in grid.indices) {
        currentCol = 0
        for (j in grid[i].indices) {
            if (grid[i][j] == '#') {
                positions.add(currentRow to currentCol)
            }
            currentCol += 1
            if (emptyCols.contains(j)) {
                currentCol += 1000000 - 1
            }
        }
        currentRow += 1
        if (emptyRows.contains(i)) {
            currentRow += 1000000 - 1
        }
    }

    var ans = 0L
    for ((x1, y1) in positions) {
        for ((x2, y2) in positions) {
            ans += abs(x1 - x2) + abs(y1 - y2)
        }
    }
    println(ans / 2)

}