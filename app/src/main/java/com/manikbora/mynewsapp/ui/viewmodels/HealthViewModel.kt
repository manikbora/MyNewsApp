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

class HealthViewModel(private val repository: NewsRepository) : ViewModel()  {

    private val _healthNews = MutableLiveData<List<Article>>()
    val healthNews: LiveData<List<Article>> get() = _healthNews

    private var isDataLoaded = false

    fun fetchHealthNews() {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getHealthNews()
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        _healthNews.postValue(articles)
                        isDataLoaded = true
                    }
                } catch (e: UnknownHostException) {
                    Log.d("HealthViewModel", "Network error: ${e.message}")
                } catch (e: HttpException) {
                    Log.d("HealthViewModel", "HTTP error: ${e.message}")
                } catch (e: Exception) {
                    Log.d("HealthViewModel", "Exception: ${e.message}")
                }
            }
        }
    }
}