package foodapp.com.data.store.local

import foodapp.com.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

interface LocalDataStore {

    fun addFoodItems(foodItems: List<FoodItem>): Flow<List<FoodItem>>

    fun getFoodItem(id: String): Flow<FoodItem>

    fun getFoodItems(): Flow<List<FoodItem>>
}
