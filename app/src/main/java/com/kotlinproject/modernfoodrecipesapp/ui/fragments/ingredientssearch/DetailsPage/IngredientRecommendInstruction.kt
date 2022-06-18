package com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.DetailsPage

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment

import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.elfay.myfridgerecipies.models.recipe_details.RecipeDetailsResponse
import com.kotlinproject.modernfoodrecipesapp.databinding.FragmentIngredientRecommendInstructionBinding
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.ActivityIngredientDetailsArgs
import com.kotlinproject.modernfoodrecipesapp.util.Resource
import com.kotlinproject.myapplication.ui.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.fragment_ingredient_recommend_instruction.*
import kotlinx.android.synthetic.main.fragment_instructions.view.*

class IngredientRecommendInstruction(recipeId: Int) : Fragment() {
    private  val  deneme = recipeId.toInt()

    private val args by navArgs<ActivityIngredientDetailsArgs>()
    private val viewModel: RecipesViewModel by viewModels()
    private var _binding: FragmentIngredientRecommendInstructionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        print(deneme)
        // Inflate the layout for this fragment
        _binding = FragmentIngredientRecommendInstructionBinding.inflate(inflater, container, false)
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
        instructions_webView.webViewClient = object : WebViewClient() {}
        val websiteUrl: String = recipeDetails.sourceUrl.toString()
        this.view?.instructions_webView?.loadUrl(websiteUrl)
    }




}