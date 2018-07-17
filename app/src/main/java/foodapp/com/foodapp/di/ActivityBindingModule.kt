package foodapp.com.foodapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import foodapp.com.foodapp.foods.ui.FoodListActivity
import foodapp.com.foodapp.foods.ui.FoodListActivityModule

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [(FoodListActivityModule::class)])
    abstract fun foodListActivity(): FoodListActivity
}
