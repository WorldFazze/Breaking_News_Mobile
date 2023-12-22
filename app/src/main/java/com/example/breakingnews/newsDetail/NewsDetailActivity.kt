package com.example.breakingnews.newsDetail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.breakingnews.R
import com.example.breakingnews.main.MainActivity
import com.example.breakingnews.databinding.ActivityNewsDetailBinding
import com.example.breakingnews.models.NewsItem

class NewsDetailActivity : AppCompatActivity(), NewsDetailContract.ViewInterface {
    private val db = MainActivity.db
    private lateinit var binding: ActivityNewsDetailBinding
    private lateinit var newsDetailPresenter: NewsDetailContract.PresenterInterface

    private fun setupPresenter() {
        newsDetailPresenter = NewsDetailPresenter(this, db)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val newsItem = intent.getParcelableExtra<NewsItem>("newsItem")
        displayNewsDetails(newsItem)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_to_favorites -> {
                newsDetailPresenter.onAddToFavoritesClick(intent)
            }
        }
        return true
    }

    private fun displayNewsDetails(newsItem: NewsItem?) {
        binding.title.text = newsItem?.title
        binding.content.text = newsItem?.content
        newsDetailPresenter.loadImage(newsItem?.image_url, binding.image)
        binding.date.text = newsItem?.pubDate
        binding.author.text = newsItem?.creator?.get(0) ?: "Автор не указан"
        binding.source.text = newsItem?.source_id
    }

    override fun showToast(string: String) {
        Toast.makeText(applicationContext, string, Toast.LENGTH_SHORT).show()
    }
}
