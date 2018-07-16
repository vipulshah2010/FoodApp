package foodapp.com.foodapp.views

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.Color
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import foodapp.com.foodapp.R

object Utils {

    fun createPaletteAsync(activity: Activity, bitmap: Bitmap?) {
        bitmap?.let {
            Palette.from(it).generate { palette ->
                palette?.let {
                    val vibrant = palette.darkVibrantSwatch
                    val color = vibrant?.rgb
                            ?: ContextCompat.getColor(activity, R.color.colorPrimaryDark)
                    Utils.darkenStatusBar(activity, color = color)
                }
            }
        }
    }

    private fun darkenStatusBar(activity: Activity, @ColorRes colorRes: Int? = null, color: Int? = null) {
        colorRes?.let {
            activity.window.statusBarColor = darkenColor(ContextCompat.getColor(activity, it))
            return
        }
        color?.let {
            activity.window.statusBarColor = darkenColor(it)
            return
        }
    }

    // Code to darken the color supplied (mostly color of toolbar)
    private fun darkenColor(color: Int): Int {
        val hsv = FloatArray(3)
        Color.colorToHSV(color, hsv)
        hsv[2] *= 0.8f
        return Color.HSVToColor(hsv)
    }
}
