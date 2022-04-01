package com.kotlinproject.modernfoodrecipesapp.adapters
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.kotlinproject.modernfoodrecipesapp.databinding.RecipesRowLayoutBinding
import com.kotlinproject.modernfoodrecipesapp.model.FoodRecipe
import com.kotlinproject.modernfoodrecipesapp.model.Result
import com.kotlinproject.modernfoodrecipesapp.util.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {

    private var recipes = emptyList<Result>()

    class MyViewHolder(private val binding: RecipesRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RecipesRowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipes[position]
        holder.bind(currentRecipe)
    }

    override fun getItemCount(): Int {
        return recipes.size
    }

    fun setData(newData: FoodRecipe){
        val recipeDiffUtil = newData.results?.let { RecipesDiffUtil(recipes, it) }
        val diffUtilResult = recipeDiffUtil?.let { DiffUtil.calculateDiff(it) }
        recipes = newData.results!!
        diffUtilResult?.dispatchUpdatesTo(this)
    }
}