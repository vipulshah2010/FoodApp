package foodapp.com.foodapp.ui.list

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.lifecycle.Observer
import dagger.hilt.android.AndroidEntryPoint
import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.R
import foodapp.com.foodapp.base.BaseActivity
import foodapp.com.foodapp.databinding.ActivityFoodListBinding
import foodapp.com.foodapp.ui.detail.FoodDetailsActivity

@AndroidEntryPoint
class FoodListActivity : BaseActivity<ActivityFoodListBinding>() {

    private val viewModel: FoodViewModel by viewModels()

    private fun showLoading() {
        binding.foodListRecyclerView.visibility = View.GONE
        binding.placeholderView.visibility = View.VISIBLE
        binding.placeholderView.showProgress()
    }

    private fun hideLoading() {
        binding.placeholderView.showProgress(false)
    }

    private fun onError(throwable: Throwable) {
        binding.foodListRecyclerView.visibility = View.GONE
        binding.placeholderView.showEmptyView()
        val errorType = viewModel.parseError(throwable)
        binding.placeholderView.setContents(errorType.icon,
                errorType.title,
                errorType.subtitle,
                R.string.inv_try_again) {
            viewModel.getFoodItems(true)
        }
    }

    private fun onLoadFoodItems(foodItems: List<FoodItem>) {
        binding.placeholderView.showEmptyView(false)
        binding.placeholderView.visibility = View.GONE
        binding.foodListRecyclerView.visibility = View.VISIBLE

        if (binding.foodListRecyclerView.adapter != null) {
            (binding.foodListRecyclerView.adapter as FoodListAdapter).setFoodItems(foodItems)
        }
    }

    companion object {
        fun newInstance(context: Context): Intent {
            return Intent(context, FoodListActivity::class.java)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupAdapter()

        viewModel.foodItemsLiveData.observe(this, Observer {
            when (it) {
                FoodResult.Loading -> {
                    showLoading()
                }
                is FoodResult.Error -> {
                    hideLoading()
                    onError(it.throwable)
                }
                is FoodResult.Success -> {
                    hideLoading()
                    onLoadFoodItems(it.data)
                }
            }
        })

        viewModel.getFoodItems(true)
    }

    private fun setupAdapter() {
        binding.foodListRecyclerView.adapter = FoodListAdapter(
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

                    startActivity(FoodDetailsActivity.newInstance(this@FoodListActivity,
                            foodItem.id), options.toBundle())
                })
    }

    override fun getBinding(inflater: LayoutInflater) = ActivityFoodListBinding.inflate(inflater)
}
