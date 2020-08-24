package com.myans.newsportal.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.myans.newsportal.data.local.AppDatabase
import com.myans.newsportal.data.remote.NewsService
import com.myans.newsportal.data.repository.CountryRepository
import com.myans.newsportal.ui.MainActivity
import com.myans.newsportal.ui.country.CountryListViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNewsRetrofit(gson: Gson) : Retrofit = Retrofit.Builder()
        .baseUrl("https://newsapi.org/v2/")
        .addConverterFactory(GsonConverterFactory.create(gson))
        .client(OkHttpClient.Builder().addInterceptor(object : Interceptor {
            override fun intercept(chain: Interceptor.Chain): Response {
                var request: Request = chain.request()
                val url: HttpUrl =
                    request.url().newBuilder()
                        .addQueryParameter("apiKey", MainActivity.API_KEY)
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
    fun provideDatabase(@ApplicationContext appContext: Context) = AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideNewsDao(db: AppDatabase) = db.newsDao()

}
