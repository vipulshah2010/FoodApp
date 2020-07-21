package foodapp.com.foodapp.ui.list

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.ViewInteraction
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import foodapp.com.foodapp.R

/**
 * Dashboard Screen elements and helper methods for screen verification
 */
internal object ScreenElements {
    val foodListRecyclerView: ViewInteraction = onView(withId(R.id.foodListRecyclerView))
    val errorView: ViewInteraction = onView(withId(R.id.errorView))
}

fun ViewInteraction.isDisplayed(): ViewInteraction = this.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))