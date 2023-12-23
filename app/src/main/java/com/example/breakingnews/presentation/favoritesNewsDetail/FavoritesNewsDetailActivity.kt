package com.example.breakingnews.presentation.favoritesNewsDetail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.breakingnews.databinding.ActivityFavoritesNewsDetailBinding
import com.example.breakingnews.data.model.News

class FavoritesNewsDetailActivity : AppCompatActivity(), FavoritesNewsDetailContract.ViewInterface {
    private lateinit var binding: ActivityFavoritesNewsDetailBinding
    private lateinit var favoritesNewsDetailPresenter: FavoritesNewsDetailContract.PresenterInterface

    private fun setupPresenter() {
        favoritesNewsDetailPresenter = FavoritesNewsDetailPresenter(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupPresenter()

        displayNewsDetails(favoritesNewsDetailPresenter.loadExtra(intent))
    }

    override fun displayNewsDetails(newsItem: News?) {
        binding.title.text = newsItem?.title
        binding.content.text = newsItem?.content
        favoritesNewsDetailPresenter.loadImage(newsItem?.image_url, binding.image)
        binding.date.text = newsItem?.pubDate
        binding.author.text = newsItem?.creator
        binding.source.text = newsItem?.source_id
    }
}
