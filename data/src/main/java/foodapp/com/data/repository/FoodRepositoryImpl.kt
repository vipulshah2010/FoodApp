package foodapp.com.data.repository

import foodapp.com.data.FoodResult
import foodapp.com.data.dispatcher.DispatcherProvider
import foodapp.com.data.model.FoodItem
import foodapp.com.data.store.local.LocalDataStore
import foodapp.com.data.store.remote.CloudDataStore
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class FoodRepositoryImpl @Inject
constructor(private val localDataStore: LocalDataStore,
            private val remoteCloudDataStore: CloudDataStore,
            private val dispatcher: DispatcherProvider) : FoodRepository {

    override fun getFoodItem(id: Int) = localDataStore
            .getFoodItem(id)
            .map { FoodResult.Success((it)) }
            .flowOn(dispatcher.io)

    @FlowPreview
    override fun getFoodItems(forceNetworkFetch: Boolean): Flow<FoodResult<List<FoodItem>>> {

        val networkThenDb = remoteCloudDataStore.getFoodItems().flatMapConcat {
            localDataStore.addFoodItems(it).flatMapConcat {
                flow { emit(FoodResult.Success(it)) }
            }
        }.flowOn(dispatcher.io)

        return if (forceNetworkFetch) {
            networkThenDb
        } else {
            localDataStore.getFoodItems().flatMapConcat {
                if (it.count() > 0) {
                    flow { emit(FoodResult.Success(it)) }
                } else {
                    networkThenDb
                }
            }.flowOn(dispatcher.io)
        }
    }
}
