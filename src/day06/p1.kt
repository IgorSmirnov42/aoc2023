package day06

private fun isWin(pressTime: Int, time: Int, distance: Int): Boolean {
    val timeLeft = time - pressTime
    val newDist = pressTime * timeLeft
    return newDist > distance
}

fun main(args: Array<String>) {
    val times = readln().split(' ').map { it.trim().toIntOrNull() }.filterNotNull()
    val distances = readln().split(' ').map { it.trim().toIntOrNull() }.filterNotNull()
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