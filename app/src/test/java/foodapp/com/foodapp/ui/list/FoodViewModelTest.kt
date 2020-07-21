package foodapp.com.foodapp.ui.list

import com.google.common.truth.Truth.assertThat
import foodapp.com.data.FoodResult
import foodapp.com.data.test.mock.MockFactory
import foodapp.com.domain.fooditems.FoodUsecase
import foodapp.com.foodapp.ErrorType
import foodapp.com.foodapp.ui.list.utils.CoroutineTest
import foodapp.com.foodapp.ui.list.utils.InstantTaskExecutorExtension
import foodapp.com.foodapp.ui.list.utils.observeForTesting
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
import org.mockito.ArgumentMatchers.anyInt
import java.io.IOException
import java.net.SocketTimeoutException

@ExperimentalCoroutinesApi
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(InstantTaskExecutorExtension::class)
internal class FoodViewModelTest : CoroutineTest {

    override lateinit var dispatcher: TestCoroutineDispatcher
    override lateinit var testScope: TestCoroutineScope

    private val usercase: FoodUsecase = mockk()

    @Test
    fun `Test loading food items - success`() = dispatcher.runBlockingTest {
        every { usercase.getFoodItems(true) } returns flow {
            emit(MockFactory.generateFoodItems(exception = false))
        }

        val viewModel = FoodViewModel(usercase)

        viewModel.foodItemsLiveData.observeForTesting {
            viewModel.getFoodItems(true)
            assertThat(it.values).hasSize(2)
            assertThat(it.values[0]).isSameInstanceAs(FoodResult.Loading)
            assertThat(it.values[1]).isInstanceOf(FoodResult.Success::class.java)
        }
    }

    @Test
    fun `Test loading food items - Failure`() = dispatcher.runBlockingTest {
        every { usercase.getFoodItems(true) } returns flow {
            emit(MockFactory.generateFoodItems(exception = true))
        }

        val viewModel = FoodViewModel(usercase)

        viewModel.foodItemsLiveData.observeForTesting {
            viewModel.getFoodItems(true)
            assertThat(it.values).hasSize(2)
            assertThat(it.values[0]).isSameInstanceAs(FoodResult.Loading)
            assertThat(it.values[1]).isInstanceOf(FoodResult.Error::class.java)
        }
    }

    @Test
    fun `Test loading single food item - success`() = dispatcher.runBlockingTest {
        every { usercase.getFoodItem(any()) } returns flow {
            emit(MockFactory.generateFoodItem(exception = false))
        }

        val viewModel = FoodViewModel(usercase)

        viewModel.foodItemLiveData.observeForTesting {
            viewModel.getFoodItem(anyInt())
            assertThat(it.values).hasSize(2)
            assertThat(it.values[0]).isSameInstanceAs(FoodResult.Loading)
            assertThat(it.values[1]).isInstanceOf(FoodResult.Success::class.java)
        }
    }

    @Test
    fun `Test loading single food item - Failure`() = dispatcher.runBlockingTest {
        every { usercase.getFoodItem(any()) } returns flow {
            emit(MockFactory.generateFoodItem(exception = true))
        }

        val viewModel = FoodViewModel(usercase)

        viewModel.foodItemLiveData.observeForTesting {
            viewModel.getFoodItem(anyInt())
            assertThat(it.values).hasSize(2)
            assertThat(it.values[0]).isSameInstanceAs(FoodResult.Loading)
            assertThat(it.values[1]).isInstanceOf(FoodResult.Error::class.java)
        }
    }

    @Test
    fun `Error is parsed correctly`() {

        val viewModel = FoodViewModel(usercase)

        assertThat(viewModel.parseError(IOException()))
                .isSameInstanceAs(ErrorType.CONNECTION_ERROR)

        assertThat(viewModel.parseError(SocketTimeoutException()))
                .isSameInstanceAs(ErrorType.TIMEOUT_ERROR)

        assertThat(viewModel.parseError(Exception()))
                .isSameInstanceAs(ErrorType.GENERIC_ERROR)
    }
}