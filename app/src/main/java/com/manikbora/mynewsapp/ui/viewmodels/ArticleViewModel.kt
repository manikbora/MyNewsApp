package com.manikbora.mynewsapp.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.manikbora.mynewsapp.data.api.RetrofitClient
import com.manikbora.mynewsapp.data.database.NewsDatabase
import com.manikbora.mynewsapp.data.model.SavedArticle
import com.manikbora.mynewsapp.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ArticleViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: NewsRepository
    private val allSavedArticles: MutableLiveData<List<SavedArticle>> = MutableLiveData() // MutableLiveData to hold the list of saved articles

    init {
        val savedArticleDao = NewsDatabase.getDatabase(application).savedArticleDao()
        val newsApiService = RetrofitClient.newsApiService
        repository = NewsRepository(newsApiService, savedArticleDao)
        viewModelScope.launch {
            // Call the suspend function within a coroutine and update the MutableLiveData
            allSavedArticles.value = repository.getAllSavedArticles()
        }
    }

    fun getAllSavedArticles(): LiveData<List<SavedArticle>> {
        return allSavedArticles // Return LiveData to observe changes in the list of saved articles
    }
}

