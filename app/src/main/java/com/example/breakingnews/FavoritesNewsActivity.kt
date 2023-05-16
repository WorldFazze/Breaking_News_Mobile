package com.example.breakingnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingnews.databinding.ActivityFavoritesNewsBinding
import com.example.breakingnews.db.adapters.NewsFavoritesAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesNewsActivity : AppCompatActivity() {
    lateinit var binding: ActivityFavoritesNewsBinding
    var context = this
    private lateinit var favoritesNewsAdapter: NewsFavoritesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val db = MainActivity.db
        val newsList = binding.NewsList
        favoritesNewsAdapter = NewsFavoritesAdapter(context, emptyList())
        newsList.adapter = favoritesNewsAdapter
        newsList.layoutManager = LinearLayoutManager(context)

        GlobalScope.launch {
            val news = db.newsDao().getAllNews()
            withContext(Dispatchers.Main) {
                favoritesNewsAdapter = NewsFavoritesAdapter(context, news)
                binding.NewsList.adapter = favoritesNewsAdapter
            }
        }
    }
}