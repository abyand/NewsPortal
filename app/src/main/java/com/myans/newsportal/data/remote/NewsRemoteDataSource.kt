package com.myans.newsportal.data.remote

import javax.inject.Inject

class NewsRemoteDataSource @Inject constructor(
    private val newsService: NewsService
): BaseDataSource(){

    suspend fun getNews() = getResult { newsService.getNewsList() }
    suspend fun getNewsDetail(id: Int) = getResult { newsService.getNewsDetail(id) }

}