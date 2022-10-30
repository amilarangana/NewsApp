package com.amila.newsapp.ui.news

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amila.newsapp.api.responses.NewsResponse
import com.amila.newsapp.repositories.NewsRepository
import com.amila.newsapp.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val newsRepository: NewsRepository) : ViewModel() {

    val breakingNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var breakingNewsPage = 1
    var breakingNewsResponse: NewsResponse? = null

    val searchNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var searchNewsPage = 1
    var searchNewsResponse: NewsResponse? = null

    val topNews: MutableLiveData<Resource<NewsResponse>> = MutableLiveData()
    var topNewsPage = 1
    var topNewsResponse: NewsResponse? = null

    init {
        getBreakingNews("science")
    }

    fun getBreakingNews(category: String) = viewModelScope.launch {
        breakingNews.postValue(Resource.Loading())
        val response = newsRepository.getBreakingNews(category, breakingNewsPage)
        breakingNews.postValue(handleBreakingNewsResponse(response))
    }

    fun getSearchNews(searchText: String) = viewModelScope.launch {
        searchNews.postValue(Resource.Loading())
        val response = newsRepository.getSearchNews(searchText, searchNewsPage)
        searchNews.postValue(handleSearchNewsResponse(response))
    }

    fun getTopNews(searchText: String) = viewModelScope.launch {
        topNews.postValue(Resource.Loading())
        val response = newsRepository.getSearchNews(searchText, topNewsPage)
        topNews.postValue(handleTopNewsResponse(response))
    }

    private fun handleBreakingNewsResponse(response: Response<NewsResponse>) :
            Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { newsResponse ->
                breakingNewsPage++
                if (breakingNewsResponse == null){
                    breakingNewsResponse = newsResponse
                }else{
                    val oldNewsItems = breakingNewsResponse?.articles
                    val newNewsItems = newsResponse.articles
                    oldNewsItems?.addAll(newNewsItems)
                }
                return Resource.Success(breakingNewsResponse ?: newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleSearchNewsResponse(response: Response<NewsResponse>) :
            Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { newsResponse ->
                searchNewsPage++
                if (searchNewsResponse == null){
                    searchNewsResponse = newsResponse
                }else{
                    val oldNewsItems = searchNewsResponse?.articles
                    val newNewsItems = newsResponse.articles
                    oldNewsItems?.addAll(newNewsItems)
                }
                return Resource.Success(searchNewsResponse ?: newsResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleTopNewsResponse(response: Response<NewsResponse>) :
            Resource<NewsResponse>{
        if (response.isSuccessful){
            response.body()?.let { newsResponse ->
                topNewsPage++
                if (topNewsResponse == null){
                    topNewsResponse = newsResponse
                }else{
                    val oldNewsItems = topNewsResponse?.articles
                    val newNewsItems = newsResponse.articles
                    oldNewsItems?.addAll(newNewsItems)
                }
                return Resource.Success(topNewsResponse ?: newsResponse)
            }
        }
        return Resource.Error(response.message())
    }
}