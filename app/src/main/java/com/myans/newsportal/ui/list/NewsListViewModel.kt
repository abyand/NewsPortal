package com.myans.newsportal.ui.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import com.myans.newsportal.data.repository.NewsRepository

class NewsListViewModel @ViewModelInject constructor(
    private val repository: NewsRepository
): ViewModel() {
    val news = repository.getAllNews()
}