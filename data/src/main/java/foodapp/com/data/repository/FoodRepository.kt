package foodapp.com.data.repository

import foodapp.com.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoodItems(forceNetworkFetch: Boolean): Flow<List<FoodItem>>

    fun getFoodItem(id: String): Flow<FoodItem>
}
