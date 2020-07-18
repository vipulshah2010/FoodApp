package foodapp.com.data.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import foodapp.com.data.FoodDatabase
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class FoodDataModule {

    @Provides
    @Singleton
    fun provideFoodDatabase(application: Application): FoodDatabase {
        return Room.inMemoryDatabaseBuilder(application, FoodDatabase::class.java)
                .allowMainThreadQueries().build()
    }
}
