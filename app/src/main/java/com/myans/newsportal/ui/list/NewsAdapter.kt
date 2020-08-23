package com.myans.newsportal.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.myans.newsportal.R
import com.myans.newsportal.data.entities.News
import com.myans.newsportal.databinding.NewsItemBinding

class NewsAdapter(private val listener: NewsItemListener) : RecyclerView.Adapter<NewsViewHolder>() {

    interface NewsItemListener {
        fun onCLickedNews(newsItem: News)
    }

    private val items = ArrayList<News>()

    fun setItems(items: List<News>) {
        this.items.clear()
        this.items.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        val binding: NewsItemBinding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(binding, listener)
    }

    override fun getItemViewType(position: Int): Int {

        return super.getItemViewType(position)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) = holder.bind(items[position])
}

class NewsViewHolder(private val itemBinding: NewsItemBinding, private val listener: NewsAdapter.NewsItemListener) : RecyclerView.ViewHolder(itemBinding.root),
    View.OnClickListener {

    private lateinit var news: News

    init {
        itemBinding.root.setOnClickListener(this)
    }

    fun bind(item: News) {
        this.news = item
        itemBinding.name.text = item.title
        itemBinding.speciesAndStatus.text = item.description
        Glide.with(itemBinding.root)
            .load(item.urlToImage)
            .placeholder(R.drawable.placeholder_news)
            .into(itemBinding.image)
    }

    override fun onClick(v: View?) {
        listener.onCLickedNews(news)
    }
}

