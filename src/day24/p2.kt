package day24

import org.apache.commons.math3.linear.Array2DRowRealMatrix
import org.apache.commons.math3.linear.ArrayRealVector
import org.apache.commons.math3.linear.LUDecomposition
import org.apache.commons.math3.linear.QRDecomposition
import utils.readInput
import java.math.BigInteger
import kotlin.math.abs
import kotlin.math.round

private fun solve(input: List<String>, intersectionFrom: Long, intersectionTo: Long) {
    data class Hailstone(val px: Long, val py: Long, val pz: Long, val vx: Long, val vy: Long, val vz: Long)

    val hailstones = input.map { line ->
        val pp = line.split(" ").map { if (it.endsWith(",")) it.split(",")[0] else it }.filter { it.isNotBlank() }
        Hailstone(pp[0].toLong(), pp[1].toLong(), pp[2].toLong(), pp[4].toLong(), pp[5].toLong(), pp[6].toLong())
    }

    fun check(vx: Long, vy: Long, vz: Long) {

        fun solveEq(): Triple<Long, Long, Long> {
            val systemsToTake = 3
            val matrix = Array2DRowRealMatrix(systemsToTake * 3, systemsToTake + 3)
            val constants = ArrayRealVector(systemsToTake * 3)
            for (i in 0 until systemsToTake) {
                val idx = i * 3
                matrix.setEntry(idx, i, (vx - hailstones[i].vx).toDouble())
                matrix.setEntry(idx + 1, i, (vy - hailstones[i].vy).toDouble())
                matrix.setEntry(idx + 2, i, (vz - hailstones[i].vz).toDouble())
                matrix.setEntry(idx, systemsToTake, 1.0)
                matrix.setEntry(idx + 1, systemsToTake + 1, 1.0)
                matrix.setEntry(idx + 2, systemsToTake + 2, 1.0)
                constants.setEntry(idx, hailstones[i].px.toDouble())
                constants.setEntry(idx + 1, hailstones[i].py.toDouble())
                constants.setEntry(idx + 2, hailstones[i].pz.toDouble())
            }
            val solver = QRDecomposition(matrix).solver
            val sol = solver.solve(constants)
            return Triple(round(sol.getEntry(systemsToTake)).toLong(),
                round(sol.getEntry(systemsToTake + 1)).toLong(),
                round(sol.getEntry(systemsToTake + 2)).toLong())
        }

        try {
            val (x, y, z) = solveEq()
            val match = hailstones.all { h ->
                val t = (h.px - x).toDouble() / (vx - h.vx).toDouble()
                if (t <= 0) {
                    return@all false
                }
                val eps = 100000
                return@all (abs(x + t * vx - (h.px + t * h.vx)) <= eps &&
                        abs(y + t * vy - (h.py + t * h.vy)) <= eps &&
                        abs(z + t * vz - (h.pz + t * h.vz)) <= eps)
            }
            val maxDiff = hailstones.maxOf { h ->
                val t = (h.px - x).toDouble() / (vx - h.vx).toDouble()
                maxOf(abs(x + t * vx - (h.px + t * h.vx)),
                        abs(y + t * vy - (h.py + t * h.vy)),
                        abs(z + t * vz - (h.pz + t * h.vz)))
            }
            if (maxDiff < 1e8) {
                println("MaxDiff: $maxDiff")
            }
            if (match) {
                println("MATCH $x $y $z $vx $vy $vz")
            }
        } catch (e: Throwable) {
//            e.printStackTrace()
        }
    }

    val size = 500L

//    check(-3L, 1L, 2L)

    for (vx in -size..size) {
        println(vx)
        for (vy in -size..size) {
            (-size..size).toList().parallelStream().forEach {vz ->
                check(vx, vy, vz)
            }
        }
    }
}

fun main() {
//    solve(readInput("day24/sample"), 7, 27)
    solve(readInput("day24/input"), 200000000000000L, 400000000000000L)
}