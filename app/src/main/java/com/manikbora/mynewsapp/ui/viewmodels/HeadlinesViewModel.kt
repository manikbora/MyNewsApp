package com.manikbora.mynewsapp.ui.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.manikbora.mynewsapp.data.model.Article
import com.manikbora.mynewsapp.data.repository.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

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
                    }
                } catch (e: UnknownHostException) {
                    Log.d("HeadlinesViewModel", "Network error: ${e.message}")
                } catch (e: HttpException) {
                    Log.d("HeadlinesViewModel", "HTTP error: ${e.message}")
                } catch (e: Exception) {
                    Log.d("HeadlinesViewModel", "Exception: ${e.message}")
                }
            }
        }
    }
}