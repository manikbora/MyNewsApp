package com.manikbora.mynewsapp.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikbora.mynewsapp.data.model.Article
import com.manikbora.mynewsapp.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusinessViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _businessNews = MutableLiveData<List<Article>>()
    val businessNews: LiveData<List<Article>> get() = _businessNews

    private var isDataLoaded = false

    fun fetchBusinessNews(country: String) {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getBusinessNews(country)
                    if (response.isSuccessful) {
                        _businessNews.postValue(response.body()?.articles ?: emptyList())
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
