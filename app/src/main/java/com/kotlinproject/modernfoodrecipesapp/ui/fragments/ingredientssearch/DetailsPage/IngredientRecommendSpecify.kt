package com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.DetailsPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.elfay.myfridgerecipies.models.recipe_details.RecipeDetailsResponse
import com.kotlinproject.modernfoodrecipesapp.adapters.ingredientsadapters.IngredientsRecommendSpecify
import com.kotlinproject.modernfoodrecipesapp.databinding.FragmentIngredientRecommendSpecifyBinding
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.ActivityIngredientDetailsArgs
import com.kotlinproject.modernfoodrecipesapp.util.Resource
import com.kotlinproject.myapplication.ui.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.fragment_ingredients.view.*


class IngredientRecommendSpecify(recipeId: Int) : Fragment() {
    private val mAdapter: IngredientsRecommendSpecify by lazy { IngredientsRecommendSpecify() }

    private  val  deneme = recipeId.toInt()

    private val args by navArgs<ActivityIngredientDetailsArgs>()
    private val viewModel: RecipesViewModel by viewModels()
    private var _binding: FragmentIngredientRecommendSpecifyBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        print(deneme)
        // Inflate the layout for this fragment
        _binding = FragmentIngredientRecommendSpecifyBinding.inflate(inflater, container, false)
        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //al recipeId = args.recipeId

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
        view?.let { setupRecyclerView(it) }
       recipeDetails.extendedIngredients?.let { mAdapter.setData(it) }
        var steps : String ="empty comeback later"

    }

    private fun setupRecyclerView(view: View) {
        view.ingredients_recyclerview.adapter = mAdapter
        view.ingredients_recyclerview.layoutManager = LinearLayoutManager(requireContext())
    }

}