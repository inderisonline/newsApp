package com.singhsoft.newsapp.headline.data
import com.singhsoft.newsapp.annotation.OpenForTesting
import com.singhsoft.newsapp.api.BaseDataSource
import com.singhsoft.newsapp.api.NewsService
import javax.inject.Inject

@OpenForTesting
class NewsRemoteDataSource @Inject constructor(private val service: NewsService) : BaseDataSource() {

    suspend fun fetchNews(page: Int, pageSize: Int? = null, country: String? ="in")
            = getResult { service.getNews(page, pageSize, country) }

}