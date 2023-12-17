package day07

private fun combination(str: String): Int {
    val mappings = str.groupBy { it }.mapValues { it.value.size }.map { it.value }.sorted().reversed()
    if (mappings.contains(5)) {
        return 0
    }
    if (mappings.contains(4)) {
        return 1
    }
    if (mappings.contains(3) && mappings.contains(2)) {
        return 2
    }
    if (mappings.contains(3)) {
        return 3
    }
    if (mappings.count { it == 2 } == 2) {
        return 4
    }
    if (mappings.contains(2)) {
        return 5
    }
    return 6
}

fun main(args: Array<String>) {
    val order = "A,K,Q,J,T,9,8,7,6,5,4,3,2"
    var x = readlnOrNull()
    val hands = mutableListOf<Pair<String, Int>>()
    while (x != null) {
        val (hand, bid) = x.split(' ')
        hands.add(Pair(hand, bid.toInt()))
        x = readlnOrNull()
    }
    val sortedRes = hands.sortedWith { o1, o2 ->
        val comb1 = combination(o1.first)
        val comb2 = combination(o2.first)
        if (comb1 != comb2) {
            return@sortedWith comb1.compareTo(comb2)
        }
        val nonEqual = o1.first.zip(o2.first).find { it.first != it.second } ?: return@sortedWith 0
        order.indexOf(nonEqual.first).compareTo(order.indexOf(nonEqual.second))
    }.reversed()
    print(sortedRes.withIndex().map { (it.index + 1).toLong() * it.value.second.toLong() }.sum())
}