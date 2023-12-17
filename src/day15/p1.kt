package day15

fun main(args: Array<String>) {
    fun hash(curHash: Int, str: String): Int {
        var hsh = curHash
        for (c in str) {
            hsh += c.code
            hsh *= 17
            hsh %= 256
        }
        return hsh
    }

    val x = readln()
    val commands = x.split(',')
    var ctr = 0
    var hsh = 0
    for (command in commands) {
        hsh = hash(0, command)
        println(hsh)
        ctr += hsh
    }
    print(ctr)

}