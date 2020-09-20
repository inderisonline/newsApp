package com.singhsoft.newsapp.api

import com.singhsoft.newsapp.headline.data.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsService {
    companion object {
        const val ENDPOINT = "https://newsapi.org/v2/"
    }

    @GET("top-headlines")
    suspend fun getNews(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = 20,
        @Query("country") country: String?
    ): Response<ResultsResponse<News>>
}