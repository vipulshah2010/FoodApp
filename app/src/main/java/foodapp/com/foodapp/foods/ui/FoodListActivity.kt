package foodapp.com.foodapp.foods.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import foodapp.com.foodapp.R
import foodapp.com.foodapp.details.FoodDetailsActivity
import foodapp.com.foodapp.foods.adapter.FoodListAdapter
import foodapp.com.foodapp.model.FoodItem
import kotlinx.android.synthetic.main.activity_food_list.*


class FoodListActivity : AppCompatActivity() {

    companion object {
        private const val EXTRA_CIRCULAR_REVEAL_X = "reveal_x"
        private const val EXTRA_CIRCULAR_REVEAL_Y = "reveal_y"

        fun newInstance(context: Context, revealX: Int, revealY: Int): Intent {
            val intent = Intent(context, FoodListActivity::class.java)
            intent.putExtra(EXTRA_CIRCULAR_REVEAL_X, revealX)
            intent.putExtra(EXTRA_CIRCULAR_REVEAL_Y, revealY)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_list)

        containerLayout.visibility = View.INVISIBLE

        val revealX = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_X, 0)
        val revealY = intent.getIntExtra(EXTRA_CIRCULAR_REVEAL_Y, 0)

        val viewTreeObserver = containerLayout.viewTreeObserver
        if (viewTreeObserver.isAlive) {
            viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {
                    revealActivity(revealX, revealY)
                    containerLayout.viewTreeObserver.removeOnGlobalLayoutListener(this)
                }
            })
        }

        val foodItems = arrayListOf<FoodItem>()

        foodItems.add(FoodItem(
                "https://randomuser.me/api/portraits/men/61.jpg",
                "Jake Wharton",
                "Today 1:45 PM",
                1234,
                "http://goldentimesnewwoman.com/WSIEFBT/passata%20beans%201.jpg",
                getString(R.string.food_quote_1),
                arrayListOf(
                        "http://iamafoodblog.com/wp-content/uploads/2015/06/shrimp-and-zucchini-noodles-11.jpg",
                        "http://iamafoodblog.com/wp-content/uploads/2014/10/IAM_0739w1.jpg",
                        "http://iamafoodblog.com/wp-content/uploads/2015/03/broccoli-soba-bowl-4.jpg")))

        foodItems.add(FoodItem(
                "https://randomuser.me/api/portraits/men/62.jpg",
                "Steve Smith",
                "Today 2:30 PM",
                5678,
                "http://goldentimesnewwoman.com/WSIEFBT/Samosas%20with%20chia%20filling%209.jpg",
                getString(R.string.food_quote_2),
                arrayListOf(
                        "http://iamafoodblog.com/wp-content/uploads/2015/06/shrimp-and-zucchini-noodles-11.jpg",
                        "http://iamafoodblog.com/wp-content/uploads/2014/10/IAM_0739w1.jpg",
                        "http://iamafoodblog.com/wp-content/uploads/2015/03/broccoli-soba-bowl-4.jpg")))

        foodItems.add(FoodItem(
                "https://randomuser.me/api/portraits/men/63.jpg",
                "Joe Root",
                "Today 9:45 AM",
                1145,
                "http://goldentimesnewwoman.com/WSIEFBT/Owsianka-na-zimno-2.jpg",
                getString(R.string.food_quote_3),
                arrayListOf(
                        "http://iamafoodblog.com/wp-content/uploads/2015/06/shrimp-and-zucchini-noodles-11.jpg",
                        "http://iamafoodblog.com/wp-content/uploads/2014/10/IAM_0739w1.jpg",
                        "http://iamafoodblog.com/wp-content/uploads/2015/03/broccoli-soba-bowl-4.jpg")))

        foodListRecyclerView.adapter = FoodListAdapter(foodItems,
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

    private fun revealActivity(x: Int, y: Int) {
        val finalRadius = (Math.max(containerLayout.width, containerLayout.height) * 1.1).toFloat()

        // create the animator for this view (the start radius is zero)
        val circularReveal = ViewAnimationUtils.createCircularReveal(containerLayout, x, y, 0f, finalRadius)
        circularReveal.duration = 400
        circularReveal.interpolator = AccelerateInterpolator()

        // make the view visible and start the animation
        containerLayout.visibility = View.VISIBLE
        circularReveal.start()
    }

}
