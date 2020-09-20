package com.singhsoft.newsapp.headline.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.singhsoft.newsapp.R
import com.singhsoft.newsapp.di.Injectable
import com.singhsoft.newsapp.di.injectViewModel
import com.singhsoft.newsapp.util.ConnectivityUtil
import com.singhsoft.newsapp.util.hide
import com.singhsoft.newsapp.util.show
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), Injectable {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var viewModel: NewsHeadlineViewModel

    private val adapter: NewsHeadlinesAdapter by lazy { NewsHeadlinesAdapter() }
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AndroidInjection.inject(this)
        viewModel = injectViewModel(viewModelFactory)
        viewModel.connectivityAvailable = ConnectivityUtil.isInternetAvailable(this)
        linearLayoutManager = LinearLayoutManager(this)
        tvHeadlineTitle.text = getString(R.string.lbl_title_headlines)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(true)

        rvHeading.layoutManager = linearLayoutManager
        rvHeading.adapter = adapter
        pbLoading.show()
        subscribeUi(adapter)
    }

    private fun subscribeUi(adapter: NewsHeadlinesAdapter) {
        viewModel.news.observe(this, Observer {
            pbLoading.hide()
            adapter.submitList(it)
        })
    }

}