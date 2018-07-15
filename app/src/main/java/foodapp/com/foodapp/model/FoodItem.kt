package foodapp.com.foodapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FoodItem(
        val profileImage: String,
        val name: String,
        val date: String,
        val votes: Int,
        val heroImage: String,
        val foodDescription: String,
        val foodImages: ArrayList<String>
) : Parcelable