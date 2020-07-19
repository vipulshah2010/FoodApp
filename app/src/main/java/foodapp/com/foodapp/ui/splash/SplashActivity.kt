package foodapp.com.foodapp.ui.splash

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.BlendMode
import android.graphics.BlendModeColorFilter
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewAnimationUtils
import androidx.viewpager.widget.ViewPager
import dagger.hilt.android.AndroidEntryPoint
import foodapp.com.foodapp.R
import foodapp.com.foodapp.base.BaseActivity
import foodapp.com.foodapp.databinding.ActivitySplashBinding
import foodapp.com.foodapp.ui.list.FoodListActivity
import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    @Inject
    lateinit var mAdapter: HeroImageViewsAdapter

    @Inject
    lateinit var mPageTransformer: ViewPager.PageTransformer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.heroImagesViewPager.setPageTransformer(true, mPageTransformer)
        binding.heroImagesViewPager.adapter = mAdapter
        binding.tabLayout.setupWithViewPager(binding.heroImagesViewPager, true)

        binding.startButton.setOnClickListener {
            animateButton()
            fadeTextAndShowProgress()
            handleNext()
        }
    }

    private fun handleNext() {
        Handler().postDelayed({
            startRevealAnimation()

            binding.progressBar.animate().alpha(0f).setDuration(200).start()

            Handler().postDelayed({
                startActivity(FoodListActivity.newInstance(this@SplashActivity))
            }, 100)
        }, 2000)
    }

    private fun startRevealAnimation() {
        binding.startButton.elevation = 0f

        binding.reveal.visibility = View.VISIBLE

        val cx = binding.reveal.width
        val cy = binding.reveal.height

        val x = (getFabWidth() / 2 + binding.startButton.x).toInt()
        val y = (getFabWidth() / 2 + binding.startButton.y).toInt()

        val finalRadius = Math.max(cx, cy)

        val circularReveal = ViewAnimationUtils.createCircularReveal(binding.reveal, x, y,
                getFabWidth().toFloat(), finalRadius.toFloat())

        circularReveal.duration = 350
        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                binding.reveal.visibility = View.INVISIBLE
                binding.buttonText.visibility = View.VISIBLE
                binding.buttonText.alpha = 1f
                binding.startButton.elevation = 4f
                val layoutParams = binding.startButton.layoutParams
                layoutParams.width = (resources.displayMetrics.density * 150).toInt()
                binding.startButton.requestLayout()
            }
        })

        circularReveal.start()
    }

    private fun getFabWidth(): Int {
        return resources.getDimension(R.dimen.fab_size).toInt()
    }

    private fun fadeTextAndShowProgress() {
        binding.buttonText.animate().alpha(0f)
                .setDuration(250)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        showProgressDialog()
                    }
                })
                .start()
    }

    private fun showProgressDialog() {
        binding.progressBar.alpha = 1f
        binding.progressBar.indeterminateDrawable.colorFilter =
                BlendModeColorFilter(Color.WHITE, BlendMode.SRC_IN)
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun animateButton() {
        val anim = ValueAnimator.ofInt(binding.startButton.measuredWidth, getFabWidth())

        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = binding.startButton.layoutParams
            layoutParams.width = value
            binding.startButton.requestLayout()
        }
        anim.duration = 250
        anim.start()
    }

    override fun getBinding(inflater: LayoutInflater) = ActivitySplashBinding.inflate(inflater)
}
