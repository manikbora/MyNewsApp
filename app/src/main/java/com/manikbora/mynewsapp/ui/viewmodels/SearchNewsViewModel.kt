package com.manikbora.mynewsapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikbora.mynewsapp.data.model.Article
import com.manikbora.mynewsapp.data.repository.NewsRepository
import kotlinx.coroutines.launch

class SearchNewsViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _searchResults = MutableLiveData<List<Article>>()
    val searchResults: LiveData<List<Article>> get() = _searchResults

    fun searchNews(query: String) {
        viewModelScope.launch {
            val response = repository.searchNews(query)
            if (response.isSuccessful) {
                val articles = response.body()?.articles
                _searchResults.value = articles!!
            } else {
                // Handle API error
            }
        }
    }
}
