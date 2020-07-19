package foodapp.com.foodapp.ui.splash

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.ViewCompat
import com.squareup.picasso.Picasso
import foodapp.com.foodapp.base.BaseFragment
import foodapp.com.foodapp.databinding.FragmentHeroImageBinding

class SplashFragment : BaseFragment<FragmentHeroImageBinding>() {

    companion object {
        const val ARG_IMAGE_RES = "image_res"
        const val ARG_IMAGE_URL = "image_url"

        fun newInstance(@DrawableRes imageRes: Int = -1, imageUrl: String = ""): SplashFragment {
            val fragment = SplashFragment()
            val bundle = Bundle()
            if (imageRes != -1) {
                bundle.putInt(ARG_IMAGE_RES, imageRes)
            }
            if (!TextUtils.isEmpty(imageUrl)) {
                bundle.putString(ARG_IMAGE_URL, imageUrl)
            }
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (requireArguments().containsKey(ARG_IMAGE_RES)) {
            val imageRes = requireArguments().getInt(ARG_IMAGE_RES)
            Picasso.get().load(imageRes).fit().noFade().centerCrop().into(binding.heroImageView)
            return
        }

        if (requireArguments().containsKey(ARG_IMAGE_URL)) {
            val imageUrl = requireArguments().getString(ARG_IMAGE_URL)
            ViewCompat.setTransitionName(binding.heroImageView, imageUrl)
            Picasso.get().load(imageUrl).fit().noFade().centerCrop().into(binding.heroImageView)

            view.setOnClickListener {
                activity?.let {
                    val p1 = androidx.core.util.Pair.create<View, String>(binding.heroImageView, imageUrl)
                    val options = ActivityOptionsCompat.makeSceneTransitionAnimation(it, p1)
                    //startActivity(DishDetailsActivity.newInstance(it, imageUrl), options.toBundle())
                }
            }
            return
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?,
                            savedInstanceState: Bundle?): FragmentHeroImageBinding {
        return FragmentHeroImageBinding.inflate(inflater, container, false)
    }
}