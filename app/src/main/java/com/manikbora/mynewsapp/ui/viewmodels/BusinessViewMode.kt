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
                    }
                } catch (e: UnknownHostException) {
                    Log.d("BusinessViewModel", "Network error: ${e.message}")
                } catch (e: HttpException) {
                    Log.d("BusinessViewModel", "HTTP error: ${e.message}")
                } catch (e: Exception) {
                    Log.d("BusinessViewModel", "Exception: ${e.message}")
                }
            }
        }
    }
}
