package com.myans.newsportal.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myans.newsportal.data.entities.Country
import com.myans.newsportal.data.entities.News
import com.myans.newsportal.data.repository.CountryRepository
import com.myans.newsportal.data.repository.NewsRepository
import com.myans.newsportal.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class NewsListViewModel @ViewModelInject constructor(
    private val newsRepository: NewsRepository,
    private val countryRepository: CountryRepository
): ViewModel() {
    var selectedCountry = countryRepository.getSelectedCountry()
    var news = MutableLiveData<Resource<List<News>>>()
    private var keyword = "INITIAL KEYWORD"
    fun getKeyword() = keyword

    fun saveSelectedCountry(country: Country) {
        selectedCountry = country
        countryRepository.saveSelectedCountry(country)
    }

    fun getAllNews(keyword: String = "") {
        if (this.keyword.equals(keyword))
            return
        this.keyword = keyword
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                news.postValue(Resource.loading())
                if (this@NewsListViewModel.keyword.isEmpty()) {
                    news.postValue(newsRepository.getAllNews(selectedCountry.countryId))
                } else {
                    news.postValue(newsRepository.getNewsWithKeyword(this@NewsListViewModel.keyword))
                }
            }
        }
    }

}