package foodapp.com.foodapp.ui.detail

import android.animation.Animator
import android.content.Context
import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.view.ViewCompat
import androidx.lifecycle.Observer
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import dagger.hilt.android.AndroidEntryPoint
import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.R
import foodapp.com.foodapp.base.BaseActivity
import foodapp.com.foodapp.databinding.ActivityFoodDetailsBinding
import foodapp.com.foodapp.ui.list.FoodViewModel
import foodapp.com.foodapp.ui.splash.HeroImageViewsAdapter
import foodapp.com.foodapp.ui.views.AnimationEndListener
import foodapp.com.foodapp.ui.views.CircleTransform
import foodapp.com.foodapp.ui.views.DepthPageTransformer
import foodapp.com.foodapp.ui.views.Utils
import kotlin.math.hypot

@AndroidEntryPoint
class FoodDetailsActivity : BaseActivity<ActivityFoodDetailsBinding>() {

    private val viewModel: FoodViewModel by viewModels()

    private var flag = true

    private var pixelDensity: Float = 0.toFloat()

    companion object {
        private const val ARG_FOOD_ID = "id"

        fun newInstance(context: Context, id: Int): Intent {
            val intent = Intent(context, FoodDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putInt(ARG_FOOD_ID, id)
            intent.putExtras(bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pixelDensity = resources.displayMetrics.density

        val id = intent.getIntExtra(ARG_FOOD_ID, -1)

        viewModel.foodItemLiveData.observe(this, Observer {
            when (it) {
                is FoodResult.Error -> {
                    Toast.makeText(this@FoodDetailsActivity, "Error!",
                            Toast.LENGTH_SHORT).show()
                }
                is FoodResult.Success -> {
                    onLoadFoodItem(it.data)
                }
            }
        })

        takeIf { id != -1 }?.apply {
            viewModel.getFoodItem(id)
        }
    }

    private fun onLoadFoodItem(foodItem: FoodItem) {
        Picasso.get().load(foodItem.profileImage)
                .noFade()
                .transform(CircleTransform())
                .into(binding.profileImageView)
        ViewCompat.setTransitionName(binding.profileImageView, foodItem.profileImage)

        supportPostponeEnterTransition()

        Picasso.get().load(foodItem.heroImage).fit()
                .noFade()
                .placeholder(R.drawable.ic_svg_food)
                .centerCrop()
                .into(binding.foodImageView, object : Callback {
                    override fun onError(e: Exception?) {
                        supportStartPostponedEnterTransition()
                    }

                    override fun onSuccess() {
                        supportStartPostponedEnterTransition()
                        Handler().postDelayed({
                            if (binding.foodImageView.drawable != null) {
                                if (binding.foodImageView.drawable is BitmapDrawable) {
                                    Utils.createPaletteAsync(this@FoodDetailsActivity,
                                            (binding.foodImageView.drawable as BitmapDrawable).bitmap)
                                }
                            }
                        }, 200)
                    }
                })

        ViewCompat.setTransitionName(binding.foodImageView, foodItem.heroImage)

        binding.foodDescTextView.text = foodItem.foodDescription
        ViewCompat.setTransitionName(binding.foodDescTextView, foodItem.foodDescription)

        ViewCompat.setTransitionName(binding.votesButton, foodItem.votes.toString())

        binding.nameTextView.text = foodItem.name

        binding.dateTextView.text = foodItem.date

        binding.votesTextView.text = "${foodItem.votes}"

        binding.dishesViewPager.setPageTransformer(true, DepthPageTransformer())

        val fragmentManager = supportFragmentManager
        binding.dishesViewPager.adapter = HeroImageViewsAdapter(imagesUrls = foodItem.foodImages,
                manager = fragmentManager)

        binding.tabLayout.setupWithViewPager(binding.dishesViewPager, true)

        binding.votesButton.setOnClickListener {

            var x = binding.foodImageFrameLayout.right
            val y = binding.foodImageFrameLayout.bottom
            x -= (28 * pixelDensity + 16 * pixelDensity).toInt()

            val hypotenuse = hypot(binding.foodImageFrameLayout.width.toDouble(),
                    binding.foodImageFrameLayout.height.toDouble()).toInt()

            if (flag) {

                val parameters = binding.linearView.layoutParams as FrameLayout.LayoutParams
                parameters.height = binding.foodImageFrameLayout.height
                binding.linearView.layoutParams = parameters

                val anim = ViewAnimationUtils.createCircularReveal(binding.linearView, x, y,
                        0f, hypotenuse.toFloat())
                anim.duration = 700

                anim.addListener(object : AnimationEndListener() {
                    override fun onAnimationEnd(animator: Animator?) {
                        binding.layoutButtons.visibility = View.VISIBLE
                        binding.layoutButtons.startAnimation(AnimationUtils
                                .loadAnimation(this@FoodDetailsActivity,
                                        R.anim.appear_alpha_anim))
                    }
                })

                binding.linearView.visibility = View.VISIBLE
                anim.start()

                flag = false
            } else {
                val anim = ViewAnimationUtils.createCircularReveal(binding.linearView, x, y,
                        hypotenuse.toFloat(), 0f)
                anim.duration = 700

                anim.addListener(object : AnimationEndListener() {
                    override fun onAnimationEnd(animator: Animator?) {
                        binding.linearView.visibility = View.GONE
                    }
                })

                binding.layoutButtons.visibility = View.GONE
                binding.layoutButtons.startAnimation(AnimationUtils
                        .loadAnimation(this@FoodDetailsActivity,
                                R.anim.disappear_alpha_anim))
                anim.start()
                flag = true
            }
        }
    }

    override fun getBinding(inflater: LayoutInflater) = ActivityFoodDetailsBinding.inflate(inflater)
}