package foodapp.com.foodapp.test

import androidx.annotation.VisibleForTesting
import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem

object MockFactory {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun createFoodItems(exception: Boolean) = when {
        exception -> FoodResult.Error(Throwable())
        else -> FoodResult.Success(arrayListOf<FoodItem>().apply {
            add(FoodItem(
                    MockData.randomInt(),
                    MockData.randomString(),
                    MockData.randomString(),
                    MockData.randomString(),
                    MockData.randomInt(),
                    arrayListOf(),
                    MockData.randomString(),
                    MockData.randomString())
            )
        })
    }
}
