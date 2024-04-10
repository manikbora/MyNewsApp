package com.manikbora.mynewsapp.data.api

import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://newsapi.org/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val newsApiService: NewsApiService by lazy {
        retrofit.create(NewsApiService::class.java)
    }

    fun createService(): NewsApiService {
        return retrofit.create(NewsApiService::class.java)
    }
}
