package foodapp.com.data.repository

import app.cash.turbine.test
import com.google.common.truth.Truth
import foodapp.com.data.FoodDatabase
import foodapp.com.data.FoodResult
import foodapp.com.data.model.FoodItem
import foodapp.com.data.network.RestApi
import foodapp.com.data.repository.utils.CoroutineTest
import foodapp.com.data.repository.utils.TestDispatcherProviderImpl
import foodapp.com.data.store.local.FoodDao
import foodapp.com.data.store.local.LocalDataStore
import foodapp.com.data.store.local.LocalDataStoreImpl
import foodapp.com.data.store.remote.CloudCloudDataStoreImpl
import foodapp.com.data.store.remote.CloudDataStore
import foodapp.com.data.test.mock.MockData
import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.time.ExperimentalTime

@ExperimentalCoroutinesApi
@ExperimentalTime
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockKExtension::class)
internal class FoodRepositoryTest : CoroutineTest {

    override lateinit var dispatcher: TestCoroutineDispatcher
    override lateinit var testScope: TestCoroutineScope

    private lateinit var localDataStore: LocalDataStore
    private lateinit var remoteCloudDataStore: CloudDataStore

    @MockK
    lateinit var restApi: RestApi

    @MockK
    lateinit var database: FoodDatabase

    @MockK(relaxed = true)
    lateinit var foodDao: FoodDao

    private lateinit var repository: FoodRepository

    @BeforeAll
    fun setup() {
        remoteCloudDataStore = CloudCloudDataStoreImpl(restApi)

        every { database.foodItemsDao() } returns foodDao
        localDataStore = LocalDataStoreImpl(database)

        repository = FoodRepositoryImpl(localDataStore, remoteCloudDataStore, TestDispatcherProviderImpl(dispatcher))
    }

    @Test
    fun `force fetching foodItems makes network and db call`() = dispatcher.runBlockingTest {
        val foodItemsSlot = slot<List<FoodItem>>()
        coEvery { restApi.getFoodItems() } returns MockData.foodItems
        every { foodDao.insertAll(capture(foodItemsSlot)) } just runs

        repository.getFoodItems(true).test {
            Truth.assertThat(expectItem()).isInstanceOf(FoodResult.Success::class.java)
            Truth.assertThat(foodItemsSlot.captured.size).isEqualTo(3)
            expectComplete()
        }
    }

    @Test
    fun `passing false flag avoids network call if cached food items found`() = dispatcher.runBlockingTest {
        val foodItemsSlot = slot<List<FoodItem>>()
        coEvery { foodDao.allFoodItems } returns MockData.foodItems.fooditems
        every { foodDao.insertAll(capture(foodItemsSlot)) } just runs

        repository.getFoodItems(false).test {
            val item = expectItem()
            Truth.assertThat(item).isInstanceOf(FoodResult.Success::class.java)
            Truth.assertThat((item as FoodResult.Success).data).hasSize(3)
            expectComplete()
        }
    }

    @Test
    fun `passing false flag still makes network call if cached food items not found`() = dispatcher.runBlockingTest {
        val foodItemsSlot = slot<List<FoodItem>>()
        coEvery { restApi.getFoodItems() } returns MockData.foodItems
        coEvery { foodDao.allFoodItems } returns MockData.emptyFoodIItems.fooditems
        every { foodDao.insertAll(capture(foodItemsSlot)) } just runs

        repository.getFoodItems(false).test {
            Truth.assertThat(expectItem()).isInstanceOf(FoodResult.Success::class.java)
            Truth.assertThat(foodItemsSlot.captured.size).isEqualTo(3)
            expectComplete()
        }
    }

    @Test
    fun `fetching single cached food item makes db query and returns correct result`() = dispatcher.runBlockingTest {
        val foodItemIdSlot = slot<Int>()
        every { foodDao.getFoodItem(capture(foodItemIdSlot)) } returns MockData.foodItem

        repository.getFoodItem(1).test {
            Truth.assertThat(expectItem()).isInstanceOf(FoodResult.Success::class.java)
            Truth.assertThat(foodItemIdSlot.captured).isEqualTo(1)
            expectComplete()
        }
    }
}