package day16

import utils.readInput

private fun dfs(posX: Int, posY: Int, direction: Int, grid: List<String>, used: Array<Array<BooleanArray>>) {
    if (posX >= grid.size || posY >= grid[0].length || posY < 0 || posX < 0) {
        return
    }
    if (used[posX][posY][direction]) {
        return
    }
    used[posX][posY][direction] = true
    if (direction == 0) { // right
        when(grid[posX][posY]) {
            '.' -> dfs(posX, posY + 1, 0, grid, used)
            '|' -> {
                dfs(posX + 1, posY, 1, grid, used)
                dfs(posX - 1, posY, 3, grid, used)
            }
            '\\' -> dfs(posX + 1, posY, 1, grid, used)
            '/' -> dfs(posX - 1, posY, 3, grid, used)
            '-' -> dfs(posX, posY + 1, 0, grid, used)
        }
    }
    if (direction == 1) { // down
        when(grid[posX][posY]) {
            '.' -> dfs(posX + 1, posY, 1, grid, used)
            '|' -> dfs(posX + 1, posY, 1, grid, used)
            '\\' -> dfs(posX, posY + 1, 0, grid, used)
            '/' -> dfs(posX, posY - 1, 2, grid, used)
            '-' -> {
                dfs(posX, posY + 1, 0, grid, used)
                dfs(posX, posY - 1, 2, grid, used)
            }
        }
    }
    if (direction == 2) { // left
        when(grid[posX][posY]) {
            '.' -> dfs(posX, posY - 1, 2, grid, used)
            '|' -> {
                dfs(posX + 1, posY, 1, grid, used)
                dfs(posX - 1, posY, 3, grid, used)
            }
            '\\' -> dfs(posX - 1 , posY, 3, grid, used)
            '/' -> dfs(posX + 1, posY, 1, grid, used)
            '-' -> dfs(posX, posY - 1, 2, grid, used)
        }
    }
    if (direction == 3) { // up
        when(grid[posX][posY]) {
            '.' -> dfs(posX - 1, posY, 3, grid, used)
            '|' -> dfs(posX - 1, posY, 3, grid, used)
            '\\' -> dfs(posX, posY - 1, 2, grid, used)
            '/' -> dfs(posX, posY + 1, 0, grid, used)
            '-' -> {
                dfs(posX, posY + 1, 0, grid, used)
                dfs(posX, posY - 1, 2, grid, used)
            }
        }
    }
}

private fun solve(grid: List<String>) {
    val used = Array(grid.size) { Array(grid[0].length) { BooleanArray(4) { false } } }
    dfs(0, 0, 0, grid, used)
    println(used.sumOf { it.count { it.any { it } } })
}

fun main() {
    val input = readInput("day16/sample")
//    val input = readInput("day16/input")
    solve(input)
}