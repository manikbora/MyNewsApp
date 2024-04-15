package com.manikbora.mynewsapp.data.api

import com.manikbora.mynewsapp.utils.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun createService(): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}
