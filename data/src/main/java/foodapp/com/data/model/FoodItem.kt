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
        val carbs: String,
        val profileImage: String,
        val name: String,
        val heroImage: String,
        val calories: Int,
        val votes: Int,
        val foodImages: ArrayList<String> = arrayListOf(),
        val foodDescription: String,
        val cholesterol: String,
        var date: String
) : Parcelable