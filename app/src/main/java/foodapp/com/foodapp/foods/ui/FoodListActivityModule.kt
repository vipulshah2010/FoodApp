package foodapp.com.foodapp.foods.ui

import dagger.Binds
import dagger.Module

@Module
abstract class FoodListActivityModule {

    @Binds
    abstract fun providePresenter(
            presenter: FoodListPresenterImpl<FoodListContract.FoodListMVPView>):
            FoodListContract.FoodListPresenter<FoodListContract.FoodListMVPView>
}
