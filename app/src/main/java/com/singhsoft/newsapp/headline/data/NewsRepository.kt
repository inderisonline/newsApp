package com.singhsoft.newsapp.headline.data

import androidx.lifecycle.LiveData
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.singhsoft.newsapp.annotation.OpenForTesting
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
@OpenForTesting
class NewsRepository @Inject constructor(
    private val dao: NewsDao,
    private val newsRemoteDataSource: NewsRemoteDataSource
) {

    fun observePagedSets(
        connectivityAvailable: Boolean,
        coroutineScope: CoroutineScope
    ) = if (connectivityAvailable) observeRemotePagedSets(coroutineScope)
    else observeLocalPagedSets()

    private fun observeLocalPagedSets(): LiveData<PagedList<News>> {
        val dataSourceFactory = dao.getNewsList()
        return LivePagedListBuilder(
            dataSourceFactory,
            NewsPageDataSourceFactory.pagedListConfig()
        ).build()
    }

    fun observeLocalNewsById(newsId: String): LiveData<News> {
        return dao.getNewsById(newsId)
    }

    private fun observeRemotePagedSets(ioCoroutineScope: CoroutineScope)
            : LiveData<PagedList<News>> {
        val dataSourceFactory = NewsPageDataSourceFactory(
            newsRemoteDataSource,
            dao, ioCoroutineScope
        )
        return LivePagedListBuilder(
            dataSourceFactory,
            NewsPageDataSourceFactory.pagedListConfig()
        ).build()
    }
}
