package com.myans.newsportal.data.repository

import com.myans.newsportal.data.local.NewsDao
import com.myans.newsportal.data.remote.NewsRemoteDataSource
import com.myans.newsportal.utils.performGetOperation
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val remoteDataSource: NewsRemoteDataSource,
    private val localDataSource: NewsDao
) {

    fun getNews(id: Int) = performGetOperation(
        databaseQuery = {localDataSource.getNews(id)},
        networkCall = {remoteDataSource.getNewsDetail(id)},
        saveCallResult = {localDataSource.insert(it)}
    )

    fun getAllNews(countryId: String) = performGetOperation(
        databaseQuery = {localDataSource.getAllNews()},
        networkCall = {remoteDataSource.getNews(countryId)},
        saveCallResult = {localDataSource.insertAll(it.articles)}
    )

}