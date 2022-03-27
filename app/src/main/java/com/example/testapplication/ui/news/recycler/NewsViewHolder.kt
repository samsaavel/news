package com.example.testapplication.ui.news.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.data.Article
import com.example.testapplication.databinding.ItemRowNewsBinding
import com.example.testapplication.utils.castDate

interface NewsSelectedListener {
    fun onNewsSelected(url: String)
}

class NewsViewHolder(
    parent: ViewGroup,
    private val newsSelectedListener: NewsSelectedListener,
    private val binding: ItemRowNewsBinding = ItemRowNewsBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    fun bindNewsToView(article: Article) {
        binding.sourceNews.text = article.source.name
        binding.publishedNews.text = article.publishedAt.castDate("T")
        Glide.with(itemView.context).load(article.urlToImage).into(binding.imageNews)
        binding.bodyNews.text = article.description
        binding.root.setOnClickListener {
            newsSelectedListener.onNewsSelected(article.url)
        }
    }
}