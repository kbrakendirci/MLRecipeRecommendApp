package com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide

import com.kotlinproject.modernfoodrecipesapp.adapters.ingredientsadapters.ViewPagerAdapter
import com.kotlinproject.modernfoodrecipesapp.databinding.FragmentRecipeDetailsBinding
import com.elfay.myfridgerecipies.models.recipe_details.RecipeDetailsResponse
import com.kotlinproject.modernfoodrecipesapp.adapters.PagerAdapter
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.instructions.InstructionsFragment
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.overview.OverviewFragment
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.ıngredients.IngredientsFragment

import com.kotlinproject.modernfoodrecipesapp.util.Constant
import com.kotlinproject.modernfoodrecipesapp.util.Resource
import com.kotlinproject.myapplication.ui.viewmodels.RecipesViewModel
import kotlinx.android.synthetic.main.activity_details.*


class RecipeDetailsFragment : Fragment() {
   /* private val viewModel: RecipesViewModel by viewModels()
     private var _binding: FragmentRecipeDetailsBinding? = null
     private val binding get() = _binding!!
     val args: RecipeDetailsFragmentArgs by navArgs()


     override fun onCreateView(
         inflater: LayoutInflater, container: ViewGroup?,
         savedInstanceState: Bundle?
     ): View? {
         // Inflate the layout for this fragment
         _binding = FragmentRecipeDetailsBinding.inflate(inflater, container, false)
         return binding.root
     }

     override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
         super.onViewCreated(view, savedInstanceState)
         val recipeId = args.recipeId
         viewModel.getRecipeInformation(recipeId)

         val fragments = ArrayList<Fragment>()
         fragments.add(OverviewFragment())
         fragments.add(IngredientsFragment())
         fragments.add(InstructionsFragment())

         val titles = ArrayList<String>()
         titles.add("Overview")
         titles.add("Ingredients")
         titles.add("Instructions")
         val adapter = ViewPagerAdapter(

             fragments,
             titles,
             supportFragmentManager
         )

         viewPager.adapter = adapter
         tabLayout.setupWithViewPager(viewPager)



     }

*/
}