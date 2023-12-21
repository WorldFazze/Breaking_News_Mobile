package com.example.breakingnews.favoritesNews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingnews.main.MainActivity
import com.example.breakingnews.databinding.ActivityFavoritesNewsBinding
import com.example.breakingnews.db.News
import com.example.breakingnews.db.adapters.NewsFavoritesAdapter
import com.example.breakingnews.main.MainContract
import com.example.breakingnews.main.MainPresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoritesNewsActivity : AppCompatActivity(), FavoritesNewsContract.ViewInterface {
    private lateinit var binding: ActivityFavoritesNewsBinding
    private lateinit var favoritesNewsAdapter: NewsFavoritesAdapter
    private lateinit var favoritesNewsPresenter: FavoritesNewsContract.PresenterInterface

    private fun setupPresenter() {
        favoritesNewsPresenter = FavoritesNewsPresenter(this, MainActivity.db)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesNewsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupPresenter()
        setupViews()

        favoritesNewsPresenter.loadFavoritesNews()
    }

    private fun setupViews() {
        favoritesNewsAdapter = NewsFavoritesAdapter(this, emptyList())
        binding.NewsList.adapter = favoritesNewsAdapter
        binding.NewsList.layoutManager = LinearLayoutManager(this)
    }

    override fun setNewsList(news: List<News>) {
        favoritesNewsAdapter = NewsFavoritesAdapter(this, news)
        binding.NewsList.adapter = favoritesNewsAdapter
    }
}
