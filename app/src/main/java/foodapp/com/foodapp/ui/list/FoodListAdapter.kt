package foodapp.com.foodapp.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load
import coil.transform.CircleCropTransformation
import foodapp.com.data.model.FoodItem
import foodapp.com.foodapp.R
import foodapp.com.foodapp.databinding.RowFoodItemBinding

class FoodListAdapter(private val listener: (FoodItem, ImageView, ImageView) -> Unit)
    : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    private var foodItems: ArrayList<FoodItem> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val binding = RowFoodItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FoodViewHolder(binding)
    }

    override fun getItemCount(): Int = foodItems.size

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bind(foodItems[position], listener)
    }

    fun setFoodItems(foodItems: List<FoodItem>) {
        this.foodItems.clear()
        this.foodItems.addAll(foodItems)
        notifyDataSetChanged()
    }

    class FoodViewHolder(private val binding: RowFoodItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(foodItem: FoodItem, listener: (FoodItem, ImageView, ImageView) -> Unit) = with(itemView) {

            binding.profileImageView.load(foodItem.profileImage, builder = {
                transformations(CircleCropTransformation())
            })

            binding.foodImageView.load(foodItem.heroImage, builder = {
                placeholder(R.drawable.ic_svg_food)
            })

            binding.foodDescTextView.text = foodItem.foodDescription
            binding.nameTextView.text = foodItem.name
            binding.dateTextView.text = foodItem.date
            binding.votesTextView.text = "${foodItem.votes}"

            binding.foodImageView.transitionName = foodItem.heroImage
            binding.heartImageView.transitionName = foodItem.votes.toString()

            setOnClickListener {
                listener(foodItem, binding.foodImageView, binding.heartImageView)
            }
        }
    }
}