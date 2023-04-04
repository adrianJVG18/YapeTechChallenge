package com.technical_challenge.yape.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technical_challenge.yape.databinding.ItemRecipeBinding

class RecipesAdapter (
    private val recipes: List<RecipeItem>
): RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position])
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeItem) {
            with(binding) {
                itemTitleTextView.text = item.name
                if (item.icon != null && item.icon.isNotBlank()) {
                    Picasso.get()
                        .load(item.icon)
                        .into(binding.itemIconImageView)
                }
            }
        }
    }
}
