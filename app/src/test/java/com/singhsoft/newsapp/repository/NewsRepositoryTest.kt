package com.singhsoft.newsapp.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.singhsoft.newsapp.api.NewsService
import com.singhsoft.newsapp.data.AppDatabase
import com.singhsoft.newsapp.headline.data.NewsDao
import com.singhsoft.newsapp.headline.data.NewsRemoteDataSource
import com.singhsoft.newsapp.headline.data.NewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.ArgumentMatchers
import org.mockito.Mockito


@RunWith(JUnit4::class)
class NewsRepositoryTest {

    private lateinit var repository: NewsRepository
    private val dao = Mockito.mock(NewsDao::class.java)
    private val service = Mockito.mock(NewsService::class.java)
    private val remoteDataSource = NewsRemoteDataSource(service)
    private val mockRemoteDataSource = Mockito.spy(remoteDataSource)

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    @Before
    fun init() {
        val db = Mockito.mock(AppDatabase::class.java)
        Mockito.`when`(db.newsDao()).thenReturn(dao)
        Mockito.`when`(db.runInTransaction(ArgumentMatchers.any())).thenCallRealMethod()
        repository = NewsRepository(dao, remoteDataSource)
    }

    @Test
    fun loadLegoSetsFromNetwork() {
        runBlocking {
            repository.observePagedSets(connectivityAvailable = true, coroutineScope = coroutineScope)

            Mockito.verify(dao, Mockito.never()).getNewsList()
            Mockito.verifyZeroInteractions(dao)
        }
    }

}