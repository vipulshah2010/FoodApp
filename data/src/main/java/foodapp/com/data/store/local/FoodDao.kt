package foodapp.com.data.store.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import foodapp.com.data.model.FoodItem

@Dao
interface FoodDao {

    @get:Query("SELECT * FROM food_item")
    val allFoodItems: List<FoodItem>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(foodItem: FoodItem)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(foodItems: List<FoodItem>)

    @Query("SELECT * FROM food_item WHERE id = :id")
    fun getFoodItem(id: String): FoodItem
}
