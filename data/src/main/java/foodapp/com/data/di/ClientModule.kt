package foodapp.com.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import foodapp.com.data.BuildConfig
import foodapp.com.data.network.utils.ByteUtils
import foodapp.com.data.qualifiers.NetworkConnectTimeout
import foodapp.com.data.qualifiers.NetworkReadTimeout
import okhttp3.OkHttpClient
import okhttp3.OkHttpClient.Builder
import okhttp3.logging.HttpLoggingInterceptor
import java.util.concurrent.TimeUnit
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
class ClientModule {

    val isDebug: Boolean
        @Named("isDebug")
        @Provides
        get() = BuildConfig.DEBUG

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

    @NetworkConnectTimeout
    @Provides
    fun networkConnectTimeoutInSeconds(): Int {
        return 60
    }

    @NetworkReadTimeout
    @Provides
    fun networkReadTimeoutInSeconds(): Int {
        return 60
    }

    @Provides
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor,
                            @NetworkConnectTimeout networkConnectTimeoutInSeconds: Int,
                            @NetworkReadTimeout networkReadTimeoutInSeconds: Int,
                            @Named("isDebug") isDebug: Boolean): OkHttpClient {

        val okHttpClient = Builder()
                .readTimeout(networkReadTimeoutInSeconds.toLong(), TimeUnit.SECONDS)
                .connectTimeout(networkConnectTimeoutInSeconds.toLong(), TimeUnit.SECONDS)

        //show logs if app is in Debug mode
        if (isDebug) okHttpClient.addInterceptor(loggingInterceptor)

        return okHttpClient.build()
    }

    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = HttpLoggingInterceptor.Level.BODY
        return logging
    }
}