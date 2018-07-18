package foodapp.com.data.repository

import foodapp.com.data.model.FoodItem
import foodapp.com.data.store.local.FoodItemsLocalDataStore
import foodapp.com.data.store.remote.FoodItemsCloudDataStore
import io.reactivex.Single
import javax.inject.Inject

class FoodItemsRepositoryImpl @Inject
constructor(private val localDataStore: FoodItemsLocalDataStore,
            private val remoteCloudDataStore: FoodItemsCloudDataStore) : FoodItemsRepository {

    override fun getFoodItem(id: String): Single<FoodItem> {
        return localDataStore.getFoodItem(id)
    }

    override fun getFoodItems(forceNetworkFetch: Boolean): Single<List<FoodItem>> {
        val fromNetwork = remoteCloudDataStore.getFoodItems()
                .flatMap {
                    localDataStore.addFoodItems(it)
                }

        return if (forceNetworkFetch)
            fromNetwork
        else
            localDataStore.getFoodItems().onErrorResumeNext {
                fromNetwork
            }
    }
}
