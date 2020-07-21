package foodapp.com.foodapp.ui.splash

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class SplashViewPager(private var imagesRes: List<Int>? = null,
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
            return SplashViewPagerFragment.newInstance(imageRes = it[position])
        }
        return SplashViewPagerFragment.newInstance(imageUrl = imagesUrls!![position])
    }
}