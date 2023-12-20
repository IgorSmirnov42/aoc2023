package day20

import utils.readInput
import java.util.LinkedList
import java.util.Queue

private fun solve(input: List<String>) {
    val type = mutableMapOf<String, String>()
    val edges = mutableMapOf<String, MutableList<String>>()
    val incomingEdges = mutableMapOf<String, MutableMap<String, Boolean>>()
    val state = mutableMapOf<String, Boolean>()

    for (line in input) {
        val (fromStr, toStr) = line.split(regex = Regex("->"), limit = 0).map { it.trim() }
        val from = if (fromStr == "broadcaster") {
            type[fromStr] = fromStr
            state[fromStr] = false
            fromStr
        } else if (fromStr.startsWith("%")) {
            val name = fromStr.substring(1)
            type[name] = "%"
            state[name] = false
            name
        } else {
            val name = fromStr.substring(1)
            type[name] = "&"
            state[name] = true
            name
        }
        val to = toStr.split(",").map { it.trim() }
        edges[from] = mutableListOf()
        for (v in to) {
            edges[from]!!.add(v)
            incomingEdges.getOrPut(v) { mutableMapOf() }[from] = false
        }
    }

    var ctrLow = 0
    var ctrHigh = 0
    repeat(1000) {
        val queue = LinkedList<Triple<String, Boolean, String>>()
        queue.add(Triple("broadcaster", false, "button"))
        while (!queue.isEmpty()) {
            val (name, isHigh, prev) = queue.poll()
            if (isHigh) {
                ctrHigh++
            } else {
                ctrLow++
            }
            if (type[name] == "%") {
                if (!isHigh) {
                    // flip and send
                    state[name] = !state[name]!!
                    for (v in edges[name]!!) {
                        queue.add(Triple(v, state[name]!!, name))
                    }
                } else {
                    continue
                }
            } else if (type[name] == "&") {
                // accumulate everything and send
                incomingEdges[name]!![prev] = isHigh
                val allHigh = incomingEdges[name]!!.values.all { it }
                for (v in edges[name]!!) {
                    queue.add(Triple(v, !allHigh, name))
                }
            } else if (name == "broadcaster") {
                for (v in edges[name]!!) {
                    queue.add(Triple(v, false, name))
                }
            }
        }
    }
    println("$ctrLow $ctrHigh ${ctrLow.toLong() * ctrHigh.toLong()}")
}

fun main() {
//    solve(readInput("day20/sample1"))
//    solve(readInput("day20/sample"))
    solve(readInput("day20/input"))
}