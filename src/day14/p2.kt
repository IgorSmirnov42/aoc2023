package day14

fun rotateNorth(grid: List<List<Char>>): List<List<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (row in grid.indices) {
        newGrid.add(mutableListOf())
        for (col in grid.indices) {
            newGrid[row].add('.')
        }
    }
    for (col in grid[0].indices) {
        var lastSolid = -1
        for (row in grid.indices) {
            if (grid[row][col] == '#') {
                lastSolid = row
                newGrid[row][col] = '#'
            } else if (grid[row][col] == 'O') {
                lastSolid = lastSolid + 1
                newGrid[lastSolid][col] = 'O'
            }
        }
    }
    return newGrid
}

fun rotateWest(grid: List<List<Char>>): List<List<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (row in grid.indices) {
        newGrid.add(mutableListOf())
        for (col in grid.indices) {
            newGrid[row].add('.')
        }
    }
    for (row in grid.indices) {
        var lastSolid = -1
        for (col in grid[0].indices) {
            if (grid[row][col] == '#') {
                lastSolid = col
                newGrid[row][col] = '#'
            } else if (grid[row][col] == 'O') {
                lastSolid = lastSolid + 1
                newGrid[row][lastSolid] = 'O'
            }
        }
    }
    return newGrid
}

fun rotateSouth(grid: List<List<Char>>): List<List<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (row in grid.indices) {
        newGrid.add(mutableListOf())
        for (col in grid.indices) {
            newGrid[row].add('.')
        }
    }
    for (col in grid[0].indices) {
        var lastSolid = grid.size
        for (row in grid.indices.reversed()) {
            if (grid[row][col] == '#') {
                lastSolid = row
                newGrid[row][col] = '#'
            } else if (grid[row][col] == 'O') {
                lastSolid = lastSolid - 1
                newGrid[lastSolid][col] = 'O'
            }
        }
    }
    return newGrid
}

fun rotateEast(grid: List<List<Char>>): List<List<Char>> {
    val newGrid = mutableListOf<MutableList<Char>>()
    for (row in grid.indices) {
        newGrid.add(mutableListOf())
        for (col in grid.indices) {
            newGrid[row].add('.')
        }
    }
    for (row in grid.indices) {
        var lastSolid = grid[0].size
        for (col in grid[0].indices.reversed()) {
            if (grid[row][col] == '#') {
                lastSolid = col
                newGrid[row][col] = '#'
            } else if (grid[row][col] == 'O') {
                lastSolid = lastSolid - 1
                newGrid[row][lastSolid] = 'O'
            }
        }
    }
    return newGrid
}

fun cycle(lst: List<List<Char>>): List<List<Char>> {
    return rotateEast(rotateSouth(rotateWest(rotateNorth(lst))))
}

fun main(args: Array<String>) {
    var x = readlnOrNull()
    val grid = mutableListOf<List<Char>>()
    while (x != null) {
        grid.add(x.toList())
        x = readlnOrNull()
    }
    val visited = mutableMapOf<List<List<Char>>, Int>()
    var lst: List<List<Char>> = grid
    var ctr = 0
    while (true) {
        ctr += 1
        lst = cycle(lst)
        if (visited.containsKey(lst)) {
            println("$ctr ${visited[lst]}")
            break
        }
        visited[lst] = ctr
    }
    val cycleLen = 155 - 92
    val sol = visited.filter { it.value == 92 + (1000000000 - 92) % cycleLen }.keys.first()
    ctr = 0
    for (row in sol.indices) {
        for (col in sol[row].indices) {
            if (sol[row][col] == 'O') {
                ctr += sol.size - row
            }
        }
    }
    println(ctr)
}