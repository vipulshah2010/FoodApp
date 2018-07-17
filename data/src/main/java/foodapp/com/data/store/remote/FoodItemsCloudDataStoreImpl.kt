package foodapp.com.data.store.remote

import foodapp.com.data.model.FoodItem
import foodapp.com.data.shared.network.RestApi
import io.reactivex.Single
import javax.inject.Inject

class FoodItemsCloudDataStoreImpl @Inject constructor(private val mRestApi: RestApi) : FoodItemsDataStore {

    override fun getFoodItems(): Single<List<FoodItem>> {
        return mRestApi.getFoodItems().map {
            it.fooditems
        }
    }
}
