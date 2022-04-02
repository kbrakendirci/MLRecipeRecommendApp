package com.kotlinproject.modernfoodrecipesapp.data.database
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlinproject.modernfoodrecipesapp.model.FoodRecipe
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0
}