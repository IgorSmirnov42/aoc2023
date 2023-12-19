package day19

import utils.readInput
import kotlin.system.measureTimeMillis

private fun solve(input: List<String>) {
    class Part(val map: Map<String, IntRange>)
    class Check(val partName: String, val condition: String, val comparison: Int, val sendTo: String) {
        fun check(part: Part): String? {
            val value = part.map[partName]!!
            val matches = when (condition) {
                ">" -> value.first > comparison
                else -> value.last < comparison
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

    fun dfs(pos: String, index: Int, map: MutableMap<String, IntRange>): Long {
        if (map.values.any { it.isEmpty() }) {
            return 0L
        }
        if (pos == "R") {
            return 0L
        }
        if (pos == "A") {
            val sizes = map.values.map { (it.last - it.first + 1).toLong() }
            return sizes[0] * sizes[1] * sizes[2] * sizes[3]
        }
        val ruls = rules[pos]!!
        if (index >= ruls.checks.size) {
            return dfs(ruls.fallback, 0, map)
        }
        val check = ruls.checks[index]
        val curVal = map[check.partName]!!

        var ans = 0L
        if (check.condition == ">") {
            map[check.partName] = IntRange(check.comparison + 1, curVal.last)
            ans += dfs(check.sendTo, 0, map)
            map[check.partName] = IntRange(curVal.first, check.comparison)
            ans += dfs(pos, index + 1, map)
        } else {
            map[check.partName] = IntRange(curVal.first, check.comparison - 1)
            ans += dfs(check.sendTo, 0, map)
            map[check.partName] = IntRange(check.comparison, curVal.last)
            ans += dfs(pos, index + 1, map)
        }
        map[check.partName] = curVal
        return ans
    }

    println(dfs("in", 0, mutableMapOf(
        "x" to IntRange(1, 4000), "m" to IntRange(1, 4000), "a" to IntRange(1, 4000), "s" to IntRange(1, 4000)
    )
    )
    )
}

fun main() {
    val time = measureTimeMillis {
//        solve(readInput("day19/sample"))
        solve(readInput("day19/input"))
    }
    println("Time: $time")
}