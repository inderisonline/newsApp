package com.singhsoft.newsapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.singhsoft.newsapp.headline.data.News
import com.singhsoft.newsapp.headline.data.NewsDao

/**
 * The Room database for this app
 */
@Database(
    entities = [News::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun newsDao(): NewsDao

    companion object {
        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "newsapp-db")
                .addCallback(object : RoomDatabase.Callback() {
                })
                .build()
        }
    }
}
