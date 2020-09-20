package com.singhsoft.newsapp.di

import com.singhsoft.newsapp.headline.ui.HomeActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class HomeActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeHomeActivity(): HomeActivity
}