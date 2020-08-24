package com.myans.newsportal.data.remote

import com.myans.newsportal.data.entities.NewsListResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    @GET("top-headlines")
    suspend fun getTopHeadlineNews(@Query("country") countryId: String = "id") : Response<NewsListResponse>


    @GET("everything")
    suspend fun getNewsList(@Query("q") querySearch: String) : Response<NewsListResponse>
}