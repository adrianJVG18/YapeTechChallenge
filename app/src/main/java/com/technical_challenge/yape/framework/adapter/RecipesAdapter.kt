package com.technical_challenge.yape.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.technical_challenge.yape.databinding.ItemRecipeBinding

class RecipesAdapter (
    private val recipes: List<RecipeItem>
): RecyclerView.Adapter<RecipeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

}

class RecipeViewHolder(private val binding: ItemRecipeBinding)
    : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeItem) {
            with(binding) {
                itemTitleTextView.text = item.name
                if (item.icon != null) {
                    // TODO icon in the future could be a String for real Urls
                    itemIconImageView.setImageResource(item.icon)
                }
            }
        }
}

data class RecipeItem(
    val name: String,
    val icon: Int?
)