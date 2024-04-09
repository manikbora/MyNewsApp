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

class SportsViewModel(private val repository: NewsRepository) : ViewModel()  {

    private val _sportsNews = MutableLiveData<List<Article>>()
    val sportsNews: LiveData<List<Article>> get() = _sportsNews

    private var isDataLoaded = false

    fun fetchSportsNews() {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getSportsNews()
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        _sportsNews.postValue(articles)
                        isDataLoaded = true

                        // Log the complete endpoint URL
                        val endpoint = response.raw().request.url.toString()
                        Log.d("SportsViewModel", "Business news endpoint: $endpoint")

                    } else {
                        val endpoint = response.raw().request.url.toString()
                        Log.d("SportsViewModel", "Error fetching business news: $endpoint")
                    }
                } catch (e: UnknownHostException) {
                    Log.d("SportsViewModel", "Network error: ${e.message}")
                } catch (e: HttpException) {
                    Log.d("SportsViewModel", "HTTP error: ${e.message}")
                } catch (e: Exception) {
                    Log.d("SportsViewModel", "Exception: ${e.message}")
                }
            }
        }
    }
}