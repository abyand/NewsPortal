package com.myans.newsportal.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myans.newsportal.data.local.AppDatabase
import com.myans.newsportal.data.local.NewsDao
import com.myans.newsportal.data.remote.NewsRemoteDataSource
import com.myans.newsportal.data.remote.NewsService
import com.myans.newsportal.data.repository.NewsRepository
import com.myans.newsportal.ui.MainActivity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url().newBuilder()
                        .addQueryParameter("apiKey", MainActivity.API_KEY)
                        .addQueryParameter("country", "id")
                        .build()
                request = request.newBuilder().url(url).build()
                return chain.proceed(request)
            }

        }).build())
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideNewsService(retrofit: Retrofit): NewsService = retrofit.create(
        NewsService::class.java)

    @Singleton
    @Provides
    fun provideCharacterRemoteDataSource(newsService: NewsService) = NewsRemoteDataSource(newsService)


    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideCharacterDao(db: AppDatabase) = db.newsDao()

    @Singleton
    @Provides
    fun provideRepository(remoteDataSource: NewsRemoteDataSource,
                          localDataSource: NewsDao) =
        NewsRepository(remoteDataSource, localDataSource)


}