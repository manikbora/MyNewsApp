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

                        // Log the complete endpoint URL
                        val endpoint = response.raw().request.url.toString()
                        Log.d("BusinessViewModel", "Business news endpoint: $endpoint")

                    } else {
                        println("Error fetching business news: ${response.message()}")
                    }
                } catch (e: UnknownHostException) {
                    println("Network error: ${e.message}")
                } catch (e: HttpException) {
                    println("HTTP error: ${e.message}")
                } catch (e: Exception) {
                    println("Exception: ${e.message}")
                }
            }
        }
    }
}
