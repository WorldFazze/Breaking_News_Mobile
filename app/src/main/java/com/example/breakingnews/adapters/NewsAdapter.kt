package com.example.breakingnews.adapters

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.example.breakingnews.main.MainActivity
import com.example.breakingnews.NewsDetailActivity
import com.example.breakingnews.databinding.NewsItemBinding
import com.example.breakingnews.models.NewsItem
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*

class NewsAdapter(private val context: Context, private val newsList: List<NewsItem>) :
    RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = NewsItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val newsItem = newsList[position]
        holder.bind(newsItem)
        holder.itemView.setOnClickListener {
            val display =
                (context.getSystemService(Context.WINDOW_SERVICE) as WindowManager).defaultDisplay
            val rotation = display.rotation
            val config = context.resources.configuration

            if (rotation == Surface.ROTATION_0 || rotation == Surface.ROTATION_180) {
                if (config.orientation == Configuration.ORIENTATION_PORTRAIT) {
                    val intent = Intent(context, NewsDetailActivity::class.java)
                    intent.putExtra("newsItem", newsItem)
                    context.startActivity(intent)
                }
            } else if (rotation == Surface.ROTATION_90 || rotation == Surface.ROTATION_270) {
                if (config.orientation == Configuration.ORIENTATION_LANDSCAPE) {
                    MainActivity.binding.detail.visibility = View.VISIBLE
                    MainActivity.binding.title.text = newsItem.title
                    MainActivity.binding.author.text = newsItem.creator?.get(0)
                        ?: "Автор не указан"
                    MainActivity.binding.date.text = newsItem.pubDate
                    MainActivity.binding.content.text = newsItem.content
                    MainActivity.binding.source.text = newsItem.source_id
                    Picasso.get().load(newsItem.image_url).into(MainActivity.binding.image)

                }
            }
        }
    }

    override fun getItemCount(): Int {
        return newsList.size
    }

    inner class ViewHolder(private val binding: NewsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

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
