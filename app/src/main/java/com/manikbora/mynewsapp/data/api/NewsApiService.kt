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

    @GET("v2/everything")
    suspend fun getEntertainmentNews(
        @Query("q") query: String = "entertainment",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getSportsNews(
        @Query("q") query: String = "sports",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getHealthNews(
        @Query("q") query: String = "health",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getScienceNews(
        @Query("q") query: String = "science",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun getTechnologyNews(
        @Query("q") query: String = "technology",
        @Query("apiKey") apiKey: String = API_KEY
    ): Response<NewsResponse>
}
