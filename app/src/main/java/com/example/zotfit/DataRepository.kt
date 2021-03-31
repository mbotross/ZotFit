package com.example.zotfit

import android.net.Uri

class DataRepository(val database: Database) {

    var username = ""
    var password = ""
    fun setLogin(username: String, password:String){
        this.username = username;
        this.password = password;
    }

    fun getDailyData(username: String): DailyData{
        return database.getDailyData(username)
    }

    fun updateDailyData(username: String, cals: String, fat: String, protein: String, carbs: String): Boolean{
        return database.updateDailyData(username, cals, fat, protein, carbs)
    }

    fun insertimage(imageUri: Uri): Boolean{
        return database.insertimage(imageUri)
    }

    fun getimage(username: String): String{
        return database.getimage(username)
    }

    //checking usernames and password match
    fun checkuser(username: String, password: String): Boolean{
        return database.checkuser(username, password)
    }
    fun adduser(username: String, password: String): Boolean{
        return database.adduser(username, password)
    }
}