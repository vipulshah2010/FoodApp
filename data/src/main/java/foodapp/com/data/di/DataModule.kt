package foodapp.com.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import foodapp.com.data.dispatcher.DispatcherProvider
import foodapp.com.data.dispatcher.DispatcherProviderImpl
import foodapp.com.data.repository.FoodRepository
import foodapp.com.data.repository.FoodRepositoryImpl
import foodapp.com.data.store.local.LocalDataStore
import foodapp.com.data.store.local.LocalDataStoreImpl
import foodapp.com.data.store.remote.CloudCloudDataStoreImpl
import foodapp.com.data.store.remote.CloudDataStore

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindLocalDataStore(localDataStoreImpl: LocalDataStoreImpl): LocalDataStore

    @Binds
    abstract fun bindCloudDataStore(cloudCloudDataStoreImpl: CloudCloudDataStoreImpl): CloudDataStore

    @Binds
    abstract fun bindDispatcher(dispatcherImpl: DispatcherProviderImpl): DispatcherProvider

    @Binds
    abstract fun bindRepository(foodRepositoryImpl: FoodRepositoryImpl): FoodRepository
}