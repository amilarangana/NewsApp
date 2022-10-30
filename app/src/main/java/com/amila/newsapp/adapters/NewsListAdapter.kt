package com.amila.newsapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.amila.newsapp.R
import com.amila.newsapp.models.Article
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.layout_news_item.view.*

class NewsListAdapter: RecyclerView.Adapter<NewsListAdapter.NewsItemViewHolder>() {

    inner class NewsItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    private val differCallback = object : DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return  oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.content == newItem.content
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsItemViewHolder {
        return NewsItemViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.layout_news_item, parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: NewsItemViewHolder, position: Int) {
        val newsItem = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(this).load(newsItem.urlToImage).into(imgNews)
            txtNewsSource.text = newsItem.source?.name
            txtNewsTitle.text = newsItem.title
            txtNewsContent.text = newsItem.content

            setOnClickListener{
                onItemClickListener?.let {it(newsItem)}
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemClickListener = listener
    }
}