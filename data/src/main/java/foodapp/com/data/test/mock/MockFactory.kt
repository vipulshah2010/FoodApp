package foodapp.com.data.test.mock

import androidx.annotation.VisibleForTesting
import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem

object MockFactory {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun generateFoodItems(exception: Boolean) = when {
        exception -> FoodResult.Error(Throwable())
        else -> FoodResult.Success(arrayListOf<FoodItem>().apply {
            add(MockData.foodItem)
        })
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun generateFoodItem(exception: Boolean) = when {
        exception -> FoodResult.Error(Throwable())
        else -> FoodResult.Success(MockData.foodItem)
    }
}
