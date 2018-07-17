package foodapp.com.foodapp.foods.ui

import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.base.MvpPresenter
import foodapp.com.foodapp.base.MvpView

class FoodListContract {

    interface FoodListMVPView : MvpView {
        fun onLoadFoodItems(foodItems: List<FoodItem>)

        fun onDisplayEmptyFoodItemsView()
    }

    interface FoodListPresenter<V : FoodListContract.FoodListMVPView> : MvpPresenter<V> {
        fun loadFoodItems(forceFetch: Boolean)
    }
}
