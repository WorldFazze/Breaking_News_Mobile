package com.example.breakingnews.presentation.favoritesNews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.breakingnews.presentation.main.MainActivity
import com.example.breakingnews.databinding.ActivityFavoritesNewsBinding
import com.example.breakingnews.data.model.News
import com.example.breakingnews.presentation.adapters.NewsFavoritesAdapter

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

    override fun showToast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }
}
