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

class EntertainmentViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _entertainmentNews = MutableLiveData<List<Article>>()
    val entertainmentNews: LiveData<List<Article>> get() = _entertainmentNews

    private var isDataLoaded = false

    fun fetchEntertainmentNews() {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getEntertainmentNews()
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        _entertainmentNews.postValue(articles)
                        isDataLoaded = true
                    }
                } catch (e: UnknownHostException) {
                    Log.d("EntertainmentViewModel", "Network error: ${e.message}")
                } catch (e: HttpException) {
                    Log.d("EntertainmentViewModel", "HTTP error: ${e.message}")
                } catch (e: Exception) {
                    Log.d("EntertainmentViewModel", "Exception: ${e.message}")
                }
            }
        }
    }
}