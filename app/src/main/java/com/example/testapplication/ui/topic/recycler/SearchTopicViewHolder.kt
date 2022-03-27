package com.example.testapplication.ui.topic.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.testapplication.data.Article
import com.example.testapplication.databinding.RowTopicsBinding
import com.example.testapplication.utils.castDate

interface TopicSelectedListener {
    fun onTopicSelected(url: String)
}

class SearchTopicViewHolder(
    parent: ViewGroup,
    private val topicSelectedListener: TopicSelectedListener,
    private val binding: RowTopicsBinding = RowTopicsBinding.inflate(
        LayoutInflater.from(parent.context), parent, false
    )
) : RecyclerView.ViewHolder(binding.root) {
    fun bindNewsToView(article: Article) {
        binding.sourceNews.text = article.source.name
        binding.publishedNews.text = article.publishedAt.castDate("T")
        Glide.with(itemView.context).load(article.urlToImage).into(binding.imageNews)
        binding.bodyNews.text = article.description
        binding.root.setOnClickListener {
            topicSelectedListener.onTopicSelected(article.url)
        }
    }
}