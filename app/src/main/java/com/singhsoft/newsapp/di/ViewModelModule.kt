package com.singhsoft.newsapp.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.singhsoft.newsapp.headline.ui.NewsDetailViewModel
import com.singhsoft.newsapp.headline.ui.NewsHeadlineViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(NewsHeadlineViewModel::class)
    abstract fun bindNewsHeadlineViewModel(viewModel: NewsHeadlineViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(NewsDetailViewModel::class)
    abstract fun bindNewsNewsDetailsViewModel(viewModel: NewsDetailViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
