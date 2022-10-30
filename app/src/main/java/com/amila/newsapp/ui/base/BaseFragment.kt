package com.amila.newsapp.ui.base

import android.os.Bundle
import android.view.View
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.amila.newsapp.adapters.NewsListAdapter
import com.amila.newsapp.ui.news.*
import com.amila.newsapp.utils.Constants
import kotlinx.android.synthetic.main.fragment_search_news.*

abstract class BaseFragment(viewId: Int) : Fragment(viewId) {

    lateinit var viewModel: NewsViewModel
    lateinit var newsListAdapter: NewsListAdapter

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as NewsActivity).newsViewModel

        if (this is BreakingNewsFragment){

        }
    }

    private val scrollListener = object : RecyclerView.OnScrollListener(){

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL){
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalLargerThanVisible = totalItemCount >=
                    if(this@BaseFragment is BreakingNewsFragment)
                        Constants.PAGE_SIZE else Constants.SEARCH_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning
                    && isTotalLargerThanVisible && isScrolling

            if (shouldPaginate){
                when(this@BaseFragment){
                    is BreakingNewsFragment -> viewModel.getBreakingNews("science")
                    is SearchNewsFragment -> viewModel.getSearchNews(edtSearchNews.text.toString())
                    is TopNewsFragment -> viewModel.getTopNews("all")
                }

                isScrolling = false
            }
        }
    }

     fun hideProgress() {
        pbPagination.visibility = View.INVISIBLE
        isLoading = false
    }

     fun showProgress(){
        pbPagination.visibility = View.VISIBLE
        isLoading = true
    }

     fun setupRecyclerViewAdapter(recyclerView: RecyclerView){
        newsListAdapter = NewsListAdapter()
         recyclerView.apply {
            adapter = newsListAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@BaseFragment.scrollListener)
        }
    }

}