package day01

fun main(args: Array<String>) {
    var x = readlnOrNull()
    var ctr = 0L
    while (x != null) {
        val firstDigit = x.find { it.isDigit() }
        val secondDigit = x.findLast { it.isDigit() }
        ctr += (firstDigit.toString() + secondDigit.toString()).toInt()
        x = readlnOrNull()
    }
    print(ctr)
}