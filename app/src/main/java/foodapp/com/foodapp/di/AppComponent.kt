package foodapp.com.foodapp.di

import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import foodapp.com.data.FoodItemsDataModule
import foodapp.com.data.shared.network.ClientModule
import foodapp.com.data.shared.network.RetrofitModule
import foodapp.com.foodapp.FoodApp
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBindingModule::class,
    FoodItemsDataModule::class,
    ClientModule::class,
    RetrofitModule::class
])
interface AppComponent : AndroidInjector<FoodApp> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<FoodApp>()
}
