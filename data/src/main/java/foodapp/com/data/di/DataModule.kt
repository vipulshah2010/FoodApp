package foodapp.com.data.di

import android.content.Context
import androidx.room.Room
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import foodapp.com.data.FoodDatabase
import foodapp.com.data.repository.FoodRepository
import foodapp.com.data.store.local.LocalDataStore
import foodapp.com.data.store.remote.CloudDataStore
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindRepository(FoodRepositoryImpl: FoodRepository): FoodRepository

    @Binds
    abstract fun bindLocalDataStore(LocalDataStoreImpl: LocalDataStore): LocalDataStore

    @Binds
    abstract fun bindCloudDataStore(CloudCloudDataStoreImpl: CloudDataStore): CloudDataStore

    @Provides
    @Singleton
    fun provideFoodDatabase(context: Context): FoodDatabase {
        return Room.inMemoryDatabaseBuilder(context, FoodDatabase::class.java).allowMainThreadQueries().build()
    }
}