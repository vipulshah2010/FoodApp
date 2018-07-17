package foodapp.com.data.repository

import foodapp.com.data.model.FoodItem
import io.reactivex.Single

interface FoodItemsRepository {
    fun getFoodItems(forceNetworkFetch: Boolean): Single<List<FoodItem>>

    fun getFoodItem(id:String): Single<FoodItem>
}
