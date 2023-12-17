package day09

private fun solve(seq: List<Int>): Int {
    if (seq.all { it == 0 }) {
        return 0
    }
    val newSeq = seq.windowed(2, 1).map { it[1] - it[0] }
    val solution = solve(newSeq)
    return seq.first() - solution
}

fun main(args: Array<String>) {
    var x = readlnOrNull()
    var ans = 0L
    while (x != null) {
        val sequence = x.split(' ').map { it.trim().toInt() }
        ans += solve(sequence)
        x = readlnOrNull()
    }
    println(ans)
}