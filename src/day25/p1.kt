package day25

import utils.readInput
import java.util.LinkedList

private fun solve(input: List<String>) {
    val edges = mutableListOf<Pair<String, String>>()
    val graph = mutableMapOf<String, MutableList<String>>()
    input.forEach { line ->
        val (from, toArr) = line.split(':').map { it.trim() }
        val to = toArr.split(' ')
        for (toV in to) {
            edges.add(from to toV)
            edges.add(toV to from)
            graph.getOrPut(from) { mutableListOf() }.add(toV)
            graph.getOrPut(toV) { mutableListOf() }.add(from)
        }
    }

    fun findBridge(e1: Pair<String, String>, e2: Pair<String, String>): Int? {
        val removed = mutableSetOf(e1, e2, e1.second to e1.first, e2.second to e2.first)
        val tin = mutableMapOf<String, Int>()
        val tup = mutableMapOf<String, Int>()

        var ctr = 0

        var bridge: Pair<String, String>? = null

        fun dfs(v: String, parent: String) {
            tin[v] = ctr
            tup[v] = ctr++
            for (nxt in graph[v]!!) {
                if (nxt == parent || removed.contains(v to nxt)) {
                    continue
                }
                if (tin.containsKey(nxt)) {
                    tup[v] = minOf(tup[v]!!, tin[nxt]!!)
                }
                else {
                    dfs(nxt, v)
                    tup[v] = minOf(tup[v]!!, tup[nxt]!!)
                    if (tup[nxt]!! > tin[v]!!) {
                        bridge = v to nxt
                    }
                }
            }
        }

        fun sizeOfComponent(v: String): Int {
            val q = LinkedList<String>()
            q.add(v)
            val used = mutableSetOf<String>()
            used.add(v)
            while (!q.isEmpty()) {
                val cur = q.poll()
                for (nxt in graph[cur]!!) {
                    if (removed.contains(cur to nxt)) {
                        continue
                    }
                    if (used.contains(nxt)) {
                        continue
                    }
                    used.add(nxt)
                    q.add(nxt)
                }
            }
            return used.size
        }

        dfs(graph.keys.first(), "")
        if (bridge != null) {
            removed.add(bridge!!)
            removed.add(bridge!!.second to bridge!!.first)
            val size = sizeOfComponent(graph.keys.first())
            return size * (graph.size - size)
        }
        return null
    }

    val edgesList = edges.toList().shuffled()

    for (edge1idx in edgesList.indices) {
        println("idx: $edge1idx")
        val edge1 = edgesList[edge1idx]
        if (edge1.first > edge1.second) {
            continue
        }
        ((edge1idx + 1)..edgesList.lastIndex).toList().parallelStream().forEach { edge2idx ->
            val edge2 = edgesList[edge2idx]
            if (edge2.first > edge2.second) {
                return@forEach
            }
            findBridge(edge1, edge2)?.let { println(it) }
        }
    }
}

fun main() {
//    solve(readInput("day25/sample"))
    solve(readInput("day25/input"))
}