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
    var ctr = 0
    while (true) {
        ctr += 1
        val queue = LinkedList<Triple<String, Boolean, String>>()
        queue.add(Triple("broadcaster", false, "button"))
        while (!queue.isEmpty()) {
            val (name, isHigh, prev) = queue.poll()
            if (name == "rx" && !isHigh) {
                println(ctr)
                return
            }
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
                val incoming = incomingEdges[name]!!
                incoming[prev] = isHigh
                val allHigh = incoming.values.all { it }
                if (name == "dt" && incoming.values.count { it } > 0) {
                    println("$ctr ${incoming.filter { it.value }.keys}")
                }
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
}


// pm once per 3833
// ks once per 3917
// dl once per 3769
// vk once per 3877

fun main() {
    println(3833L * 3917L * 3769L * 3877L)
}