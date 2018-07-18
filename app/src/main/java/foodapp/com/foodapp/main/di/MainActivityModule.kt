package foodapp.com.foodapp.main.di

import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import dagger.Module
import dagger.Provides
import foodapp.com.foodapp.R
import foodapp.com.foodapp.main.adapter.HeroImageViewsAdapter
import foodapp.com.foodapp.main.ui.MainActivity
import foodapp.com.foodapp.views.DepthPageTransformer

@Module
class MainActivityModule {

    @Provides
    fun activity(activity: MainActivity): AppCompatActivity = activity

    @Provides
    fun provideAdapter(activity: AppCompatActivity) =
            HeroImageViewsAdapter(imagesRes = arrayListOf(R.drawable.frame1, R.drawable.frame2, R.drawable.frame3),
                    manager = activity.supportFragmentManager
            )

    @Provides
    fun providePageTransformer(): ViewPager.PageTransformer {
        return DepthPageTransformer()
    }
}
