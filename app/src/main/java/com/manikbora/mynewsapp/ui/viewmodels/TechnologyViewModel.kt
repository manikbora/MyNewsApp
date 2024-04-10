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

class TechnologyViewModel(private val repository: NewsRepository) : ViewModel() {

    private val _technologyNews = MutableLiveData<List<Article>>()
    val technologyNews: LiveData<List<Article>> get() = _technologyNews

    private var isDataLoaded = false

    fun fetchTechnologyNews() {
        if (!isDataLoaded) {
            viewModelScope.launch(Dispatchers.IO) {
                try {
                    val response = repository.getTechnologyNews()
                    if (response.isSuccessful) {
                        val articles = response.body()?.articles ?: emptyList()
                        _technologyNews.postValue(articles)
                        isDataLoaded = true
                    }
                } catch (e: UnknownHostException) {
                    Log.d("TechnologyViewModel", "Network error: ${e.message}")
                } catch (e: HttpException) {
                    Log.d("TechnologyViewModel", "HTTP error: ${e.message}")
                } catch (e: Exception) {
                    Log.d("TechnologyViewModel", "Exception: ${e.message}")
                }
            }
        }
    }
}
