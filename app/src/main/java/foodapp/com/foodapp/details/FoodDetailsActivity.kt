package foodapp.com.foodapp.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.squareup.picasso.Picasso
import foodapp.com.foodapp.R
import foodapp.com.foodapp.dashboard.adapter.HeroImageViewsAdapter
import foodapp.com.foodapp.model.FoodItem
import foodapp.com.foodapp.views.CircleTransform
import foodapp.com.foodapp.views.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_food_details.*

class FoodDetailsActivity : AppCompatActivity() {

    companion object {
        private const val ARG_FOOD_ITEM = "food_item"

        fun newInstance(context: Context, foodItem: FoodItem): Intent {
            val intent = Intent(context, FoodDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(ARG_FOOD_ITEM, foodItem)
            intent.putExtras(bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        val foodItem = intent.getParcelableExtra<FoodItem>(ARG_FOOD_ITEM)

        Picasso.get().load(foodItem.profileImage).transform(CircleTransform()).into(profileImageView)
        ViewCompat.setTransitionName(profileImageView, foodItem.profileImage)

        Picasso.get().load(foodItem.heroImage).fit().centerCrop().into(foodImageView)
        ViewCompat.setTransitionName(foodImageView, foodItem.heroImage)

        foodDescTextView.text = foodItem.foodDescription
        ViewCompat.setTransitionName(foodDescTextView, foodItem.foodDescription)

        ViewCompat.setTransitionName(votesButton, foodItem.votes.toString())

        nameTextView.text = foodItem.name

        dateTextView.text = foodItem.date

        votesTextView.text = "${foodItem.votes}"

        dishesViewPager.setPageTransformer(true, DepthPageTransformer())

        val fragmentManager = supportFragmentManager
        dishesViewPager.adapter = HeroImageViewsAdapter(imagesUrls = foodItem.foodImages, manager = fragmentManager)

        tabLayout.setupWithViewPager(dishesViewPager, true)
    }
}