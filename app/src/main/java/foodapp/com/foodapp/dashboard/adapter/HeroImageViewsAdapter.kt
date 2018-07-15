package foodapp.com.foodapp.dashboard.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import foodapp.com.foodapp.dashboard.ui.HeroImageFragment

class HeroImageViewsAdapter(private var imagesRes: ArrayList<Int>? = null, private var imagesUrls: ArrayList<String>? = null, manager: FragmentManager) : FragmentStatePagerAdapter(manager) {

    override fun getCount(): Int {
        imagesRes?.let {
            return it.size
        }
        imagesUrls?.let {
            return it.size
        }
        return -1
    }

    override fun getItem(position: Int): Fragment? {
        imagesRes?.let {
            return HeroImageFragment.newInstance(imageRes = it[position])
        }
        imagesUrls?.let {
            return HeroImageFragment.newInstance(imageUrl = it[position])
        }
        return null
    }
}