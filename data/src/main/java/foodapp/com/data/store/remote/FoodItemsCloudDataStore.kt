package foodapp.com.data.store.remote

import foodapp.com.data.model.FoodItem
import io.reactivex.Single

interface FoodItemsCloudDataStore {
    fun getFoodItems(): Single<List<FoodItem>>
}
