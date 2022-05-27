package com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.modernfoodrecipesapp.adapters.ingredientsadapters.ViewPagerAdapter
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.DetailsPage.IngredientRecommendInstruction
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.DetailsPage.IngredientRecommendSpecify
import com.kotlinproject.modernfoodrecipesapp.ui.fragments.ingredientssearch.DetailsPage.IngredientRecommentOverview
import kotlinx.android.synthetic.main.activity_details.*

class ActivityIngredientDetails : AppCompatActivity() {
    private val args by navArgs<ActivityIngredientDetailsArgs>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ingredient_details)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(ContextCompat.getColor(this,R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        val recipeId = args.recipeId


        val fragments = ArrayList<Fragment>()
        fragments.add(IngredientRecommentOverview(recipeId))
        fragments.add(IngredientRecommendSpecify(recipeId))
        fragments.add(IngredientRecommendInstruction(recipeId))

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")
        val adapter = ViewPagerAdapter(
            recipeId ,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)

    }



//Sayfaya geri dönüş için
override fun onOptionsItemSelected(item: MenuItem): Boolean {
    if (item.itemId == android.R.id.home) {
        finish()
    }
    return super.onOptionsItemSelected(item)
}
}

private fun Bundle.putString(s: String) {

}
