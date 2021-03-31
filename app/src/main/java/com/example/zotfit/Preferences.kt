package com.example.zotfit

object Preferences {
    var username = "";
    var password = "";
    fun setLogin(username: String, password:String){
        this.username = username;
        this.password = password;
    }


}