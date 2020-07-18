package foodapp.com.data

import dagger.Binds
import dagger.Module
import foodapp.com.data.repository.FoodRepository
import foodapp.com.data.repository.FoodRepositoryImpl
import foodapp.com.data.store.local.LocalDataStore
import foodapp.com.data.store.local.LocalDataStoreImpl
import foodapp.com.data.store.remote.CloudCloudDataStoreImpl
import foodapp.com.data.store.remote.CloudDataStore

@Module
abstract class FoodDataModule {

    @Binds
    abstract fun provideFoodItemsRepository(repository: FoodRepositoryImpl): FoodRepository

    @Binds
    abstract fun provideFoodItemsCloudDataStore(dataStore: CloudCloudDataStoreImpl): CloudDataStore

    @Binds
    abstract fun provideFoodItemsLocalDataStore(dataStore: LocalDataStoreImpl): LocalDataStore
}
