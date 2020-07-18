package foodapp.com.foodapp.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import dagger.android.AndroidInjection
import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.ErrorType
import foodapp.com.foodapp.R
import foodapp.com.foodapp.main.ui.detail.FoodDetailsActivity
import foodapp.com.foodapp.ui.detail.FoodDetailsActivity
import kotlinx.android.synthetic.main.activity_food_list.*
import javax.inject.Inject

class FoodListActivity : AppCompatActivity(), FoodListContract.FoodListMVPView {

    override fun onDisplayEmptyFoodItemsView() {
    }

    override fun showLoading() {
        placeholderView.visibility = View.VISIBLE
        foodListRecyclerView.visibility = View.GONE

        placeholderView.showProgress()
    }

    override fun hideLoading() {
        placeholderView.showProgress(false)
    }

    override fun onError(type: ErrorType) {
        foodListRecyclerView.visibility = View.GONE
        placeholderView.showEmptyView()
        placeholderView.setContents(type.icon, type.title, type.subtitle, R.string.inv_try_again) {
            mPresenter.loadFoodItems(false)
        }
    }

    override fun onLoadFoodItems(foodItems: List<FoodItem>) {
        placeholderView.showEmptyView(false)
        placeholderView.visibility = View.GONE
        foodListRecyclerView.visibility = View.VISIBLE

        if (foodListRecyclerView.adapter != null) {
            (foodListRecyclerView.adapter as FoodListAdapter).setFoodItems(foodItems)
        }
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

                    val p1 = Pair.create<View, String>(foodImageView, foodItem.heroImage)
                    val p2 = Pair.create<View, String>(foodDescTextView, foodItem.foodDescription)
                    val p3 = Pair.create<View, String>(profileImageView, foodItem.profileImage)
                    val p4 = Pair.create<View, String>(heartImageView, foodItem.votes.toString())
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this@FoodListActivity, p1, p2, p3, p4)

                    startActivity(FoodDetailsActivity.newInstance(this@FoodListActivity, foodItem), options.toBundle())
                })
    }
}
