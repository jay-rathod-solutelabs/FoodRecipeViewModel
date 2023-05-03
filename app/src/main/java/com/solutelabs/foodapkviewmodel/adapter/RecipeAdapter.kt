package com.solutelabs.foodapkviewmodel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.solutelabs.foodapkviewmodel.databinding.ItemLayoutBinding
import com.solutelabs.foodrecipeapp.model.Recipe
import com.solutelabs.foodrecipeapp.utils.ImageLoaderUtil

class RecipeAdapter(private var recipes: List<Recipe>) :
    RecyclerView.Adapter<RecipeAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(recipe: Recipe) {
            binding.textviewRecipeTitle.text = recipe.title
            binding.textviewRecipeRating.text = recipe.rating.toString()

            ImageLoaderUtil.load(
                binding.root.context,
                recipe.featured_image,
                binding.imageviewRecipeImage
            )
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val recipe = recipes[position]
        holder.bind(recipe)
    }

    override fun getItemCount(): Int = recipes.size

    fun updateList(newList: List<Recipe>) {
        recipes = newList
        notifyDataSetChanged()
    }
}
