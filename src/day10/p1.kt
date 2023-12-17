package day10

private fun dfs(stx: Int, sty: Int, grid: List<String>): Int {
    var ctr = 0
    var x = stx
    var y = sty
    var prevx = -1
    var prevy = -1
    while (grid[x][y] != 'S' || prevx == -1) {
        val char = grid[x][y]
        val possibleDirections = when (char) {
            'L' -> listOf(-1 to 0, 0 to 1)
            '|' -> listOf(-1 to 0, 1 to 0)
            '-' -> listOf(0 to -1, 0 to 1)
            '7' -> listOf(0 to -1, 1 to 0)
            'F' -> listOf(0 to 1, 1 to 0)
            'J' -> listOf(-1 to 0, 0 to -1)
            'S' -> listOf(1 to 0)
            else -> emptyList()
        }
        for ((dx, dy) in possibleDirections) {
            val (newX, newY) = x + dx to y + dy
            if (newX == prevx && newY == prevy) {
                continue
            }
            prevx = x
            prevy = y
            x = newX
            y = newY
            ctr += 1
            break
        }
    }
    return ctr
}

fun main(args: Array<String>) {
    var x = readlnOrNull()
    val grid = mutableListOf<String>()
    while (x != null) {
        grid.add(x)
        x = readlnOrNull()
    }
    val sx = grid.indexOfFirst { it.contains('S') }
    val sy = grid[sx].indexOf('S')
    print(dfs(sx, sy, grid))
}