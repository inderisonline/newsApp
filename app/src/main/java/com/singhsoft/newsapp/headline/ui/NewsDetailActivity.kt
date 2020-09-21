package com.singhsoft.newsapp.headline.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.singhsoft.newsapp.R
import com.singhsoft.newsapp.binding.bindImageFromUrl
import com.singhsoft.newsapp.binding.bindUtcDateToIst
import com.singhsoft.newsapp.di.Injectable
import com.singhsoft.newsapp.di.injectViewModel
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_news_detail.*
import javax.inject.Inject


class NewsDetailActivity : AppCompatActivity(), Injectable {
    companion object {
        const val ARG_NEWS_ID = "ARG_NEWS_ID"
    }

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: NewsDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidInjection.inject(this)
        setContentView(R.layout.activity_news_detail)
        val newsId = intent.extras?.getString(ARG_NEWS_ID, "")
        viewModel = injectViewModel(viewModelFactory)
        newsId?.let {
            viewModel.id = it
            subscribeUi()
        }
        ivBack.setOnClickListener { onBackPressed() }


    }

    private fun subscribeUi() {
        viewModel.news.observe(this, Observer { news ->
            tvTitle.text = news.title
            bindImageFromUrl(ivImage, news.urlToImage)
            tvAuthor.text = news.source?.name
            bindUtcDateToIst(tvPublishedDate,news.publishedAt!!)
            tvContent.text = news.description
        })
    }
}