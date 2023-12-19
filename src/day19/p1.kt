package day19

import utils.readInput
import kotlin.system.measureTimeMillis

private fun solve(input: List<String>) {
    class Part(val map: Map<String, Int>)
    class Check(val partName: String, val condition: String, val comparison: Int, val sendTo: String) {
        fun check(part: Part): String? {
            val value = part.map[partName]!!
            val matches = when (condition) {
                ">" -> value > comparison
                else -> value < comparison
            }
            return if (matches) {
                sendTo
            } else {
                null
            }
        }
    }
    class Rule(val checks: List<Check>, val fallback: String) {
        fun check(part: Part): String {
            for (check in checks) {
                val res = check.check(part)
                if (res != null) {
                    return res
                }
            }
            return fallback
        }
    }

    val blank = input.indexOfFirst { it.isBlank() }
    val rulesStr = input.subList(0, blank)
    val partsStr = input.subList(blank + 1, input.size)

    val rules: Map<String, Rule> = rulesStr.associate {
        val (name, checksStr) = it.split("{", "}")
        val checksArr = checksStr.split(',')
        val fallback = checksArr.last()
        val checks = checksArr.dropLast(1).map {
            val (fst, sendTo) = it.split(":")
            val partName = fst[0].toString()
            val condition = fst[1].toString()
            val comparison = fst.substring(2).toInt()
            Check(partName, condition, comparison, sendTo)
        }
        name to Rule(checks, fallback)
    }

    val parts = partsStr.map {
        it.split("{", ",", "}")
            .filter { it.isNotBlank() }.associate {
                val (partName, value) = it.split('=')
                partName to value.toInt()
            }
    }.map { Part(it) }

    var ans = 0
    for (part in parts) {
        var rule = "in"
        while (rule != "R" && rule != "A") {
            rule = rules[rule]!!.check(part)
        }
        if (rule == "A") {
            ans += part.map.values.sum()
        }
    }
    println(ans)
}

fun main() {
    val time = measureTimeMillis {
//        solve(readInput("day19/sample"))
        solve(readInput("day19/input"))
    }
    println("Time: $time")
}