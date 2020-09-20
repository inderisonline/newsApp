package com.singhsoft.newsapp.headline.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.singhsoft.newsapp.databinding.ListItemHeadlinesBinding
import com.singhsoft.newsapp.headline.data.News


class NewsHeadlinesAdapter : PagedListAdapter<News, NewsHeadlinesAdapter.ViewHolder>(NewsDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val news = getItem(position)
        news?.let {
            holder.apply {
                bind(createOnClickListener(news.newsId), news)
                itemView.tag = news
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemHeadlinesBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun createOnClickListener(newsId: Int): View.OnClickListener {
        return View.OnClickListener {

        }
    }


    class ViewHolder(private val binding: ListItemHeadlinesBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(listener: View.OnClickListener, item: News) {
            binding.apply {
                clickListener = listener
                news = item
                executePendingBindings()
            }
        }
    }
}

private class NewsDiffCallback : DiffUtil.ItemCallback<News>() {

    override fun areItemsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem.newsId == newItem.newsId
    }

    override fun areContentsTheSame(oldItem: News, newItem: News): Boolean {
        return oldItem == newItem
    }
}