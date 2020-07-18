package foodapp.com.foodapp.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import foodapp.com.foodapp.R
import foodapp.com.foodapp.ui.splash.HeroImageViewsAdapter
import foodapp.com.foodapp.ui.views.DepthPageTransformer

@Module
@InstallIn(ActivityComponent::class)
abstract class MainActivityModule {

    @Provides
    fun provideAdapter(activity: Activity) =
            HeroImageViewsAdapter(imagesRes = arrayListOf(R.drawable.frame1, R.drawable.frame2, R.drawable.frame3),
                    manager = (activity as AppCompatActivity).supportFragmentManager
            )

    @Provides
    fun providePageTransformer(): ViewPager.PageTransformer {
        return DepthPageTransformer()
    }
}
