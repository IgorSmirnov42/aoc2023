package day05

fun main(args: Array<String>) {

    data class Mapper(val from: Long, val to: Long, val range: Long) {
        private fun matches(poLong: Long): Boolean {
            return (to..<to + range).contains(poLong)
        }

        fun transform(poLong: Long): Long? {
            if (!matches(poLong)) return null
            return from + (poLong - to)
        }
    }

    val seeds = readln().split(':')[1].split(' ').filter { it.isNotBlank() }.map { it.trim().toLong() }
    val seedToSoil = mutableListOf<Mapper>()
    var x = readlnOrNull()
    x = readlnOrNull()
    x = readlnOrNull()
    while (!x.isNullOrBlank()) {
        val (from, to, range) = x.split(' ').map { it.toLong() }
        seedToSoil.add(Mapper(from, to, range))
        x = readlnOrNull()
    }

    val soilToFertilizer = mutableListOf<Mapper>()
    x = readlnOrNull()
    x = readlnOrNull()
    while (!x.isNullOrBlank()) {
        val (from, to, range) = x.split(' ').map { it.toLong() }
        soilToFertilizer.add(Mapper(from, to, range))
        x = readlnOrNull()
    }

    val fertilizerToWater = mutableListOf<Mapper>()
    x = readlnOrNull()
    x = readlnOrNull()
    while (!x.isNullOrBlank()) {
        val (from, to, range) = x.split(' ').map { it.toLong() }
        fertilizerToWater.add(Mapper(from, to, range))
        x = readlnOrNull()
    }

    val waterToLight = mutableListOf<Mapper>()
    x = readlnOrNull()
    x = readlnOrNull()
    while (!x.isNullOrBlank()) {
        val (from, to, range) = x.split(' ').map { it.toLong() }
        waterToLight.add(Mapper(from, to, range))
        x = readlnOrNull()
    }

    val lightToTemp = mutableListOf<Mapper>()
    x = readlnOrNull()
    x = readlnOrNull()
    while (!x.isNullOrBlank()) {
        val (from, to, range) = x.split(' ').map { it.toLong() }
        lightToTemp.add(Mapper(from, to, range))
        x = readlnOrNull()
    }

    val tempToHumid = mutableListOf<Mapper>()
    x = readlnOrNull()
    x = readlnOrNull()
    while (!x.isNullOrBlank()) {
        val (from, to, range) = x.split(' ').map { it.toLong() }
        tempToHumid.add(Mapper(from, to, range))
        x = readlnOrNull()
    }

    val humidToLoc = mutableListOf<Mapper>()
    x = readlnOrNull()
    x = readlnOrNull()
    while (!x.isNullOrBlank()) {
        val (from, to, range) = x.split(' ').map { it.toLong() }
        humidToLoc.add(Mapper(from, to, range))
        x = readlnOrNull()
    }

    val mappers = listOf(seedToSoil, soilToFertilizer, fertilizerToWater, waterToLight, lightToTemp, tempToHumid, humidToLoc)
    val locations = seeds.map {
        var x = it
        for (mapper in mappers) {
            x = mapper.map { it.transform(x) }.filter { it != null }.firstOrNull() ?: x
        }
        x
    }
    println(locations.min())
}