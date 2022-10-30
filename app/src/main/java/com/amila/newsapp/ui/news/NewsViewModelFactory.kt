package com.amila.newsapp.ui.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.amila.newsapp.repositories.NewsRepository

class NewsViewModelProviderFactory(private val newsRepository: NewsRepository)
    : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsViewModel(newsRepository) as T
    }
}