package day12

private fun check(i: Int, map: String, data: List<Int>): Boolean {

    fun get(i: Int, qPtr: Int): Char {
        return if ((i and 1.shl(qPtr)) > 0) {
            '#'
        } else {
            '.'
        }
    }

    var ptr = 0
    var qPtr = 0
    var dataPtr = 0
    while (ptr != map.length) {
        val curChar = if (map[ptr] != '?') {
            map[ptr]
        } else {
            get(i, qPtr++)
        }
        if (curChar == '.') {
            ptr += 1
            continue
        }
        if (dataPtr >= data.size) {
            return false
        }
        var len = 1
        ++ptr
        while (true) {
            val cChar = if (map[ptr] != '?') {
                map[ptr]
            } else {
                get(i, qPtr++)
            }
            if (cChar == '#') {
                len += 1
                ptr += 1
            } else {
                break
            }
        }
        if (len != data[dataPtr]) {
            return false
        }
        ++dataPtr
        ++ptr
    }
    return dataPtr == data.size
}

private fun solve(str: String): Long {
    val (mapT, dataStr) = str.split(' ')
    val map = ".$mapT."
    val data = dataStr.split(',').map { it.toInt() }
    val questions = map.count { it == '?' }
    var ctr = 0
    for (i in 0 until 1.shl(questions)) {
        ctr += if (check(i, map, data)) 1 else 0
    }
    return ctr.toLong()
}

fun main(args: Array<String>) {
    var ctr = 0L
    var x = readlnOrNull()
    while (x != null) {
        ctr += solve(x)
        println(solve(x))
        x = readlnOrNull()
    }
    println(ctr)

}