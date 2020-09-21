package com.singhsoft.newsapp.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.singhsoft.newsapp.headline.data.NewsRepository
import com.singhsoft.newsapp.headline.ui.NewsHeadlineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito


@RunWith(JUnit4::class)
class NewsHeadlineViewModelTest {

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val repository = Mockito.mock(NewsRepository::class.java)
    private var viewModel = NewsHeadlineViewModel(repository, coroutineScope)

    @Test
    fun testNull() {
        MatcherAssert.assertThat(viewModel.connectivityAvailable, CoreMatchers.notNullValue())
    }

    @Test
    fun doNotFetchWithoutObservers() {
        Mockito.verify(repository, Mockito.never()).observePagedSets(false, coroutineScope)
    }

    @Test
    fun doNotFetchWithoutObserverOnConnectionChange() {
        viewModel.connectivityAvailable = true
        Mockito.verify(repository, Mockito.never()).observePagedSets(true, coroutineScope)
    }


}