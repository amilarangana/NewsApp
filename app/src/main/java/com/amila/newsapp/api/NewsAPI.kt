package com.amila.newsapp.api

import com.amila.newsapp.api.responses.NewsResponse
import com.amila.newsapp.utils.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface NewsAPI {

    @GET("everything")
    suspend fun getSearchNews(@Query("q") searchText: String,
                              @Query("page") page: Int = 1,
                              @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<NewsResponse>

    @GET("top-headlines")
    suspend fun getBreakingNews(@Query("category") category: String = "science",
                                @Query("page") page: Int = 1,
                                @Query("apiKey") apiKey: String = Constants.API_KEY
    ) : Response<NewsResponse>
}