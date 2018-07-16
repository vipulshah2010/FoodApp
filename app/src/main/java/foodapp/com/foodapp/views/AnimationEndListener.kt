package foodapp.com.foodapp.views

import android.animation.Animator

abstract class AnimationEndListener : Animator.AnimatorListener {
    override fun onAnimationStart(animator: Animator) {
        // do nothing
    }

    override fun onAnimationCancel(animator: Animator) {
        // do nothing
    }

    override fun onAnimationRepeat(animator: Animator) {
        // do nothing
    }
}
