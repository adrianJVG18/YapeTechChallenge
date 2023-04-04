package com.technical_challenge.yape.framework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.technical_challenge.yape.databinding.ItemRecipeBinding

class RecipesAdapter (
    private val recipes: List<RecipeItem>,
    private val onItemClickListener: OnItemClickListener
): RecyclerView.Adapter<RecipesAdapter.RecipeViewHolder>(){

    interface OnItemClickListener {
        fun onItemClick(item: RecipeItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
        val binding = ItemRecipeBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecipeViewHolder(binding)
    }

    override fun getItemCount(): Int = recipes.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) {
        holder.bind(recipes[position], onItemClickListener)
    }

    inner class RecipeViewHolder(private val binding: ItemRecipeBinding)
        : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: RecipeItem, clickAction: RecipesAdapter.OnItemClickListener) {
            with(binding) {
                itemTitleTextView.text = item.name
                if (item.icon != null && item.icon.isNotBlank()) {
                    Picasso.get()
                        .load(item.icon)
                        .into(binding.itemIconImageView)
                }
                root.setOnClickListener {
                    clickAction.onItemClick(item)
                }
            }
        }
    }
}
