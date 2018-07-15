package foodapp.com.foodapp.foods.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import foodapp.com.foodapp.R
import foodapp.com.foodapp.model.FoodItem
import foodapp.com.foodapp.views.CircleTransform
import kotlinx.android.synthetic.main.item_food_row.view.*

class FoodListAdapter(private val foodItems: ArrayList<FoodItem>, private val listener: (FoodItem, ImageView, ImageView, ImageView, TextView) -> Unit)
    : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
            FoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_food_row, parent, false))

    override fun getItemCount(): Int = foodItems.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) = holder.bind(foodItems[position], listener)

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(foodItem: FoodItem, listener: (FoodItem, ImageView, ImageView, ImageView, TextView) -> Unit) = with(itemView) {

            Picasso.get().load(foodItem.profileImage).transform(CircleTransform()).into(profileImageView)
            ViewCompat.setTransitionName(profileImageView, foodItem.profileImage)

            Picasso.get().load(foodItem.heroImage).fit().centerCrop().into(foodImageView)
            ViewCompat.setTransitionName(foodImageView, foodItem.heroImage)

            foodDescTextView.text = foodItem.foodDescription
            ViewCompat.setTransitionName(foodDescTextView, foodItem.foodDescription)

            ViewCompat.setTransitionName(heartImageView, foodItem.votes.toString())

            nameTextView.text = foodItem.name

            dateTextView.text = foodItem.date

            votesTextView.text = "${foodItem.votes}"

            setOnClickListener { listener(foodItem, foodImageView, profileImageView, heartImageView, foodDescTextView) }

            // Setup transition names
        }
    }
}