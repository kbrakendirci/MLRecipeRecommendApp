package com.kotlinproject.modernfoodrecipesapp.data
import com.kotlinproject.modernfoodrecipesapp.data.network.FoodRecipesApi
import com.kotlinproject.modernfoodrecipesapp.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject


class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

}