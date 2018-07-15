package foodapp.com.foodapp.dashboard.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import foodapp.com.foodapp.R
import foodapp.com.foodapp.dashboard.adapter.HeroImageViewsAdapter
import foodapp.com.foodapp.foods.ui.FoodListActivity
import foodapp.com.foodapp.views.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentManager = supportFragmentManager

        heroImagesViewPager.setPageTransformer(true, DepthPageTransformer())
        heroImagesViewPager.adapter = HeroImageViewsAdapter(
                imagesRes = arrayListOf(R.drawable.frame1, R.drawable.frame2, R.drawable.frame3),
                manager = fragmentManager
        )

        tabLayout.setupWithViewPager(heroImagesViewPager, true)

        startButton.setOnClickListener {

            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(this, it, "transition")
            val revealX = (it.x + it.width / 2).toInt()
            val revealY = (it.y + it.height / 2).toInt()

            startActivity(FoodListActivity.newInstance(this@MainActivity, revealX, revealY), options.toBundle())
        }
    }
}
