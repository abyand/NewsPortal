package com.myans.newsportal.data.entities

data class NewsListResponse(
    val status: String,
    val totalResult: Int,
    val articles: List<NewsResponse>
)