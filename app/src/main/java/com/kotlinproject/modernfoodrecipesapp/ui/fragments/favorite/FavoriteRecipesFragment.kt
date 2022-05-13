package com.kotlinproject.modernfoodrecipesapp.ui.fragments.favorite

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kotlinproject.modernfoodrecipesapp.R
import com.kotlinproject.ui.fragments.favorite.CustomAdapter
import kotlinx.android.synthetic.main.dropdown_item.*
import kotlinx.android.synthetic.main.fragment_favorite_recipes.*
import okhttp3.*
import org.apache.commons.csv.CSVFormat
import org.apache.commons.csv.CSVParser
import org.apache.commons.text.StringEscapeUtils
import org.json.JSONObject
import java.io.*
import java.util.concurrent.TimeUnit

class FavoriteRecipesFragment : Fragment() {
    

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupDropdown()
        button()
        super.onViewCreated(view, savedInstanceState)


    }


    private fun selectButton(): String {
        var denem=autoCompleteTextView.text
        var review = "Sort by Vote"
        print(denem)
        if (denem.equals("Sort by Vote") ){
            review = "0"
            return review

        }else{
            review = "1"
            return review
        }

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


private fun setupDropdown(){
    // get reference to the string array that we just created
    val languages = resources.getStringArray(R.array.Choose_Your_Sorting_Preference)
    // create an array adapter and pass the required parameter
    // in our case pass the context, drop down layout , and array.
    val arrayAdapter = context?.let { ArrayAdapter(it, R.layout.dropdown_item, languages) }
    // get reference to the autocomplete text view
    val autocompleteTV = autoCompleteTextView
    // set adapter to the autocomplete tv to the arrayAdapter
    autocompleteTV.setAdapter(arrayAdapter)

    print(sortOrder)

}

    private fun fillHashMap(s: String): String {
        var deneme:String
        val csvHash = HashMap<String, String>()
        resources.openRawResource(R.raw.datafood)
        val bufferedReader = BufferedReader(InputStreamReader(resources.openRawResource(R.raw.datafood)))
        val csvReader = CSVParser(bufferedReader, CSVFormat.DEFAULT
            .withFirstRecordAsHeader()
            .withIgnoreHeaderCase()
            .withTrim())

        for(csvRecords in csvReader){

            val test = csvRecords.get(0)
            val splitedCsv = test.split(";")
            csvHash.put(splitedCsv[0], splitedCsv[1])
            if(splitedCsv[1]==s){
                //Toast.makeText(context, splitedCsv[0],Toast.LENGTH_SHORT)
                deneme= splitedCsv[0]
                return deneme
            }
        }
      return 0.toString()
    }

    private fun button(){
        callButton.setOnClickListener {
            val recipeName =recipeId.text
            val sortorder = selectButton()
            val recipeId=fillHashMap("$recipeName")
            var recyclerView = recycler_view
            val data = arrayListOf<String>();


            val client = OkHttpClient.Builder()
                .connectTimeout(50, TimeUnit.SECONDS)
                .writeTimeout(50, TimeUnit.SECONDS)
                .readTimeout(50, TimeUnit.SECONDS)
                .build()

            val BASE_URL = "http://192.168.1.11:4000?recipe_id="+recipeId+"&sort_order="+sortorder

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
                                    val deneme = removeQuotesAndUnescape(json.getString("data"))
                                    val gson = Gson()
                                    val cleanedDataNew : List<List<String>> = gson.fromJson(deneme, object : TypeToken<List<List<String>>>() {}.type)
                                    val adapter = CustomAdapter(context, cleanedDataNew)
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
    }
}

