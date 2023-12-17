package day13

private fun solve(grid: List<String>): Long {
    fun checkRowReflection(row: Int): Boolean {
        for (rowC in 0..row) {
            val correspondingRow = row + 1 + (row - rowC)
            if (correspondingRow >= grid.size) {
                continue
            }
            if (grid[rowC] != grid[correspondingRow]) {
                return false
            }
        }
        return true
    }

    fun checkColumnReflection(column: Int): Boolean {
        for (columnC in 0..column) {
            val correspondingCol = column + 1 + (column - columnC)
            if (correspondingCol >= grid[0].length) {
                continue
            }
            for (i in grid.indices) {
                if (grid[i][columnC] != grid[i][correspondingCol]) {
                    return false
                }
            }
        }
        return true
    }

    var ctr = 0L

    for (row in 0 until grid.size - 1) {
        if (checkRowReflection(row)) {
            ctr += 100L * (row + 1).toLong()
            println("Row $row")
        }
    }
    for (col in 0 until grid[0].length - 1) {
        if (checkColumnReflection(col)) {
            ctr += col + 1
            println("Col $col")
        }
    }
    println(ctr)
    return ctr
}

fun main(args: Array<String>) {
    var ctr = 0L
    var x = readlnOrNull()
    while (x != null) {
        val grid = mutableListOf(x)
        while (x?.isNotBlank() == true) {
            x = readlnOrNull()
            if (x != null && x.isNotBlank()) {
                grid.add(x)
            }
        }
        ctr += solve(grid)
        x = readlnOrNull()
    }
    println(ctr)
}