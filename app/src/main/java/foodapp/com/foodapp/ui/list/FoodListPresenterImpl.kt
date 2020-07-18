package foodapp.com.foodapp.ui.list

import foodapp.com.data.model.FoodItem
import foodapp.com.domain.fooditems.FoodUsecase
import io.reactivex.observers.DisposableSingleObserver
import java.util.*
import javax.inject.Inject

class FoodListPresenterImpl<V : FoodListContract.FoodListMVPView> @Inject
constructor(private val foodUsecase: FoodUsecase) : BasePresenter<V>(),
        FoodListContract.FoodListPresenter<V> {

    override fun loadFoodItems(forceFetch: Boolean) {
        mvpView!!.showLoading()

        foodUsecase.execute(object : DisposableSingleObserver<List<FoodItem>>() {
            override fun onSuccess(foodItems: List<FoodItem>) {
                mvpView!!.hideLoading()
                mFoodItems.addAll(foodItems)
                renderFoodItems(foodItems)
            }

            override fun onError(e: Throwable) {
                mvpView!!.hideLoading()
                handleError(e)
            }
        }, FoodUsecase.Input(forceFetch))
    }

    private val mFoodItems: ArrayList<FoodItem> = ArrayList()

    override fun onDetach() {
        foodUsecase.dispose()
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
