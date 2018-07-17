package foodapp.com.data.store.local

import foodapp.com.data.model.FoodItem
import io.reactivex.Single

interface FoodItemsLocalDataStore {

    fun addFoodItems(foodItems: List<FoodItem>): Single<List<FoodItem>>

    fun getFoodItem(id: String): Single<FoodItem>

    fun getFoodItems(): Single<List<FoodItem>>
}
