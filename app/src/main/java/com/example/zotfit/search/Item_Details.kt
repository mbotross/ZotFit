package com.example.zotfit.search

import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.zotfit.DailyData
import com.example.zotfit.Database
import com.example.zotfit.Preferences
import com.example.zotfit.R

class Item_Details : AppCompatActivity() {
    private lateinit var calories: String
    private lateinit var database: Database
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item__details)
        database = Database.getInstance(applicationContext)
        val webView: WebView = findViewById(R.id.webView)
        val bundle = intent.extras
        val url = bundle?.getString("url")
        val description = bundle?.getString("description")
        val splitDescription: List<String>? = description?.split("|")
        webView.webViewClient = WebViewClient()
        webView.loadUrl(url)
        webView.settings.javaScriptEnabled = true
        webView.settings.supportZoom()
        var dataList: MutableList<Float> = mutableListOf()

        val additem: Button = findViewById(R.id.add_item)
        splitDescription?.let{
            for(string in splitDescription) dataList.add(parseData(string))
        }
        additem.setOnClickListener{
            updatedailyData(dataList)
        }
    }

    private fun parseData(description: String): Float{
        var str: List<String> = description.split(":")
        var integer = str.get(1)
        integer = integer.replace("[^\\d.]".toRegex(), "")
        return integer.toFloat()
    }

    private fun updatedailyData(data: List<Float>){
        if(data.isNotEmpty()){
            val getData = database.getDailyData(Preferences.username)
            val cals = getData.calorories + data[0]
            val fat = getData.fat + data[1]
            val protein = getData.protein + data[2]
            val carbs = getData.carbohydrates + data[3]
            println("cals"+ cals)
            database.updateDailyData(Preferences.username, cals.toString(), fat.toString(), protein.toString(), carbs.toString())
        }
    }
}