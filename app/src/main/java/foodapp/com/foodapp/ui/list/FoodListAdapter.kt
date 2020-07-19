package foodapp.com.foodapp.ui.list

import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dagger.hilt.android.scopes.ActivityRetainedScoped
import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.R
import foodapp.com.foodapp.ui.views.CircleTransform
import kotlinx.android.synthetic.main.item_food_row.view.*

class FoodListAdapter(private val listener: (FoodItem, ImageView, ImageView, ImageView, TextView) -> Unit)
    : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    private var mHasShowedAnimation = false
    private var mHasStartedCountDown = false
    private var foodItems: ArrayList<FoodItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder =
            FoodViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_food_row, parent, false))

    override fun getItemCount(): Int = foodItems.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodItems[position], listener)

        if (!mHasShowedAnimation) {
            Handler().postDelayed({
                holder.itemView.startAnimation(AnimationUtils.loadAnimation(holder.itemView.context,
                        R.anim.slide_down_and_scale))
            }, (position * 100).toLong())

            if (!mHasStartedCountDown) {
                mHasStartedCountDown = true

                Handler().postDelayed({ mHasShowedAnimation = true }, 400)
            }
        }
    }

    fun setFoodItems(foodItems: List<FoodItem>) {
        this.foodItems.clear()
        this.foodItems.addAll(foodItems)
        notifyDataSetChanged()
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(foodItem: FoodItem, listener: (FoodItem, ImageView, ImageView, ImageView, TextView) -> Unit) = with(itemView) {

            Picasso.get().load(foodItem.profileImage).noFade().transform(CircleTransform()).into(profileImageView)
            ViewCompat.setTransitionName(profileImageView, foodItem.profileImage)

            Picasso.get().load(foodItem.heroImage).fit().placeholder(R.drawable.ic_svg_food).noFade().centerCrop().into(foodImageView)

            ViewCompat.setTransitionName(foodImageView, foodItem.heroImage)

            foodDescTextView.text = foodItem.foodDescription
            ViewCompat.setTransitionName(foodDescTextView, foodItem.foodDescription)

            ViewCompat.setTransitionName(heartImageView, foodItem.votes.toString())

            nameTextView.text = foodItem.name

            dateTextView.text = foodItem.date

            votesTextView.text = "${foodItem.votes}"

            setOnClickListener { listener(foodItem, foodImageView, profileImageView, heartImageView, foodDescTextView) }
        }
    }
}