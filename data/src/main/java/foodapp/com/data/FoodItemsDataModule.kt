package foodapp.com.data

import dagger.Binds
import dagger.Module
import foodapp.com.data.repository.FoodItemsRepository
import foodapp.com.data.repository.FoodItemsRepositoryImpl
import foodapp.com.data.store.local.FoodItemsLocalDataStore
import foodapp.com.data.store.local.FoodItemsLocalDataStoreImpl
import foodapp.com.data.store.remote.FoodItemsCloudCloudDataStoreImpl
import foodapp.com.data.store.remote.FoodItemsCloudDataStore

@Module
abstract class FoodItemsDataModule {

    @Binds
    abstract fun provideFoodItemsRepository(repository: FoodItemsRepositoryImpl): FoodItemsRepository

    @Binds
    abstract fun provideFoodItemsCloudDataStore(dataStore: FoodItemsCloudCloudDataStoreImpl): FoodItemsCloudDataStore

    @Binds
    abstract fun provideFoodItemsLocalDataStore(dataStore: FoodItemsLocalDataStoreImpl): FoodItemsLocalDataStore
}
