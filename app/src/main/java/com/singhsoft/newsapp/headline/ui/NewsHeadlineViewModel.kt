package com.singhsoft.newsapp.headline.ui

import androidx.lifecycle.ViewModel
import com.singhsoft.newsapp.di.CoroutineScropeIO
import com.singhsoft.newsapp.headline.data.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject

class NewsHeadlineViewModel @Inject constructor(private val repository: NewsRepository,
                                                @CoroutineScropeIO private val ioCoroutineScope: CoroutineScope
) : ViewModel() {

    var connectivityAvailable: Boolean = false

    val news by lazy {
        repository.observePagedSets(connectivityAvailable, ioCoroutineScope)
    }

    /**
     * Cancel all coroutines when the ViewModel is cleared.
     */
    override fun onCleared() {
        super.onCleared()
        ioCoroutineScope.cancel()
    }
}
