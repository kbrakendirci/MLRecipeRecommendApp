package com.kotlinproject.modernfoodrecipesapp.viewmodels
import android.app.Application
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.kotlinproject.modernfoodrecipesapp.data.DataStoreRepository
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.API_KEY
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.DEFAULT_DIET_TYPE
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.DEFAULT_MEAL_TYPE
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.DEFAULT_RECIPES_NUMBER
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_API_KEY
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_DIET
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_FILL_INGREDIENTS
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_NUMBER
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_SEARCH
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class RecipesViewModel @Inject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) : AndroidViewModel(application) {
    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false


    fun applySearchQuery(searchQuery: String): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_SEARCH] = searchQuery
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"
        return queries
    }
    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }


    fun applyQueries():HashMap<String, String> {
        val queries = HashMap<String, String>()
viewModelScope.launch {
    readMealAndDietType.collect { value ->
        mealType=value.selectedMealType
        dietType=value.selectedDietType
    }
}

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }


    private fun saveBackOnline(backOnline: Boolean) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }

    fun showNetworkStatus() {
        if (!networkStatus) {
            Toast.makeText(getApplication(), "No Internet Connection.", Toast.LENGTH_SHORT).show()
            saveBackOnline(true)
        } else if (networkStatus) {
            if (backOnline) {
                Toast.makeText(getApplication(), "We're back online.", Toast.LENGTH_SHORT).show()
                saveBackOnline(false)
            }
        }
    }

}