package com.amila.newsapp.ui.news

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import com.amila.newsapp.R
import com.amila.newsapp.ui.base.BaseFragment
import com.amila.newsapp.utils.Constants
import com.amila.newsapp.utils.Resource
import kotlinx.android.synthetic.main.fragment_breaking_news.*
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class BreakingNewsFragment : BaseFragment(R.layout.fragment_breaking_news) {

    private val TAG: String = "BreakingNewsFragment"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerViewAdapter(rvBreakingNews)

        viewModel.breakingNews.observe(viewLifecycleOwner, Observer { response ->
            when(response){
                is Resource.Success -> {
                    hideProgress()
                    response.data?.let { newsResponse ->
                        newsListAdapter.differ.submitList(newsResponse.articles.toList())
                        val totalPages = newsResponse.totalResults/ Constants.PAGE_SIZE + 2
                        isLastPage = viewModel.breakingNewsPage == totalPages
                        if (isLastPage){
                            rvBreakingNews.setPadding(0,0,0,0)
                        }
                    }
                }
                is Resource.Error -> {
                    hideProgress()
                    response.message?.let { message ->
                        MainScope().launch {
                            Toast.makeText(activity, "Error is occurred: $message", Toast.LENGTH_SHORT).show()
                        }

                        Log.e(TAG, "Error is occurred: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgress()
                }
                else -> {}
            }
        })
    }
}