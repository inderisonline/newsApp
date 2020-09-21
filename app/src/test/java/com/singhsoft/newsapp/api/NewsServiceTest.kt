package com.singhsoft.newsapp.api

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.Okio
import org.hamcrest.CoreMatchers
import org.junit.*
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@RunWith(JUnit4::class)
class NewsServiceTest {
    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var service: NewsService

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun createService() {
        mockWebServer = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(mockWebServer.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsService::class.java)
    }

    @After
    fun stopService() {
        mockWebServer.shutdown()
    }

    @Test
    fun requestNewsHeadline() {
        runBlocking {
            enqueueResponse("headline_news.json")
            val resultResponse = service.getNews(country = "in").body()

            val request = mockWebServer.takeRequest()
            Assert.assertNotNull(resultResponse)
            Assert.assertThat(request.path, CoreMatchers.`is`("/top-headlines?page_size=20&country=in"))
        }
    }

    @Test
    fun getNewsResponse() {
        runBlocking {
            enqueueResponse("headline_news.json")
            val resultResponse = service.getNews(country = "in").body()
            val news = resultResponse!!.articles

            Assert.assertThat(resultResponse.totalResults, CoreMatchers.`is`(4))
            Assert.assertThat(news.size, CoreMatchers.`is`(4))
        }
    }


    @Test
    fun getNewsItem() {
        runBlocking {
            enqueueResponse("headline_news.json")
            val resultResponse = service.getNews(country = "in").body()
            val news = resultResponse!!.articles

            val data = news[0]
            Assert.assertThat(data.author, CoreMatchers.`is`("Cointelegraph By Helen Partz"))
            Assert.assertThat(
                data.title,
                CoreMatchers.`is`("Wisconsin Assembly candidate is accepting Bitcoin donations again")
            )
            Assert.assertThat(
                data.description,
                CoreMatchers.`is`("“Cryptocurrency is money,” argues a Wisconsin State Assembly candidate.")
            )
            Assert.assertThat(
                data.url,
                CoreMatchers.`is`("https://cointelegraph.com/news/wisconsin-assembly-candidate-is-accepting-bitcoin-donations-again")
            )
            Assert.assertThat(
                data.urlToImage,
                CoreMatchers.`is`("https://s3.eu-central-1.amazonaws.com/s3.cointelegraph.com/uploads/2020-09/67232697-daea-4284-9a8f-6004a183a35e.jpg")
            )
            Assert.assertThat(data.publishedAt, CoreMatchers.`is`("2020-09-21T10:51:38Z"))
        }
    }

    private fun enqueueResponse(fileName: String, headers: Map<String, String> = emptyMap()) {
        val inputStream = javaClass.classLoader
            .getResourceAsStream("headline_response/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val mockResponse = MockResponse()
        for ((key, value) in headers) {
            mockResponse.addHeader(key, value)
        }
        mockWebServer.enqueue(mockResponse.setBody(
            source.readString(Charsets.UTF_8))
        )
    }
}