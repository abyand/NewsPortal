package com.myans.newsportal.data.entities

data class NewsList(
    val status: String,
    val totalResult: Int,
    val articles: List<News>
)