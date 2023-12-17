package day15

fun main(args: Array<String>) {
    fun hash(str: String): Int {
        var hsh = 0
        for (c in str) {
            hsh += c.code
            hsh *= 17
            hsh %= 256
        }
        return hsh
    }

    val x = readln()
    val commands = x.split(',')
    val boxes = Array(256) { mutableListOf<Pair<String, Int>>() }
    for (command in commands) {
        if (command.contains('=')) {
            val (name, focusStr) = command.split('=')
            val focus = focusStr.toInt()
            val box = hash(name)
            val idx = boxes[box].indexOfFirst { it.first == name }
            if (idx == -1) {
                boxes[box].add(name to focus)
            } else {
                boxes[box][idx] = name to focus
            }
        } else if (command.contains('-')) {
            val name = command.split('-').first()
            val box = hash(name)
            boxes[box] = boxes[box].filter { it.first != name }.toMutableList()
        }
    }
    var ans = 0L
    for (boxId in boxes.indices) {
        for (slotId in boxes[boxId].indices) {
            ans += (boxId + 1) * (slotId + 1) * (boxes[boxId][slotId].second)
        }
    }
    println(ans)
}