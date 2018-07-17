package foodapp.com.foodapp.di

import android.content.Context
import androidx.room.Room
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import foodapp.com.data.FoodItemsDatabase
import foodapp.com.data.shared.ExecutionThread
import foodapp.com.data.shared.PostExecutionThread
import foodapp.com.domain.shared.IOThread
import foodapp.com.domain.shared.UIThread
import foodapp.com.foodapp.AppConstants
import foodapp.com.foodapp.FoodApp
import okhttp3.HttpUrl
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Provides
    fun provideContext(foodApp: FoodApp): Context {
        return foodApp.applicationContext
    }

    @Provides
    fun providesIOThread(ioThread: IOThread): ExecutionThread {
        return ioThread
    }

    @Provides
    fun providesUIThread(uiThread: UIThread): PostExecutionThread {
        return uiThread
    }

    @Provides
    fun provideEndpoint(): HttpUrl {
        return HttpUrl.parse(AppConstants.BASE_URL)!!
    }

    @Provides
    @Singleton
    fun provideFoodItemsDatabase(context: Context): FoodItemsDatabase {
        return Room.inMemoryDatabaseBuilder(context, FoodItemsDatabase::class.java).allowMainThreadQueries().build()
    }
}
