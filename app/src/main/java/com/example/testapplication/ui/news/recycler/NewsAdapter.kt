package com.example.testapplication.ui.news.recycler

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.data.Article

class NewsAdapter(private val newsSelectedListener: NewsSelectedListener) :
    RecyclerView.Adapter<NewsViewHolder>() {
    private val news = mutableListOf<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        return NewsViewHolder(parent, newsSelectedListener)
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bindNewsToView(news[position])
    }

    override fun getItemCount() = news.size

    fun setNewsItems(newNews: List<Article>) {
        news.addAll(newNews)
        notifyDataSetChanged()
    }

    fun updatedata(clear: Boolean) {
        if (clear) {
            news.clear()
            notifyDataSetChanged()
        } else
            Log.d("no limpie nada", "x")
    }
}