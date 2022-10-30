package com.amila.newsapp.repositories

import com.amila.newsapp.api.RetrofitInstance
import javax.inject.Inject

class NewsRepository @Inject constructor(private val retrofitInstance : RetrofitInstance) {

    suspend fun getBreakingNews(category: String, page: Int) =
        retrofitInstance.newsApi.getBreakingNews(category, page)

    suspend fun getSearchNews(searchText: String, page: Int) =
        retrofitInstance.newsApi.getSearchNews(searchText, page)
}