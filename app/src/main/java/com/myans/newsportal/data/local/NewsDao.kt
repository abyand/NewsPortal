package com.myans.newsportal.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.myans.newsportal.data.entities.News

@Dao
interface NewsDao {

    @Query("SELECT * FROM news")
    fun getAllNews() : LiveData<List<News>>

    @Query("SELECT * FROM news WHERE publishedAt = :id")
    fun getNews(id: Int): LiveData<News>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(newsList: List<News>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(news: News)
}