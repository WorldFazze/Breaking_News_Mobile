package com.example.breakingnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.breakingnews.databinding.ActivityNewsDetailBinding
import com.example.breakingnews.models.NewsItem
import com.squareup.picasso.Picasso

class NewsDetailActivity : AppCompatActivity() {
    lateinit var binding: ActivityNewsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsItem = intent.getParcelableExtra<NewsItem>("newsItem")
        binding.title.text = newsItem?.title
        binding.content.text = newsItem?.content
        Picasso.get().load(newsItem?.image_url).into(binding.image)
        binding.date.text = newsItem?.pubDate
        binding.author.text = newsItem?.creator?.get(0) ?: "Автор не указан"
        binding.source.text = newsItem?.source_id
    }
}