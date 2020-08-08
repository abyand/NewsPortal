package com.myans.newsportal.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.myans.newsportal.data.entities.News
import com.myans.newsportal.utils.SourceConverterClass

@Database(entities = [News::class], version = 1, exportSchema = false)
@TypeConverters(SourceConverterClass::class)
abstract class AppDatabase: RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "news")
                .fallbackToDestructiveMigration()
                .build()
    }

}