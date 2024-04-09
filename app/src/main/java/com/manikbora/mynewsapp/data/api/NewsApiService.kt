package com.manikbora.mynewsapp.data.api

import com.manikbora.mynewsapp.data.model.NewsResponse
import com.manikbora.mynewsapp.utils.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {

    @GET("v2/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getBusinessNews(
        @Query("q") query: String = "business",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}
