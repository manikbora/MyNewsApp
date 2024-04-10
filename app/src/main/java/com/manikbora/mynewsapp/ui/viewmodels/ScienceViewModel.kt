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

class ScienceViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _scienceNews = MutableLiveData<List<Article>>()
    val scienceNews: LiveData<List<Article>> get() = _scienceNews

    private var isDataLoaded = false

    fun fetchScienceNews() {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getScienceNews()
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        _scienceNews.postValue(articles)
                        isDataLoaded = true
                    }
                } catch (e: UnknownHostException) {
                    Log.d("ScienceViewModel", "Network error: ${e.message}")
                } catch (e: HttpException) {
                    Log.d("ScienceViewModel", "HTTP error: ${e.message}")
                } catch (e: Exception) {
                    Log.d("ScienceViewModel", "Exception: ${e.message}")
                }
            }
        }
    }
}
