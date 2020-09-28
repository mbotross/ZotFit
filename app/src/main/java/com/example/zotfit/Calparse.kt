package com.example.zotfit
import com.fatsecret.platform.model.CompactFood
import com.fatsecret.platform.services.FatsecretService
import com.fatsecret.platform.services.Request

class Calparse {
    var home: Home? = null
    var request: Request? = null
    private var service: FatsecretService
    init {
        val key = "2bddcb3b9540419c83d720f4eef90eb8"
        val secret = "13e285dbfec5421a94a705f20659eaa2"
        service = FatsecretService(key, secret)
    }

    fun searchfood(searchItem: String): List<CompactFood>? {
        val response = service.searchFoods(searchItem)
        return response.results
    }


}