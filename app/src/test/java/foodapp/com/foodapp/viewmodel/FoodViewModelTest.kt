package foodapp.com.foodapp.viewmodel

import com.google.common.truth.Truth.assertThat
import foodapp.com.data.FoodResult
import foodapp.com.domain.fooditems.FoodUsecase
import foodapp.com.foodapp.ErrorType
import foodapp.com.foodapp.test.CoroutineTest
import foodapp.com.foodapp.test.InstantTaskExecutorExtension
import foodapp.com.foodapp.test.MockFactory
import foodapp.com.foodapp.test.observeForTesting
import foodapp.com.foodapp.ui.list.FoodViewModel
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import java.io.IOException
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantTaskExecutorExtension::class)
internal class FoodViewModelTest : CoroutineTest {

    override lateinit var dispatcher: TestCoroutineDispatcher
    override lateinit var testScope: TestCoroutineScope

    private val usercase: FoodUsecase = mockk()
    private val viewModel = FoodViewModel(usercase)

    @Test
    fun `Test loading food items - success`() = dispatcher.runBlockingTest {
        every { usercase.getFoodItems(true) } returns flow {
            emit(MockFactory.createFoodItems(exception = false))
        }

        viewModel.foodItemsLiveData.observeForTesting {
            viewModel.getFoodItems(true)
            assertThat(it.values).hasSize(2)
            assertThat(it.values[0]).isSameInstanceAs(FoodResult.Loading)
            assertThat(it.values[1]).isInstanceOf(FoodResult.Success::class.java)
        }
    }

    @Test
    fun `Error is parsed correctly`() {
        assertThat(viewModel.parseError(IOException()))
                .isSameInstanceAs(ErrorType.CONNECTION_ERROR)

        assertThat(viewModel.parseError(SocketTimeoutException()))
                .isSameInstanceAs(ErrorType.TIMEOUT_ERROR)

        assertThat(viewModel.parseError(Exception()))
                .isSameInstanceAs(ErrorType.GENERIC_ERROR)
    }
}