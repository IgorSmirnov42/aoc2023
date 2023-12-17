package day12

fun lazyDp(posMap: Int, posData: Int, map: String, data: List<Int>, res: Array<LongArray>): Long {
    if (posMap >= map.length) {
        return if (posData == data.size) {
            1
        } else {
            0
        }
    }
    if (res[posMap][posData] != -1L) {
        return res[posMap][posData]
    }
    var result = 0L

    if (map[posMap] == '.' || map[posMap] == '?') {
        result += lazyDp(posMap + 1, posData, map, data, res)
    }

    if ((map[posMap] == '#' || map[posMap] == '?') && posData < data.size) {
        val length = data[posData]
        if (posMap + length < map.length && map[posMap + length] != '#') {
            val substr = map.substring(posMap until posMap + length)
            if (substr.all { it != '.' }) {
                result += lazyDp(posMap + length + 1, posData + 1, map, data, res)
            }
        }
    }

    res[posMap][posData] = result
    return result
}

private fun solve(str: String): Long {
    val (mapT, dataT) = str.split(' ')
    val map = ".$mapT?$mapT?$mapT?$mapT?$mapT."
    val data = "$dataT,$dataT,$dataT,$dataT,$dataT".split(',').map { it.toInt() }
    println("$map $data")
    return lazyDp(0, 0, map, data, Array(map.length) { LongArray(data.size + 1) { -1 } })
}

fun main(args: Array<String>) {
    var ctr = 0L
    var x = readlnOrNull()
    while (x != null) {
        ctr += solve(x)
        x = readlnOrNull()
    }
    println(ctr)

}