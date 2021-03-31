package com.example.zotfit

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel

class DataViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: DataRepository
    var username = ""
    var password = ""
    init {
        val database = Database.getInstance(application.applicationContext)
        repository = DataRepository(database)
        username = repository.username
        password = repository.password
    }
    fun getDailyData(username: String): DailyData{
        return repository.getDailyData(username)
    }

    fun updateDailyData(username: String, cals: String, fat: String, protein: String, carbs: String): Boolean{
        return repository.updateDailyData(username, cals, fat, protein, carbs)
    }

    fun insertimage(imageUri: Uri): Boolean{
        return repository.insertimage(imageUri)
    }

    fun getimage(username: String): String{
        return repository.getimage(username)
    }

    //checking usernames and password match
    fun checkuser(username: String, password: String): Boolean{
        return repository.checkuser(username, password)
    }
    fun adduser(username: String, password: String): Boolean{
        return repository.adduser(username, password)
    }
}