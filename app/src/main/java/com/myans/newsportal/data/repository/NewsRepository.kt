package com.myans.newsportal.data.repository

import com.myans.newsportal.data.entities.News
import com.myans.newsportal.data.entities.NewsResponse
import com.myans.newsportal.data.local.NewsDao
import com.myans.newsportal.data.local.PreferenceProvider
import com.myans.newsportal.data.remote.NewsRemoteDataSource
import com.myans.newsportal.utils.performGetOperation
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsDao
) {

    suspend fun getAllNews(countryId: String) = performGetOperation(
        databaseQuery = {localDataSource.getAllNews(countryId)},
        networkCall = {remoteDataSource.getNews(countryId)},
        saveCallResult = {localDataSource.insertAll(it)},
        dataBridge = {bridgeNewsResponseToNewsEntities(it.articles, countryId)}
    )

    suspend fun getNewsWithKeyword(keyword: String) = performGetOperation(
        databaseQuery = {localDataSource.getNewsWithKeyword(keyword)},
        networkCall = {remoteDataSource.getNewsWithKeyword(keyword)},
        saveCallResult = {localDataSource.insertAll(it)},
        dataBridge = {bridgeNewsResponseToNewsEntities(it.articles, "")}
    )

    fun bridgeNewsResponseToNewsEntities(listNewsResponse: List<NewsResponse>, countryId: String): List<News> =
        listNewsResponse.map {
            News(
                publishedAt = it.publishedAt,
                author = it.author,
                title = it.title,
                description = it.description,
                url = it.url,
                urlToImage = it.urlToImage,
                content = it.content,
                countryId = countryId
            )
        }

}