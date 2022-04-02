package com.kotlinproject.modernfoodrecipesapp.util

class Constant{

    companion object {

        const val BASE_URL = "https://api.spoonacular.com"
        const val API_KEY = "a3206fe66742458b81063fe27ec66fc1"

        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        // ROOM Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"

    }

}