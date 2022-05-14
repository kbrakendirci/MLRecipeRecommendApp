package com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.DetailsPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.elfay.myfridgerecipies.models.recipe_details.RecipeDetailsResponse
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.databinding.FragmentIngredientRecommentOverviewBinding
import com.kotlinproject.modernfoodrecipesapp.util.Resource
import com.kotlinproject.myapplication.ui.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.fragment_overview.view.*
import org.jsoup.Jsoup


class IngredientRecommentOverview(recipeId: Int) : Fragment() {
    private  val  deneme = recipeId.toInt()
    private val viewModel: RecipesViewModel by viewModels()
    private var _binding: FragmentIngredientRecommentOverviewBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentIngredientRecommentOverviewBinding.inflate(inflater, container, false)
        return binding.root
        // Inflate the layout for this fragment
         }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getRecipeInformation(deneme)

        viewModel.recipeInformationResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {

                    response.data?.let { recipesResponse ->
                        bind(recipesResponse)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                    }
                }
                is Resource.Loading -> {

                }
            }
        })


    }
    private fun bind(recipeDetails : RecipeDetailsResponse){
        Glide.with(this).load(recipeDetails.image).into(binding.recipeImage)
        binding.recipeName.text = recipeDetails.title
        var steps : String ="empty comeback later"

        val summary = recipeDetails.summary
        binding.summaryTextView.text = Jsoup.parse(summary).text()

    }

}