package com.kotlinproject.modernfoodrecipesapp.ui.fragments.nltrecommend

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.kotlinproject.modernfoodrecipesapp.R
import com.squareup.picasso.Picasso

class NltkFragmentRecommendAdapter(private val context: Context?, private val list: List<List<String>>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private inner class ViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal var tvLabel: TextView
        internal lateinit var tv_img: ImageView

        init {
            tvLabel = itemView.findViewById(R.id.tv_label) // Initialize your All views prensent in list items
            tv_img= itemView.findViewById(R.id.tv_img)
        }

        internal fun bind(position: Int) {
            // This method will be called anytime a list item is created or update its data
            //Do your stuff here
            tvLabel.text = list[position][0]
            val imageUri = Uri.parse(list[position][4])
            Picasso.get()
                .load(imageUri)
                .noFade()
                .into(tv_img)

        }


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.nltk_recommend_itemlist, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as ViewHolder).bind(position)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}