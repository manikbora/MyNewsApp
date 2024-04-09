package com.manikbora.mynewsapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import android.util.Log // Import Log class
import com.manikbora.mynewsapp.data.model.Article
import com.manikbora.mynewsapp.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// BusinessViewModel.kt

class BusinessViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _businessNews = MutableLiveData<List<Article>>()
    val businessNews: LiveData<List<Article>> get() = _businessNews

    private var isDataLoaded = false

    fun fetchBusinessNews() {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getBusinessNews()
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        _businessNews.postValue(articles)
                        isDataLoaded = true

                        // Log success message and number of articles fetched
                        Log.d("BusinessViewModel", "Business news fetched successfully")
                        Log.d("BusinessViewModel", "Number of articles fetched: ${articles.size}")
                    } else {
                        Log.e("BusinessViewModel", "Error fetching business news: ${response.message()}")
                    }
                } catch (e: Exception) {
                    Log.e("BusinessViewModel", "Exception: ${e.message}", e)
                }
            }
        }
    }
}

