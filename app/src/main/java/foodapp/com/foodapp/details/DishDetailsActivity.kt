package foodapp.com.foodapp.details

import android.content.Intent
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.fragment.app.FragmentActivity
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import foodapp.com.foodapp.R
import foodapp.com.foodapp.views.Utils
import kotlinx.android.synthetic.main.activity_dish_details.*
import java.lang.Exception

class DishDetailsActivity : AppCompatActivity() {

    companion object {
        private const val ARG_IMAGE_URL = "image_url"

        fun newInstance(activity: FragmentActivity, imageUrl: String): Intent {
            val intent = Intent(activity, DishDetailsActivity::class.java)
            val bundle = Bundle()
            bundle.putString(ARG_IMAGE_URL, imageUrl)
            intent.putExtras(bundle)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_details)
        val imageUrl = intent.getStringExtra(ARG_IMAGE_URL)
        ViewCompat.setTransitionName(foodImageView, imageUrl)

        Picasso.get().load(imageUrl).fit().centerCrop().into(foodImageView, object : Callback {
            override fun onError(e: Exception?) {
                // do nothing
            }

            override fun onSuccess() {
                Handler().postDelayed({
                    if (foodImageView.drawable != null) {
                        if (foodImageView.drawable is BitmapDrawable) {
                            Utils.createPaletteAsync(this@DishDetailsActivity, (foodImageView.drawable as BitmapDrawable).bitmap)
                        }
                    }
                }, 200)
            }
        })
    }
}