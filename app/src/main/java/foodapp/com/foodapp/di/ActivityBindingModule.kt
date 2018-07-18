package foodapp.com.foodapp.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import foodapp.com.foodapp.details.di.FoodDetailsActivityModule
import foodapp.com.foodapp.details.ui.FoodDetailsActivity
import foodapp.com.foodapp.foods.di.FoodListActivityModule
import foodapp.com.foodapp.foods.ui.FoodListActivity
import foodapp.com.foodapp.main.di.MainActivityModule
import foodapp.com.foodapp.main.ui.MainActivity

@Module
abstract class ActivityBindingModule {

    @ContributesAndroidInjector(modules = [(FoodListActivityModule::class)])
    abstract fun foodListActivity(): FoodListActivity

    @ContributesAndroidInjector(modules = [(MainActivityModule::class)])
    abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(modules = [(FoodDetailsActivityModule::class)])
    abstract fun foodDetailsActivity(): FoodDetailsActivity
}
