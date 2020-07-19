package foodapp.com.data.repository

import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem
import kotlinx.coroutines.flow.Flow

interface FoodRepository {
    fun getFoodItems(forceNetworkFetch: Boolean): Flow<FoodResult<List<FoodItem>>>

    fun getFoodItem(id: Int): Flow<FoodResult<FoodItem>>
}
