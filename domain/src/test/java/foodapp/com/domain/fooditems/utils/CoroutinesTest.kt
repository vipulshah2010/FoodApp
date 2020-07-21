package foodapp.com.domain.fooditems.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.jupiter.api.extension.*

@ExperimentalCoroutinesApi
@ExtendWith(TestCoroutineExtension::class)
interface CoroutineTest {

    var testScope: TestCoroutineScope
    var dispatcher: TestCoroutineDispatcher

    @After
    fun after() {
        dispatcher.cleanupTestCoroutines()
    }
}

@ExperimentalCoroutinesApi
class TestCoroutineExtension : TestInstancePostProcessor,
        BeforeAllCallback,
        AfterEachCallback,
        AfterAllCallback {

    private val dispatcher = TestCoroutineDispatcher()
    private val testScope = TestCoroutineScope(dispatcher)

    override fun postProcessTestInstance(testInstance: Any?, context: ExtensionContext?) {
        testInstance as CoroutineTest
        testInstance.testScope = testScope
        testInstance.dispatcher = dispatcher
    }

    override fun beforeAll(context: ExtensionContext?) {
        Dispatchers.setMain(dispatcher)
    }

    override fun afterEach(context: ExtensionContext?) {
        testScope.cleanupTestCoroutines()
    }

    override fun afterAll(context: ExtensionContext?) {
        Dispatchers.resetMain()
    }
}