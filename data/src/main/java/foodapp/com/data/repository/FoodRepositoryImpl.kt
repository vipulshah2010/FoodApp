package foodapp.com.data.repository

import foodapp.com.data.DispatcherProvider
import foodapp.com.data.model.FoodItem
import foodapp.com.data.store.local.LocalDataStore
import foodapp.com.data.store.remote.CloudDataStore
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FoodRepositoryImpl @Inject
constructor(private val localDataStore: LocalDataStore,
            private val remoteCloudDataStore: CloudDataStore,
            private val dispatcher: DispatcherProvider) : FoodRepository {

    override fun getFoodItem(id: String) = localDataStore
            .getFoodItem(id)
            .flowOn(dispatcher.io)

    @FlowPreview
    override fun getFoodItems(forceNetworkFetch: Boolean): Flow<List<FoodItem>> {

        val networkThenDb = remoteCloudDataStore.getFoodItems().flatMapConcat {
            localDataStore.addFoodItems(it).flatMapConcat {
                flow { emit(it) }
            }
        }.flowOn(dispatcher.io)

        return if (forceNetworkFetch) {
            networkThenDb
        } else {
            localDataStore.getFoodItems().flatMapConcat {
                if (it.count() > 0) {
                    flow { emit(it) }
                } else {
                    networkThenDb
                }
            }.flowOn(dispatcher.io)
        }
    }
}
