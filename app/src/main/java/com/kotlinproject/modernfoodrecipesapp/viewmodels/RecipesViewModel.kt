package com.kotlinproject.modernfoodrecipesapp.viewmodels
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.API_KEY
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_API_KEY
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_DIET
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_FILL_INGREDIENTS
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_NUMBER
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_TYPE



class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries():HashMap<String, String> {
        val queries = HashMap<String, String>()

        queries[QUERY_NUMBER] = "50"
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = "snack"
        queries[QUERY_DIET] = "vegan"
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }

}