package day08

fun main(args: Array<String>) {
    val map = mutableMapOf<String, Pair<String, String>>()
    val commands = readln()
    readln()
    var mapItem = readlnOrNull()
    while (mapItem != null) {
        val node = mapItem.split("=")[0].trim()
        val (left, right) = mapItem.split("=")[1].trim('(', ' ', ')').split(',').map { it.trim() }
        map[node] = left to right
        mapItem = readlnOrNull()
    }
    val minToNextZ = Array(commands.length) {
        mutableMapOf<String, Pair<String, Int>>()
    }
    val jump = Array(commands.length) {
        Array(28) {
            mutableMapOf<String, String>()
        }
    }
    for (pwr in 0..< 28) {
        for (i in commands.indices) {
            for (key in map.keys) {
                if (pwr == 0) {
                    jump[i][pwr][key] = if (commands[i] == 'L') {
                        map[key]!!.first
                    } else {
                        map[key]!!.second
                    }
                } else {
                    jump[i][pwr][key] = jump[(i + 1.shl(pwr - 1)) % commands.length][pwr - 1][jump[i][pwr - 1][key]]!!
                }
            }
        }
    }
    val reverse = Array(commands.length) {
        mutableMapOf<String, MutableList<String>>()
    }
    for (i in commands.indices) {
        for (key in map.keys) {
            val nextIdx = (1 + i) % commands.length
            val nextNode = jump[i][0][key]!!
            reverse[nextIdx].getOrPut(nextNode) { mutableListOf() }.add(key)
        }
    }

    var iter = 0
    var nextToCheck = mutableListOf<Pair<Int, String>>()
    for (i in commands.indices) {
        for (key in map.keys) {
            nextToCheck.add(i to key)
        }
    }
    while (minToNextZ.any { it.size != map.size }) {
        iter += 1
        val newNextToCheck = mutableListOf<Pair<Int, String>>()
        for ((i, key) in nextToCheck) {
            if (minToNextZ[i].containsKey(key)) {
                continue
            }
            val prevIdx = (i - 1 + commands.length) % commands.length
            val nxt = jump[i][0][key]!!
            if (nxt.endsWith("Z")) {
                minToNextZ[i][key] = nxt to 1
                newNextToCheck.addAll(reverse[i][key]?.map { prevIdx to it } ?: emptyList())
            } else {
                if (minToNextZ[(i + 1) % commands.length].containsKey(nxt)) {
                    val (v, d) = minToNextZ[(i + 1) % commands.length][nxt]!!
                    minToNextZ[i][key] = v to (d + 1)
                    newNextToCheck.addAll(reverse[i][key]?.map { prevIdx to it } ?: emptyList())
                }
            }
        }
        nextToCheck = newNextToCheck
    }
    var allCur = map.keys.filter { it.endsWith("A") }
    var ctr = 0L
    while (allCur.any { !it.endsWith("Z") }) {
        var curIdx = (ctr % commands.length.toLong()).toInt()
        if (allCur.count { it.endsWith("Z") } > 4) {
            println("${ctr} ${allCur} ${curIdx}")
        }
        val step = allCur.maxOf { cur ->
            minToNextZ[curIdx][cur]!!.second
        }
        for (i in 0..27) {
            if (step.and(1.shl(i)) > 0) {
                allCur = allCur.map {
                    jump[curIdx][i][it]!!
                }
                ctr += 1.shl(i)
                curIdx = (curIdx + 1.shl(i)) % commands.length
            }
        }
    }
    println(ctr)
}