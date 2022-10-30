package com.amila.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import com.amila.newsapp.R
import com.amila.newsapp.ui.base.BaseFragment
import com.amila.newsapp.utils.Constants
import com.amila.newsapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_search_news.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SearchNewsFragment: BaseFragment(R.layout.fragment_search_news) {

    private val TAG: String = "SearchNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter(rvSearchNews)

        viewModel.searchNews.observe(viewLifecycleOwner) { response ->
            when (response) {
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let { newsResponse ->
                        newsListAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults / Constants.SEARCH_PAGE_SIZE + 2
                        isLastPage = viewModel.searchNewsPage == totalPages
                        if (isLastPage) {
                            rvSearchNews.setPadding(0, 0, 0, 0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                    response.message?.let { message ->
                        Toast.makeText(activity, "Error is occurred: $message",
                            Toast.LENGTH_SHORT).show()

                        Log.e(TAG, "Error is occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgress()
                }
                else -> {}
            }
        }

        var searchJob: Job? = null
        edtSearchNews.addTextChangedListener { editable ->
            searchJob?.cancel()
            searchJob = MainScope().launch {
                delay(Constants.SEARCH_DELAY)
                editable?.let {
                    if (editable.toString().isNotEmpty()){
                        viewModel.searchNewsPage = 1
                        viewModel.searchNewsResponse = null
                        viewModel.getSearchNews(editable.toString())
                    }
                }
            }
        }

        hideProgress()
    }
}