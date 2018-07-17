package foodapp.com.foodapp.foods.ui

import foodapp.com.data.model.FoodItem
import foodapp.com.domain.fooditems.GetFoodItemsInteractor
import foodapp.com.foodapp.base.BasePresenter
import io.reactivex.observers.DisposableSingleObserver
import java.util.*
import javax.inject.Inject

class FoodListPresenterImpl<V : FoodListContract.FoodListMVPView> @Inject
constructor(private val getFoodItemsInteractor: GetFoodItemsInteractor) : BasePresenter<V>(),
        FoodListContract.FoodListPresenter<V> {

    override fun loadFoodItems(forceFetch: Boolean) {
        mvpView!!.showLoading()

        getFoodItemsInteractor.execute(object : DisposableSingleObserver<List<FoodItem>>() {
            override fun onSuccess(foodItems: List<FoodItem>) {
                mvpView!!.hideLoading()
                mFoodItems.addAll(foodItems)
                renderFoodItems(foodItems)
            }

            override fun onError(e: Throwable) {
                mvpView!!.hideLoading()
                handleError(e)
            }
        }, GetFoodItemsInteractor.Input(forceFetch))
    }

    private val mFoodItems: ArrayList<FoodItem> = ArrayList()

    override fun onDetach() {
        getFoodItemsInteractor.dispose()
        super.onDetach()
    }

    private fun renderFoodItems(foodItems: List<FoodItem>?) {
        if (foodItems != null && foodItems.isNotEmpty()) {
            mvpView!!.onLoadFoodItems(foodItems)
        } else {
            mvpView!!.onDisplayEmptyFoodItemsView()
        }
    }
}
