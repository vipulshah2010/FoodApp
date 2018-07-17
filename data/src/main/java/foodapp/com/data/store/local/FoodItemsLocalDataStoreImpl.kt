package foodapp.com.data.store.local

import foodapp.com.data.FoodItemsDatabase
import foodapp.com.data.model.FoodItem
import io.reactivex.Single
import timber.log.Timber
import javax.inject.Inject

class FoodItemsLocalDataStoreImpl @Inject constructor(private val mFoodItemsDatabase: FoodItemsDatabase) : FoodItemsLocalDataStore {

    override fun getFoodItems(): Single<List<FoodItem>> {
        return Single.create {
            val foodItems = mFoodItemsDatabase.foodItemsDao().allFoodItems
            if (foodItems.isEmpty()) {
                it.onError(Exception())
            } else {
                it.onSuccess(foodItems)
            }
        }
    }

    override fun addFoodItems(foodItems: List<FoodItem>): Single<List<FoodItem>> {
        mFoodItemsDatabase.beginTransaction()
        try {
            if (foodItems.isNotEmpty()) {
                foodItems.forEach(object : (FoodItem) -> Unit {
                    override fun invoke(foodItem: FoodItem) {
                        mFoodItemsDatabase.foodItemsDao().insertFoodItem(foodItem)
                    }
                })
            }

            mFoodItemsDatabase.setTransactionSuccessful()
        } catch (e: Exception) {
            Timber.e(e)
        } finally {
            mFoodItemsDatabase.endTransaction()
        }

        return Single.just(mFoodItemsDatabase.foodItemsDao().allFoodItems)
    }

    override fun getFoodItem(id: String): Single<FoodItem> {
        return Single.create { emitter ->
            val foodItem = mFoodItemsDatabase.foodItemsDao().getFoodItem(id)
            emitter.onSuccess(foodItem)
        }
    }
}
