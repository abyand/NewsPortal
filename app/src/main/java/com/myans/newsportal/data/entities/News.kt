package com.myans.newsportal.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news")
data class News (
    @PrimaryKey
    val publishedAt: String,
    val author: String?,
    val title: String,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val content: String?,
    var countryId: String
)