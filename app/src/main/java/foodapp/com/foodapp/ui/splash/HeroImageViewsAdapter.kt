package foodapp.com.foodapp.ui.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import dagger.hilt.android.scopes.ActivityScoped

@ActivityScoped
class HeroImageViewsAdapter(private var imagesRes: ArrayList<Int>? = null,
                            private var imagesUrls: ArrayList<String>? = null,
                            manager: FragmentManager) : FragmentStatePagerAdapter(manager,
        BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        imagesRes?.let {
            return it.size
        }
        imagesUrls?.let {
            return it.size
        }
        return -1
    }

    override fun getItem(position: Int): Fragment {
        imagesRes?.let {
            return SplashFragment.newInstance(imageRes = it[position])
        }
        return SplashFragment.newInstance(imageUrl = imagesUrls!![position])
    }
}