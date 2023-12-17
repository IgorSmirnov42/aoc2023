package day05

private data class Mapper(val from: Long, val to: Long, val rangeLen: Long) {

    fun transform(rangeTo: LongRange): Pair<LongRange, List<LongRange>> {
        if (rangeTo.last < to) {
            return LongRange.EMPTY to listOf(rangeTo)
        }
        if (rangeTo.first >= to + rangeLen) {
            return LongRange.EMPTY to listOf(rangeTo)
        }
        val start = maxOf(to, rangeTo.first)
        val end = minOf(to + rangeLen - 1, rangeTo.last)
        return LongRange(from + start - to, from + end - to) to listOf(LongRange(rangeTo.start, start - 1), LongRange(end + 1, rangeTo.last))
    }
}

fun main(args: Array<String>) {
    val seeds = readln().split(':')[1].split(' ').filter { it.isNotBlank() }.map { it.trim().toLong() }.windowed(2, step = 2).map {
        LongRange(it[0], it[0] + it[1] - 1)
    }

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
        var x = listOf(it)
        for (mapper in mappers) {
            var unmappedRanges = x
            val newX = mutableListOf<LongRange>()
            for (mapperRange in mapper) {
                val newUnmappedRanges = mutableListOf<LongRange>()
                for (unmappedRange in unmappedRanges) {
                    val (mappedRange, unmapped) = mapperRange.transform(unmappedRange)
                    if (!mappedRange.isEmpty()) {
                        newX.add(mappedRange)
                    }
                    newUnmappedRanges.addAll(unmapped.filterNot { it.isEmpty() })
                }
                unmappedRanges = newUnmappedRanges
            }
            newX.addAll(unmappedRanges)
            x = newX
        }
        x
    }
    println(locations.minOf { it.minOf { it.first } })
}