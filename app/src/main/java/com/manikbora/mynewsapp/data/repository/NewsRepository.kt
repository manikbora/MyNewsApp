package com.manikbora.mynewsapp.data.repository

import com.manikbora.mynewsapp.data.api.NewsApiService
import com.manikbora.mynewsapp.data.dao.SavedArticleDao
import com.manikbora.mynewsapp.data.model.Article
import com.manikbora.mynewsapp.data.model.NewsResponse
import com.manikbora.mynewsapp.data.model.SavedArticle
import com.manikbora.mynewsapp.utils.Constants
import retrofit2.Response

class NewsRepository(private val newsApiService: NewsApiService) {

//    suspend fun insertArticle(article: SavedArticle) {
//        savedArticleDao.insertArticle(article)
//    }
//
//    suspend fun deleteArticle(article: SavedArticle) {
//        savedArticleDao.deleteArticle(article)
//    }
//
//    suspend fun getAllSavedArticles(): List<SavedArticle> {
//        return savedArticleDao.getAllSavedArticles()
//    }
//
//    suspend fun getArticleById(articleId: Long): SavedArticle? {
//        return savedArticleDao.getArticleById(articleId)
//    }

    suspend fun getTopHeadlines(country: String): Response<NewsResponse> {
        return newsApiService.getTopHeadlines(country, Constants.API_KEY)
    }

    suspend fun getBusinessNews(): Response<NewsResponse> {
        return newsApiService.getBusinessNews(query = "business", apiKey = Constants.API_KEY)
    }

    suspend fun getEntertainmentNews(): Response<NewsResponse> {
        return newsApiService.getEntertainmentNews(query = "entertainment", apiKey = Constants.API_KEY)
    }

    suspend fun getSportsNews(): Response<NewsResponse> {
        return newsApiService.getSportsNews(query = "sports", apiKey = Constants.API_KEY)
    }

    suspend fun getHealthNews(): Response<NewsResponse> {
        return newsApiService.getHealthNews(query = "health", apiKey = Constants.API_KEY)
    }

    suspend fun getScienceNews(): Response<NewsResponse> {
        return newsApiService.getScienceNews(query = "science", apiKey = Constants.API_KEY)
    }

    suspend fun getTechnologyNews(): Response<NewsResponse> {
        return newsApiService.getTechnologyNews(query = "technology", apiKey = Constants.API_KEY)
    }

}
