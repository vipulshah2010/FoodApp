package foodapp.com.foodapp.details.di

import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import dagger.Module
import dagger.Provides
import foodapp.com.foodapp.R
import foodapp.com.foodapp.details.ui.FoodDetailsActivity
import javax.inject.Named

@Module
class FoodDetailsActivityModule {

    @Provides
    fun activity(activity: FoodDetailsActivity): AppCompatActivity {
        return activity
    }

    @Provides
    @Named("appearAlphaAnim")
    fun provideAppearAlphaAnimation(activity: AppCompatActivity): Animation {
        return AnimationUtils.loadAnimation(activity, R.anim.appear_alpha_anim)
    }

    @Provides
    @Named("disappearAlphaAnim")
    fun provideDisappearAlphaAnimation(activity: AppCompatActivity): Animation {
        return AnimationUtils.loadAnimation(activity, R.anim.disappear_alpha_anim)
    }
}
