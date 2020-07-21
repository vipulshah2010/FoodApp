package foodapp.com.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import foodapp.com.data.BuildConfig
import foodapp.com.data.network.utils.ByteUtils
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object ClientModule {

    @Named("cacheSize")
    @Provides
    fun provideCacheSize(): Long {
        return ByteUtils.toBytes(10, ByteUtils.ByteUnit.MB)
    }

    @Named("cacheMaxAge")
    @Provides
    fun provideCacheMaxAgeMinutes(): Int {
        return 2
    }

    @Named("cacheMaxStale")
    @Provides
    fun provideCacheMaxStaleDays(): Int {
        return 7
    }

    @Named("retryCount")
    @Provides
    fun provideApiRetryCount(): Int {
        return 1
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val okHttpClient = Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

        //show logs if app is in Debug mode
        if (BuildConfig.DEBUG)
            okHttpClient.addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })

        return okHttpClient.build()
    }
}