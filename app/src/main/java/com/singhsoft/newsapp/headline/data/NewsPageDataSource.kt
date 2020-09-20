package com.singhsoft.newsapp.headline.data

import androidx.paging.PageKeyedDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import com.singhsoft.newsapp.data.Result
import javax.inject.Inject

class NewsPageDataSource @Inject constructor(
    private val dataSource: NewsRemoteDataSource,
    private val dao: NewsDao,
    private val scope: CoroutineScope
) : PageKeyedDataSource<Int, News>() {

    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, News>) {
        fetchData(1, params.requestedLoadSize) {
            callback.onResult(it, null, 2)
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page + 1)
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, News>) {
        val page = params.key
        fetchData(page, params.requestedLoadSize) {
            callback.onResult(it, page - 1)
        }
    }

    private fun fetchData(page: Int, pageSize: Int, callback: (List<News>) -> Unit) {
        scope.launch(getJobErrorHandler()) {
            val response = dataSource.fetchNews(page, pageSize)
            if (response.status == Result.Status.SUCCESS) {
                val results = response.data!!.articles
                dao.insertAll(results)
                callback(results)
            } else if (response.status == Result.Status.ERROR) {
                postError(response.message!!)
            }
        }
    }

    private fun getJobErrorHandler() = CoroutineExceptionHandler { _, e ->
        postError(e.message ?: e.toString())
    }

    private fun postError(message: String) {
        // TODO network error handling
        //networkState.postValue(NetworkState.FAILED)
    }

}