package com.singhsoft.newsapp.headline.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "news", indices = [Index(value = ["url"], unique = true)])
data class News(
    @PrimaryKey(autoGenerate = true)
    val newsId: Int = 0,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?,
    @Embedded
    val source: NewsSource?

)