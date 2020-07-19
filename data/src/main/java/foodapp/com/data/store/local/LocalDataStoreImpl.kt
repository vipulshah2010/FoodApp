package foodapp.com.data.store.local

import foodapp.com.data.FoodDatabase
import foodapp.com.data.model.FoodItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LocalDataStoreImpl @Inject constructor(private val mFoodDatabase: FoodDatabase) :
        LocalDataStore {

    override fun getFoodItems(): Flow<List<FoodItem>> = flow {
        emit(mFoodDatabase.foodItemsDao().allFoodItems)
    }

    override fun addFoodItems(foodItems: List<FoodItem>) = flow {
        mFoodDatabase.foodItemsDao().insertAll(foodItems)
        emit(mFoodDatabase.foodItemsDao().allFoodItems)
    }

    override fun getFoodItem(id: Int) = flow {
        emit(mFoodDatabase.foodItemsDao().getFoodItem(id))
    }
}
