package foodapp.com.foodapp.ui.list

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert.assertEquals
import org.junit.Test

class DashboardFragmentTest {
    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("foodapp.com.foodapp", appContext.packageName)
    }

    @Test
    fun myTest() {
        val scenario = launchFragmentInContainer {  }<DashboardFragment>()
        ScreenElements.foodListRecyclerView.isDisplayed()
    }

}
