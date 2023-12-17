package day06

private fun isWin(pressTime: Long, time: Long, distance: Long): Boolean {
    val timeLeft = time - pressTime
    val newDist = pressTime * timeLeft
    return newDist > distance
}

fun main(args: Array<String>) {
    val times = listOf(readln().split(' ').map { it.trim().toIntOrNull() }.filterNotNull().joinToString(separator = "").toLong())
    val distances = listOf(readln().split(' ').map { it.trim().toIntOrNull() }.filterNotNull().joinToString(separator = "").toLong())
    val n = times.size
    var res = 1L
    for (i in times.indices) {
        var options = 0
        for (press in 0..times[i]) {
            if (isWin(press, times[i], distances[i])) {
                options += 1
            }
        }
        res *= options
    }
    println(res)
}