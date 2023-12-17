package day14

private fun solve(grid: List<String>): Long {
    var res = 0L
    for (col in grid[0].indices) {
        var lastSolid = -1
        for (row in grid.indices) {
            if (grid[row][col] == '#') {
                lastSolid = row
            } else if (grid[row][col] == 'O') {
                lastSolid = lastSolid + 1
                res += grid.size - lastSolid
            }
        }
    }
    return res
}

fun main(args: Array<String>) {
    var ctr = 0L
    var x = readlnOrNull()
    val grid = mutableListOf<String>()
    while (x != null) {
        grid.add(x)
        x = readlnOrNull()
    }

    println(solve(grid))
}