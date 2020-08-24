package com.myans.newsportal.data.remote

import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
){

    suspend fun getNews(countryId: String) = getResult { newsService.getTopHeadlineNews(countryId) }

    suspend fun getNewsWithKeyword(keyword: String) = getResult { newsService.getNewsList(keyword) }

}