package foodapp.com.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import foodapp.com.data.network.RestApi
import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.CallAdapter.Factory
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
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
}
