package com.singhsoft.newsapp.headline.data

import androidx.paging.DataSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface NewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(news: List<News>)

    @Query("SELECT * FROM NEWS ORDER BY publishedAt DESC ")
    fun getNewsList(): DataSource.Factory<Int, News>

    @Query("SELECT * FROM news WHERE newsId=:newsId")
    fun getNewsById(newsId: Int): News
}