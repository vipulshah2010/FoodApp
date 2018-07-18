package foodapp.com.foodapp.foods.di

import dagger.Binds
import dagger.Module
import foodapp.com.foodapp.foods.ui.FoodListContract
import foodapp.com.foodapp.foods.ui.FoodListPresenterImpl

@Module
abstract class FoodListActivityModule {

    @Binds
    abstract fun providePresenter(presenter: FoodListPresenterImpl<FoodListContract.FoodListMVPView>):
            FoodListContract.FoodListPresenter<FoodListContract.FoodListMVPView>
}
