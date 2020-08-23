package com.myans.newsportal.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myans.newsportal.data.entities.News
import com.myans.newsportal.data.repository.NewsRepository
import com.myans.newsportal.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository
): ViewModel() {
    var countryId = "id"
    var news = MutableLiveData<Resource<List<News>>>()

    fun getAllNews(countryId: String) {
        this.countryId = countryId
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                news.postValue(Resource.loading())
                news.postValue(newsRepository.getAllNews(countryId))
            }
        }
    }

}