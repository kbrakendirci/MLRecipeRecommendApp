package com.kotlinproject.modernfoodrecipesapp.di
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton
import android.content.Context
import androidx.room.Room
import com.kotlinproject.modernfoodrecipesapp.data.database.RecipesDatabase
import com.kotlinproject.modernfoodrecipesapp.util.Constant.Companion.DATABASE_NAME
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        RecipesDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideDao(database: RecipesDatabase) = database.recipesDao()

}