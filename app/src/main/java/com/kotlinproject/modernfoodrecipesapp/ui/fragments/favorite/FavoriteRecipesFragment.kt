package com.kotlinproject.modernfoodrecipesapp.ui.fragments.favorite
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.ui.fragments.favorite.CustomAdapter

import kotlinx.android.synthetic.main.fragment_favorite_recipes.*
import okhttp3.*
import org.apache.commons.text.StringEscapeUtils
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

class FavoriteRecipesFragment : Fragment() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        callButton.setOnClickListener {
            val recipeId =recipeId.text
            val sortorder = sortOrder.text


            var recyclerView = recycler_view
            val data = arrayListOf<String>();



            val client = OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build()

            val BASE_URL = "http://192.168.1.47:4000/?recipe_id="+recipeId+"&sort_order="+sortorder

            val request = Request.Builder()
                .url(BASE_URL)
                .build()

            client.newCall(request).enqueue(object : Callback{
                override fun onFailure(call: Call, e: IOException) {
                    e.printStackTrace()
                }
                override fun onResponse(call: Call, response: Response) {
                    response.use {
                        if (!response.isSuccessful) throw IOException("Unexpected code $response")
                        val jsonString = response.body?.string()

                        try {
                            activity?.runOnUiThread(Runnable {
                                val json = JSONObject(jsonString)
                                //ekrana 1 resim bastırmak için 1 yaptık i yapınca sonuç alamıyorum.Amacım aslında 10 tane recipeı ve isimlerini göstermek
                                for (i in 0..10) {
                                    val cleanedData = removeQuotesAndUnescape(json.getString("data"))?.split(",")?.get(i)?.replace("]", "")?.replace("\"", "")
                                    cleanedData?.let { it1 -> data.add(it1) }
                                    val adapter = CustomAdapter(context, data)
                                    recyclerView.layoutManager = LinearLayoutManager(context)
                                    recyclerView.adapter = adapter
                                    var uiHandler = Handler(Looper.getMainLooper())
                                    //Burda döngü ile alındı

                                    /*   uiHandler.post(Runnable {
                                           name.setText(cleanedData);

                                       })*/
                                } })
                        }
                        catch (e: IOException){
                            Log.e("Error", e.localizedMessage);
                        }
                    }

                }
            })


        }
        super.onViewCreated(view, savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_favorite_recipes, container, false)
    }
    private fun removeQuotesAndUnescape(uncleanJson: String): String? {
        val noQuotes = uncleanJson.replace("^\"|\"$".toRegex(), "")
        return StringEscapeUtils.unescapeJava(noQuotes)
    }
    companion object {
          @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FavoriteRecipesFragment().apply {

            }
    }
}