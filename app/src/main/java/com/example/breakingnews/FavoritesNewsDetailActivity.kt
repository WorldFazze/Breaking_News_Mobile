package com.example.breakingnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.breakingnews.main.MainActivity
import com.example.breakingnews.databinding.ActivityFavoritesNewsDetailBinding
import com.example.breakingnews.db.News
import com.squareup.picasso.Picasso

class FavoritesNewsDetailActivity : AppCompatActivity() {
    val db = MainActivity.db
    lateinit var binding: ActivityFavoritesNewsDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsItem = intent.getParcelableExtra<News>("newsItem")
        binding.title.text = newsItem?.title
        binding.content.text = newsItem?.content
        Picasso.get().load(newsItem?.image_url).into(binding.image)
        binding.date.text = newsItem?.pubDate
        binding.author.text = newsItem?.creator
        binding.source.text = newsItem?.source_id
    }
}