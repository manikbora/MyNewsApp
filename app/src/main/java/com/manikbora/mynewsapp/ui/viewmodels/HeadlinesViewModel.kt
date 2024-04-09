package com.manikbora.mynewsapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikbora.mynewsapp.data.model.Article
import com.manikbora.mynewsapp.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HeadlinesViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _topHeadlines = MutableLiveData<List<Article>>()
    val topHeadlines: LiveData<List<Article>> get() = _topHeadlines

    private var isDataLoaded = false

    fun fetchTopHeadlines(country: String) {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getTopHeadlines(country)
                    if (response.isSuccessful) {
                        _topHeadlines.postValue(response.body()?.articles ?: emptyList())
                        isDataLoaded = true
                    } else {
                        // Handle error (e.g., show error message)
                    }
                } catch (e: Exception) {
                    // Handle network error
                }
            }
        }
    }
}