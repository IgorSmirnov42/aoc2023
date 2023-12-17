package day03

private fun calculatePartNumbers(grid: List<String>): List<Int> {
    val res = mutableListOf<Int>()
    for (i in grid.indices) {
        for (j in grid[i].indices) {
            if (!grid[i][j].isDigit()) {
                continue
            }
            if (grid[i].getOrNull(j - 1)?.isDigit() == true) {
                continue
            }
            val start = j
            var end = j + 1
            while (grid[i].getOrNull(end)?.isDigit() == true) {
                end += 1
            }
            val number = grid[i].substring(start..< end).toInt()
            var success = false
            for (k in (i-1)..(i+1)) {
                for (t in (start - 1)..end) {
                    val x = grid.getOrNull(k)?.getOrNull(t) ?: continue
                    if (x.isDigit()) continue
                    if (x == '.') continue
                    res.add(number)
                    success = true
                    break
                }
                if (success) {
                    break
                }
            }
        }
    }
    return res
}

fun main(args: Array<String>) {
    val grid = mutableListOf<String>()
    var x = readlnOrNull()
    while (x != null) {
        grid.add(x)
        x = readlnOrNull()
    }
    val partNumbers = calculatePartNumbers(grid)
    println(partNumbers.sum())
}