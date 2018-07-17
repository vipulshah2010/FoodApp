package foodapp.com.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import foodapp.com.data.model.FoodItem
import foodapp.com.data.store.local.FoodItemsDao

@Database(entities = [(FoodItem::class)], version = 1, exportSchema = false)
@TypeConverters(FoodItemEntityConverters::class)
abstract class FoodItemsDatabase : RoomDatabase() {

    abstract fun foodItemsDao(): FoodItemsDao
}
