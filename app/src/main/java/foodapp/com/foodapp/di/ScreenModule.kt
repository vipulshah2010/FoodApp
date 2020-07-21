package foodapp.com.foodapp.di

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.android.scopes.FragmentScoped
import foodapp.com.foodapp.R
import foodapp.com.foodapp.ui.splash.SplashViewPager
import foodapp.com.foodapp.ui.views.DepthPageTransformer

@Module
@InstallIn(FragmentComponent::class)
object ScreenModule {

    @Provides
    @FragmentScoped
    fun provideAdapter(activity: Activity) =
            SplashViewPager(imagesRes = listOf(
                    R.drawable.frame1,
                    R.drawable.frame2,
                    R.drawable.frame3),
                    manager = (activity as AppCompatActivity).supportFragmentManager
            )

    @Provides
    @FragmentScoped
    fun providePageTransformer(): ViewPager.PageTransformer = DepthPageTransformer()
}
