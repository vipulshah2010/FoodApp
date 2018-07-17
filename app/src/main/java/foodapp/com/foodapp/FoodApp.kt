package foodapp.com.foodapp

import android.app.Activity
import android.app.Application
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import foodapp.com.foodapp.di.DaggerAppComponent
import timber.log.Timber
import javax.inject.Inject

class FoodApp : Application(), HasActivityInjector {

    @Inject
    lateinit var mDispatchingAndroidActivityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        DaggerAppComponent.builder().create(this).inject(this)

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return mDispatchingAndroidActivityInjector
    }
}