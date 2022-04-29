package com.kotlinproject.modernfoodrecipesapp.adapters.ingredientsadapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.kotlinproject.modernfoodrecipesapp.R


class ViewPagerAdapter(val list : List<String>) : RecyclerView.Adapter<ViewPagerAdapter.ViewPagerViewHolder>() {
    inner class ViewPagerViewHolder(itemView : View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_view_pager,parent,false)
        return ViewPagerViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewPagerViewHolder, position: Int) {
        val text = list[position]
        holder.itemView.id
    }

    override fun getItemCount(): Int {
     return list.size
    }
}