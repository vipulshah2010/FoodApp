package foodapp.com.foodapp.test

import java.util.*

object MockData {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomInt(): Int = Random().nextInt(1000)

    fun randomLong(): Long = randomInt().toLong()

    fun randomBoolean(): Boolean = Math.random() < 0.5

    fun randomDate(): Date = Date(randomLong())
}