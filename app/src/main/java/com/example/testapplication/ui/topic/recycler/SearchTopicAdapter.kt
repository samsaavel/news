package com.example.testapplication.ui.topic.recycler

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapplication.data.Article

class SearchTopicAdapter(private val topicSelectedListener: TopicSelectedListener) :
    RecyclerView.Adapter<SearchTopicViewHolder>() {
    private val topics = mutableListOf<Article>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTopicViewHolder {
        return SearchTopicViewHolder(parent, topicSelectedListener)
    }

    override fun onBindViewHolder(holder: SearchTopicViewHolder, position: Int) {
        holder.bindNewsToView(topics[position])
    }

    override fun getItemCount() = topics.size

    fun setTopicsItems(newTopics: List<Article>) {
        topics.addAll(newTopics)
        notifyDataSetChanged()
    }

    fun updateData(clear: Boolean) {
        if (clear) {
            topics.clear()
            notifyDataSetChanged()
        } else
            Log.d("no limpie", "topics")
    }

}