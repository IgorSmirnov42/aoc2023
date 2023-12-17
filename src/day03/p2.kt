package day03

private fun calculatePartNumbers(grid: List<String>): List<Pair<Int, Pair<Int, Int>>> {
    val res = mutableListOf<Pair<Int, Pair<Int, Int>>>()
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
            for (k in (i-1)..(i+1)) {
                for (t in (start - 1)..end) {
                    val x = grid.getOrNull(k)?.getOrNull(t) ?: continue
                    if (x == '*') {
                        res.add(number to (k to t))
                    }
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
    print(partNumbers.groupBy { it.second }.filter { it.value.size == 2 }.map { it.value[0].first.toLong() * it.value[1].first.toLong()  }.sum())
}