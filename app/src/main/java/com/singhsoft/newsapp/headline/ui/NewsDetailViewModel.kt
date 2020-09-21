package com.singhsoft.newsapp.headline.ui

import androidx.lifecycle.ViewModel
import com.singhsoft.newsapp.headline.data.NewsRepository
import javax.inject.Inject
import kotlin.properties.Delegates


class NewsDetailViewModel @Inject constructor(repository: NewsRepository) : ViewModel() {

  lateinit var id :String

    val news by lazy { repository.observeLocalNewsById(id) }

}
