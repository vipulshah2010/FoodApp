package foodapp.com.data.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.*

@Entity(tableName = "food_item")
@Parcelize
data class FoodItem(
        @PrimaryKey var id: String = UUID.randomUUID().toString(),
        var carbs: String = "",
        var profileImage: String = "",
        var name: String = "",
        var heroImage: String = "",
        var calories: Int = 0,
        var votes: Int = 0,
        var foodImages: ArrayList<String> = arrayListOf(),
        var foodDescription: String = "",
        var cholesterol: String = "",
        var date: String = ""
) : Parcelable