package com.kotlinproject.modernfoodrecipesapp.adapters.ingredientsadapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.elfay.myfridgerecipies.models.RecipiesByIngredientsReponseItem
import com.kotlinproject.modernfoodrecipesapp.databinding.ListItemRecipeBinding


class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.RecipesViewHolder>() {
    inner class RecipesViewHolder(private var binding: ListItemRecipeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val recipeImage = binding.recipeImage
        val recipeName = binding.recipeName
        val Likes = binding.likescount
        //val usedIngredient = binding.useding
        //val missedIngredient = binding.missedIng

        val card = binding.cardview
    }

    private val diffCallback = object : DiffUtil.ItemCallback<RecipiesByIngredientsReponseItem>() {
        override fun areContentsTheSame(
            oldItem: RecipiesByIngredientsReponseItem,
            newItem: RecipiesByIngredientsReponseItem
        ): Boolean {
            return oldItem == newItem
        }

        override fun areItemsTheSame(
            oldItem: RecipiesByIngredientsReponseItem,
            newItem: RecipiesByIngredientsReponseItem
        ): Boolean {
            return oldItem.id == newItem.id
        }


    }
    val differ = AsyncListDiffer(this, diffCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipesViewHolder {
        return RecipesViewHolder(
            ListItemRecipeBinding.inflate(LayoutInflater.from(parent.context))
        )
    }

    override fun onBindViewHolder(holder: RecipesViewHolder, position: Int) {
        val recipe = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(recipe.image).into(holder.recipeImage)

        }
        holder.recipeName.text = recipe.title
        holder.Likes.text = recipe.likes.toString()
       // holder.usedIngredient.text = recipe.usedIngredientCount.toString()
        //holder.missedIngredient.text = recipe.missedIngredientCount.toString()
        holder.card.setOnClickListener {
            onItemClickListener?.let {
                it(recipe)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((RecipiesByIngredientsReponseItem) -> Unit)? = null

    fun setOnItemClickListener(listener: (RecipiesByIngredientsReponseItem) -> Unit) {
        onItemClickListener = listener
    }
}