package foodapp.com.data.store.remote

import foodapp.com.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

interface CloudDataStore {
    fun getFoodItems(): Flow<List<FoodItem>>
}
