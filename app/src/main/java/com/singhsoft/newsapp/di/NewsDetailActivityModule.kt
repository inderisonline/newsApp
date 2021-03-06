package com.singhsoft.newsapp.di

import com.singhsoft.newsapp.headline.ui.NewsDetailActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class NewsDetailActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): NewsDetailActivity
}