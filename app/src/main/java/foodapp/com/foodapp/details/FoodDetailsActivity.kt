package foodapp.com.foodapp.details

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import com.squareup.picasso.Picasso
import foodapp.com.foodapp.R
import foodapp.com.foodapp.dashboard.adapter.HeroImageViewsAdapter
import foodapp.com.foodapp.model.FoodItem
import foodapp.com.foodapp.views.CircleTransform
import foodapp.com.foodapp.views.DepthPageTransformer
import kotlinx.android.synthetic.main.activity_food_details.*

@SuppressLint("RestrictedApi")
class FoodDetailsActivity : AppCompatActivity() {

    var flag = true
    var pixelDensity: Float = 0.toFloat()
    var alphaAnimation: Animation? = null

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

        pixelDensity = resources.displayMetrics.density
        alphaAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha_anim)

        votesButton.visibility = View.INVISIBLE

        votesButton.post {
            // get the center for the clipping circle
            val cx = votesButton.measuredWidth / 2
            val cy = votesButton.measuredHeight / 2

            // get the final radius for the clipping circle
            val finalRadius = Math.max(votesButton.width, votesButton.height) / 2

            // create the animator for this view (the start radius is zero)
            val anim = ViewAnimationUtils.createCircularReveal(votesButton, cx, cy, 0f, finalRadius.toFloat())
            anim.duration = 400

            // make the view visible and start the animation
            votesButton.visibility = View.VISIBLE
            anim.start()
        }

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

        votesButton.setOnClickListener {

            var x = foodImageFrameLayout.right
            val y = foodImageFrameLayout.bottom
            x -= (28 * pixelDensity + 16 * pixelDensity).toInt()

            val hypotenuse = Math.hypot(foodImageFrameLayout.width.toDouble(), foodImageFrameLayout.height.toDouble()).toInt()

            if (flag) {

                val parameters = linearView.layoutParams as FrameLayout.LayoutParams
                parameters.height = foodImageFrameLayout.height
                linearView.layoutParams = parameters

                val anim = ViewAnimationUtils.createCircularReveal(linearView, x, y, 0f, hypotenuse.toFloat())
                anim.duration = 700

                anim.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animator: Animator) {

                    }

                    override fun onAnimationEnd(animator: Animator) {
                        layoutButtons.visibility = View.VISIBLE
                        layoutButtons.startAnimation(alphaAnimation)
                    }

                    override fun onAnimationCancel(animator: Animator) {

                    }

                    override fun onAnimationRepeat(animator: Animator) {

                    }
                })

                linearView.visibility = View.VISIBLE
                anim.start()

                flag = false
            } else {

                val anim = ViewAnimationUtils.createCircularReveal(linearView, x, y, hypotenuse.toFloat(), 0f)
                anim.duration = 400

                anim.addListener(object : Animator.AnimatorListener {
                    override fun onAnimationStart(animator: Animator) {

                    }

                    override fun onAnimationEnd(animator: Animator) {
                        linearView.visibility = View.GONE
                        layoutButtons.visibility = View.GONE
                    }

                    override fun onAnimationCancel(animator: Animator) {

                    }

                    override fun onAnimationRepeat(animator: Animator) {

                    }
                })

                anim.start()
                flag = true
            }
        }
    }
}