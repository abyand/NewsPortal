package com.myans.newsportal.data.entities

data class NewsResponse(
    val publishedAt: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val content: String?
)