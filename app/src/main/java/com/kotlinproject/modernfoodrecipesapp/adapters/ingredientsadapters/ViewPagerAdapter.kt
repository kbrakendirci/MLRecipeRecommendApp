package com.kotlinproject.modernfoodrecipesapp.adapters.ingredientsadapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter


class ViewPagerAdapter(
    recipeId: Int,
    private val fragments: ArrayList<Fragment>,
    private val title: ArrayList<String>,
    fm: FragmentManager
): FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getCount(): Int {
        return fragments.size
    }

    override fun getItem(position: Int): Fragment {

        return fragments[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return title[position]
    }
}