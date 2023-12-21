package com.example.breakingnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.breakingnews.main.MainActivity
import com.example.breakingnews.databinding.ActivityNewsDetailBinding
import com.example.breakingnews.db.News
import com.example.breakingnews.models.NewsItem
import com.squareup.picasso.Picasso
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class NewsDetailActivity : AppCompatActivity() {
    val db = MainActivity.db
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

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.nav_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add_to_favorites -> {
                GlobalScope.launch {
                    val newsItem = intent.getParcelableExtra<NewsItem>("newsItem")
                    val news = News(
                        creator = newsItem?.creator?.get(0) ?: "Автор не указан",
                        content = newsItem?.content,
                        pubDate = newsItem?.pubDate,
                        source_id = newsItem?.source_id,
                        title = newsItem?.title,
                        image_url = newsItem?.image_url
                    )
                    db.newsDao().insertNews(news)
                }
                Toast.makeText(applicationContext, "Новость добавлена в избранные", Toast.LENGTH_SHORT).show();
            }
        }
        return true
    }
}