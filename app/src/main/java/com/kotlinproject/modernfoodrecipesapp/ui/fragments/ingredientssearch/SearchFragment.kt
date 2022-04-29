package com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.kotlinproject.modernfoodrecipesapp.adapters.ingredientsadapters.RecipesAdapter
import com.kotlinproject.modernfoodrecipesapp.databinding.FragmentSearchBinding
import com.kotlinproject.modernfoodrecipesapp.util.Constant
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.SEACH_RECIPES_TIME_DELAY
import com.kotlinproject.modernfoodrecipesapp.util.Resource


import com.kotlinproject.myapplication.ui.viewmodels.RecipesViewModel



class SearchFragment : Fragment() {

    private val viewModel: RecipesViewModel by viewModels()

    private lateinit var recipesAdapter: RecipesAdapter
    private var _binding: FragmentSearchBinding? = null


    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()

        var job: Job? = null
        binding.search.addTextChangedListener { editable ->
            job?.cancel()
            job = MainScope().launch {
                delay(SEACH_RECIPES_TIME_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()) {
                        viewModel.getRecipes(editable.toString())
                    }
                }
            }
        }
        viewModel.searchedRecipesResponse.observe(viewLifecycleOwner, { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { recipesResponse ->
                        recipesAdapter.differ.submitList(recipesResponse)
                    }
                }
                is Resource.Error -> {
                    response.message?.let { message ->
                        Log.e(Constant.TAG, "An error occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
        recipesAdapter.setOnItemClickListener {

        }
    }


    private fun hideProgressBar() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    private fun showProgressBar() {
        binding.progressBar.visibility = View.VISIBLE
    }

    private fun setupRecyclerView() {
        recipesAdapter = RecipesAdapter()
        binding.recipiesRecyclerView.apply {
            adapter = recipesAdapter
            layoutManager = GridLayoutManager(context, 1, LinearLayoutManager.VERTICAL, false)
        }
    }
}