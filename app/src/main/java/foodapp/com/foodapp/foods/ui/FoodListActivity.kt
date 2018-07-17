package foodapp.com.foodapp.foods.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import dagger.android.AndroidInjection
import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.ErrorType
import foodapp.com.foodapp.R
import foodapp.com.foodapp.details.FoodDetailsActivity
import foodapp.com.foodapp.foods.adapter.FoodListAdapter
import kotlinx.android.synthetic.main.activity_food_list.*
import javax.inject.Inject

class FoodListActivity : AppCompatActivity(), FoodListContract.FoodListMVPView {

    override fun hideLoading() {
        // do nothing
    }

    override fun onError(type: ErrorType) {
        // do nothing
    }

    override fun showLoading() {
        // do nothing
    }

    override fun onLoadFoodItems(foodItems: List<FoodItem>) {
        if (foodListRecyclerView.adapter != null) {
            (foodListRecyclerView.adapter as FoodListAdapter).setFoodItems(foodItems)
        }
    }

    override fun onDisplayEmptyFoodItemsView() {
        // do nothing
    }

    @Inject
    lateinit var mPresenter: FoodListContract.FoodListPresenter<FoodListContract.FoodListMVPView>

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, FoodListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AndroidInjection.inject(this)

        mPresenter.onAttach(this)

        setContentView(R.layout.activity_food_list)

        setupAdapter()

        mPresenter.loadFoodItems(false)
    }

    private fun setupAdapter() {
        foodListRecyclerView.adapter = FoodListAdapter(
                listener = { foodItem: FoodItem,
                             foodImageView: ImageView,
                             profileImageView: ImageView,
                             heartImageView: ImageView,
                             foodDescTextView: TextView ->

                    val p1 = androidx.core.util.Pair.create<View, String>(foodImageView, foodItem.heroImage)
                    val p2 = androidx.core.util.Pair.create<View, String>(foodDescTextView, foodItem.foodDescription)
                    val p3 = androidx.core.util.Pair.create<View, String>(profileImageView, foodItem.profileImage)
                    val p4 = androidx.core.util.Pair.create<View, String>(heartImageView, foodItem.votes.toString())
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@FoodListActivity, p1, p2, p3, p4)

                    startActivity(FoodDetailsActivity.newInstance(this@FoodListActivity, foodItem), options.toBundle())
                })
    }
}
