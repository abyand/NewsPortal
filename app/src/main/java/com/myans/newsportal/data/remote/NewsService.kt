package com.myans.newsportal.data.remote

import com.myans.newsportal.data.entities.News
import com.myans.newsportal.data.entities.NewsList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface NewsService {

    @GET("top-headlines")
    suspend fun getNewsList(@Query("country") countryId: String) : Response<NewsList>

    @GET("character/{id}")
    suspend fun getNewsDetail(@Path("id") id: Int): Response<News>
}