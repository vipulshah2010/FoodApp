package foodapp.com.data.shared.network

import dagger.Module
import dagger.Provides
import foodapp.com.data.shared.ExecutionThread
import io.reactivex.plugins.RxJavaPlugins
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.CallAdapter.Factory
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import javax.inject.Named

@Module
class RetrofitModule {

    @Provides
    fun provideRestApi(retrofit: Retrofit): RestApi {
        return retrofit.create(RestApi::class.java)
    }

    @Provides
    fun provideRetrofit(baseUrl: HttpUrl, @Named("gson") converterFactory: Converter.Factory,
                        callAdapterFactory: Factory, okHttpClient: OkHttpClient): Retrofit {
        return Builder().baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(callAdapterFactory)
                .client(okHttpClient)
                .build()
    }

    @Provides
    @Named("gson")
    fun provideGsonConverterFactory(): Converter.Factory {
        return GsonConverterFactory.create()
    }

    @Provides
    fun provideRxJavaCallAdapterFactory(executionThread: ExecutionThread): Factory {
        RxJavaPlugins.setErrorHandler { throwable -> Timber.e(throwable.message) }
        return RxJava2CallAdapterFactory.createWithScheduler(executionThread.scheduler)
    }
}
