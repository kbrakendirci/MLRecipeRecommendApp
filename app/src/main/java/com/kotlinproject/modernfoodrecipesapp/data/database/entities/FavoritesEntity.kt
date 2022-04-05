package com.kotlinproject.modernfoodrecipesapp.data.database.entities
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.kotlinproject.modernfoodrecipesapp.model.Result
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.FAVORITE_RECIPES_TABLE


@Entity(tableName = FAVORITE_RECIPES_TABLE)
class FavoritesEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val result: Result
)