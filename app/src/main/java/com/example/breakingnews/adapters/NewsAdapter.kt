package com.example.breakingnews.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingnews.databinding.NewsItemBinding
import com.example.breakingnews.models.NewsItem
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val context: Context, private val newsList: List<NewsItem>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.bind(newsItem)
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(private val binding: NewsItemBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(newsItem: NewsItem) {
            binding.title.text = newsItem.title
            binding.date.text = newsItem.pubDate?.let { formatDate(it) }
        }

        private fun formatDate(dateString: String): String {
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            val date = dateFormat.parse(dateString)
            val formattedDateFormat = SimpleDateFormat("dd.MM.yyyy", Locale.getDefault())
            return formattedDateFormat.format(date!!)
        }
    }
}
