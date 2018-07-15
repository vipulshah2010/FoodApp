package foodapp.com.foodapp.dashboard.ui

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewAnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
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

        heroImagesViewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                // do nothing
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                // do nothing
            }

            override fun onPageSelected(position: Int) {

            }
        })

        tabLayout.setupWithViewPager(heroImagesViewPager, true)

        startButton.setOnClickListener {

            startAnimation()
        }
    }

    private fun startAnimation() {
        animateButtonWidth();

        fadeOutTextAndShowProgressDialog()

        nextAction()
    }

    private fun nextAction() {
        Handler().postDelayed({
            revealButton()

            fadeOutProgressDialog()

            delayedStartNextActivity()
        }, 2000)
    }

    private fun revealButton() {
        startButton.elevation = 0f

        reveal.visibility = View.VISIBLE

        val cx = reveal.width
        val cy = reveal.height

        val x = (getFabWidth() / 2 + startButton.x).toInt()
        val y = (getFabWidth() / 2 + startButton.y).toInt()

        val finalRadius = Math.max(cx, cy)

        val circularReveal = ViewAnimationUtils.createCircularReveal(reveal, x, y, getFabWidth().toFloat(), finalRadius.toFloat())

        circularReveal.duration = 350
        circularReveal.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                reset(animation)
            }

            private fun reset(animation: Animator) {
                super.onAnimationEnd(animation)
                reveal.visibility = View.INVISIBLE
                buttonText.visibility = View.VISIBLE
                buttonText.alpha = 1f
                startButton.elevation = 4f
                val layoutParams = startButton.layoutParams
                layoutParams.width = (resources.displayMetrics.density * 150).toInt()
                startButton.requestLayout()
            }
        })

        circularReveal.start()
    }

    private fun fadeOutProgressDialog() {
        progressBar.animate().alpha(0f).setDuration(200).start()
    }

    private fun delayedStartNextActivity() {
        Handler().postDelayed({ startActivity(Intent(this@MainActivity, FoodListActivity::class.java)) }, 100)
    }

    private fun getFabWidth(): Int {
        return resources.getDimension(R.dimen.fab_size).toInt()
    }

    private fun fadeOutTextAndShowProgressDialog() {
        buttonText.animate().alpha(0f)
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
        progressBar.alpha = 1f
        progressBar
                .indeterminateDrawable
                .setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN)
        progressBar.visibility = View.VISIBLE
    }

    private fun animateButtonWidth() {
        val anim = ValueAnimator.ofInt(startButton.measuredWidth, getFabWidth())

        anim.addUpdateListener { valueAnimator ->
            val value = valueAnimator.animatedValue as Int
            val layoutParams = startButton.layoutParams
            layoutParams.width = value
            startButton.requestLayout()
        }
        anim.duration = 250
        anim.start()
    }
}
