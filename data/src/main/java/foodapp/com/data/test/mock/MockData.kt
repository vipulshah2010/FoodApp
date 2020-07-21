package foodapp.com.data.test.mock

import foodapp.com.data.model.FoodItem
import foodapp.com.data.model.FoodItems
import java.util.*

object MockData {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomInt(): Int = Random().nextInt(1000)

    fun randomLong(): Long = randomInt().toLong()

    fun randomBoolean(): Boolean = Math.random() < 0.5

    fun randomDate(): Date = Date(randomLong())

    val foodItem = FoodItem(
            randomInt(),
            randomString(),
            randomString(),
            randomString(),
            randomInt(),
            arrayListOf(),
            randomString(),
            randomString())

    val foodItems = FoodItems(arrayListOf<FoodItem>().apply {
        add(foodItem)
        add(foodItem)
        add(foodItem)
    })

    val emptyFoodIItems = FoodItems(arrayListOf())

    val throwable = Throwable()
}