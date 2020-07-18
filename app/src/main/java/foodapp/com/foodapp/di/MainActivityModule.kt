package foodapp.com.foodapp.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import foodapp.com.foodapp.R
import foodapp.com.foodapp.ui.splash.HeroImageViewsAdapter
import foodapp.com.foodapp.ui.views.DepthPageTransformer

@Module
@InstallIn(ActivityComponent::class)
object MainActivityModule {

    @Provides
    @ActivityScoped
    fun provideAdapter(activity: Activity) =
            HeroImageViewsAdapter(imagesRes = arrayListOf(R.drawable.frame1, R.drawable.frame2, R.drawable.frame3),
                    manager = (activity as AppCompatActivity).supportFragmentManager
            )

    @Provides
    @ActivityScoped
    fun providePageTransformer(): ViewPager.PageTransformer {
        return DepthPageTransformer()
    }
}
